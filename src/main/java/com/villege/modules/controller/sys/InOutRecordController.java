package com.villege.modules.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.villege.common.annotation.SysLog;
import com.villege.common.utils.PageUtils;
import com.villege.common.utils.R;
import com.villege.dade.face.Base64Util;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.InOutRecordVo;
import com.villege.entity.sys.PersonInfoVo;
import com.villege.face.AppConfig;
import com.villege.face.FaceApi2;
import com.villege.face.RootResp;
import com.villege.modules.base.AbstractController;
import com.villege.service.sys.CommunityService;
import com.villege.service.sys.InOutRecordService;
import com.villege.service.sys.PersonInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/sys/inOut")
public class InOutRecordController extends AbstractController {
    @Autowired
    private InOutRecordService inOutRecordService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private AppConfig appConfig;

    @Value("${upload.face}")
    String face;
    @Value("${upload.excel}")
    String excel;
    @Value("${upload.urlPrefix}")
    String urlPrefix;
    //获取当前合作伙伴的小区列表
    @PostMapping("/communityList")
    @RequiresPermissions("sys:inOut:list")
    public R communityList() {
        List<CommunityVo> communityList = communityService.getCommunityList();
        return R.ok().put("data", communityList);
    }

    @PostMapping("/list")
    @RequiresPermissions("sys:inOut:list")
    public R list(@RequestBody Map<String, Object> params) {
        PageUtils pageList = inOutRecordService.getPageList(params);
        List<InOutRecordVo> payList = (List<InOutRecordVo>)pageList.getList();
        Map map = new HashMap();
        map.put("pageList",pageList);




        return R.ok().put("data", map);
    }

    @SysLog("添加人员进出信息")
    @PostMapping("/add")
    @ApiOperation("添加人员进出信息(人脸识别)")
    @RequiresPermissions("sys:inOut:list")
    public R add(@RequestBody InOutRecordVo rec, HttpServletRequest request) throws Exception{
        RootResp resp = searchPersonsReturnsByGroup(rec); //carVerify(consume);
        /*{"FaceNum":7399,"ResultsReturnsByGroup":[
            "GroupCandidates":[
                {"GroupId":"testGroup",
                "Candidates":[
                    {"PersonId":"Test_22","FaceId":"3858384500535902169","Score":100.0,"PersonName":"httest22",
                        "PersonGroupInfos":[{"GroupId":"testGroup","PersonExDescriptions":[]}]
                    },
                    {"PersonId":"Test_91","FaceId":"4642020619626536744","Score":100.0,"PersonName":"赖国荣",
                        "PersonGroupInfos":[{"GroupId":"testGroup","PersonExDescriptions":[]}]}]}],"RetCode":0
                    }
                 ],
                 "FaceModelVersion":"3.0","RequestId":"b9c66919-0afc-4dd8-af46-600c961b2bb1"
               }
         */
        String msg="";
        if(resp.getRet()==0) {
            System.out.println(resp.getData().toString());
            JSONObject object = JSONObject.parseObject(resp.getData().toString());
            JSONArray resultsReturnsByGroup = object.getJSONArray("ResultsReturnsByGroup");
            JSONObject returnsByGroupJSONObject = resultsReturnsByGroup.getJSONObject(0);
            JSONArray groupCandidates = returnsByGroupJSONObject.getJSONArray("GroupCandidates");
            JSONObject groupCandidatesJSONObject = groupCandidates.getJSONObject(0);
            JSONArray candidates = groupCandidatesJSONObject.getJSONArray("Candidates");
            //返回多个人员，匹配数据库人员信息
            String personId ="";
            String faceId = "";
            String personName = "";
            String faceUrl = "";
            long pid = 0;
            PersonInfoVo p = null,p1=null;
            for(int i=0;i<candidates.size();i++) {
                JSONObject personInfo = candidates.getJSONObject(i);
                personId = personInfo.getString("PersonId");
                faceId = personInfo.getString("FaceId");
                personName = personInfo.getString("PersonName");
                personId = personId.substring(5);
                pid = Integer.parseInt(personId);
                p = personInfoService.getById(pid);
                if(p == null)
                    continue;
                else
                   p1=p;
                faceUrl = p.getFaceUrl();
                if(faceUrl == null || faceUrl.equals("")){
                    continue;
                }
                faceUrl = faceUrl.substring(faceUrl.lastIndexOf("/")+1,faceUrl.lastIndexOf("."));
                if(faceId.equals(faceUrl)) {
                    break;
                }
            }
            if(p==null) {
                return R.ok().put("data","人员信息不存在");
            }
            if(rec.getCommunityId() != p.getCommunityId()) {
                return R.ok().put("data","对不起，你不是本小区居民，请与系统管理员联系。");
            }
            rec.setCommunityId(p.getCommunityId());
            rec.setPersonId(p.getPersonId());
            String base64 = rec.getFileBase64();
            rec.setFileBase64("");
            //查找系统中是否有该人员的出入场信息
            InOutRecordVo in = inOutRecordService.getInOutRecord(rec);
            //保存进入图片
            String newFileName = UUID.randomUUID()+"." + rec.getExtName();
            String fileName = face + newFileName;
            Base64Util.decoderBase64File(base64,fileName);
            String basePath = urlPrefix + "villegePic/face/" + newFileName;
            //进入小区
            if(in == null) {
                rec.setInPic(basePath);
                rec.setInTime(new Date());
                inOutRecordService.save(rec);
                msg = "【"+p.getUserName() + "】进入小区";
            //离开小区
            }else{
                in.setOutTime(new Date());
                in.setOutPic(basePath);
                inOutRecordService.saveOrUpdate(in);
                msg = "【"+p.getUserName() + "】离开小区";
            }
        }else{
            msg = "人脸识别失败,错误码=" + resp.getRet() + "," + resp.getMsg();
        }
        return R.ok().put("data",msg);
    }


    private RootResp searchPersonsReturnsByGroup(InOutRecordVo rec) throws Exception {

        FaceApi2 faceApi = new FaceApi2();

        RootResp resp = faceApi.searchPersonsReturnsByGroup(appConfig, rec.getFileBase64());

        System.out.println(resp.getData()+":"+resp.getMsg()+":"+resp.getRet());

        return resp;
    }
    //首页统计图表
    @PostMapping("/chart")
    @RequiresPermissions("sys:inOut:list")
    public R getPersonStaticsList(){
        Map map = new HashMap();
        List<CommunityVo> communityList = inOutRecordService.getAgentCommunity();
        int size = communityList.size();
        JSONArray series = new JSONArray();
        JSONArray product = new JSONArray();
        JSONObject dataset = new JSONObject();
        JSONArray source = new JSONArray();
        if(size>0) {
            product.add("product");
            product.add("男");
            product.add("女");
            JSONObject bar = new JSONObject();
            bar.put("type", "bar");
            series.add(bar);
            bar = new JSONObject();
            bar.put("type", "bar");
            series.add(bar);
            source.add(product);

            for (CommunityVo community : communityList) {

                map.put("community",community.getCommunityName());
                JSONArray row = new JSONArray();
                row.add(community.getCommunityName());
                List<CommunityVo> list = inOutRecordService.getPersonStat(community.getCommunityId());
                String sexArr[] = {"男","女"};
                for(int j=0;j<2;j++) {
                    int val = 0;
                    for(CommunityVo cv : list) {
                        if (cv.getSex().equals(sexArr[j])) {
                            val=cv.getPersonCnt();
                            break;
                        }
                    }
                    row.add(val);
                }
                source.add(row);
            }
            dataset.put("source",source);
        }
        JSONObject title = new JSONObject();
        title.put("text","小区居民分类统计表");
        title.put("subtext","单位：人");
        JSONObject tooltip = new JSONObject();
        JSONObject legend = new JSONObject();
        JSONObject xAxis = new JSONObject();
        xAxis.put("type","category");
        JSONObject yAxis = new JSONObject();

        JSONObject mapData = new JSONObject();
        mapData.put("title",title);
        mapData.put("tooltip",tooltip);
        mapData.put("legend",legend);
        mapData.put("dataset",dataset);
        mapData.put("xAxis",xAxis);
        mapData.put("yAxis",yAxis);
        mapData.put("series",series);
        JSONObject options = new JSONObject();
        options.put("options",mapData);
        //options.toJSONString()
        return R.ok().put("data",options);
    }
}
