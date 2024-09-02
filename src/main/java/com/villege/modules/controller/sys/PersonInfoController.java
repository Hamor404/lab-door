package com.villege.modules.controller.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.villege.common.annotation.SysLog;
import com.villege.common.utils.PageUtils;
import com.villege.common.utils.R;
import com.villege.common.validator.ValidatorUtils;
import com.villege.dade.face.Base64Util;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.PersonInfoVo;
import com.villege.entity.sys.SysUserEntity;
import com.villege.face.AppConfig;
import com.villege.face.FaceApi2;
import com.villege.face.RootResp;
import com.villege.modules.base.AbstractController;
import com.villege.service.sys.PersonInfoService;
import com.villege.shiro.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@RestController
@RequestMapping("/sys/person")
public class PersonInfoController extends AbstractController {
    @Autowired
    private PersonInfoService personService;

    @Value("${upload.excel}")
    String excel;
    @Value("${upload.face}")
    String face;
    @Value("${upload.urlPrefix}")
    String urlPrefix;
    @Autowired
    private AppConfig appConfig;


    @PostMapping("/list")
    @RequiresPermissions("sys:person:list")
    public R list(@RequestBody Map<String, Object> params) {
        List<CommunityVo> communityList = null;
        communityList = personService.getAgentCommunity();
        PageUtils pageList = personService.getPageList(params);
        Map<String ,Object> map = new HashMap<String ,Object>();
        map.put("communityList",communityList);
        map.put("pageList",pageList);

        return R.ok().put("data", map);
    }

    @ApiOperation("获取人员信息")
    @GetMapping("/info/{personId}")
    @RequiresPermissions("sys:person:info")
    public R info(@PathVariable("personId") Long personId) {

        List<CommunityVo> communityList = personService.getAgentCommunity();
        Map map =  new HashMap();
        if(personId>0) {
            PersonInfoVo person = personService.getById(personId);
            map.put("person", person);
        }
        map.put("communityList",communityList);

        return R.ok().put("data", map);
    }
    @SysLog("添加人员")
    @PostMapping("/add")
    @ApiOperation("添加人员")
    @RequiresPermissions("sys:person:add")
    public R add(@RequestBody PersonInfoVo entity, HttpServletRequest request) throws  Exception{
        entity.setCreaterTime(new Date());
        SysUserEntity user = ShiroUtils.getUser();
        entity.setCreater(user.getUsername());
        personService.save(entity);
        return R.ok();
    }


    @SysLog("编辑人员")
    @PutMapping("/edit")
    @ApiOperation("编辑人员")
    @RequiresPermissions("sys:person:info")
    public R edit(@RequestBody PersonInfoVo entity, HttpServletRequest request) throws Exception {
        ValidatorUtils.validateEntity(entity);
        PersonInfoVo user = personService.getById(entity.getPersonId());

        entity.setCreater(user.getCreater());
        entity.setCreaterTime(user.getCreaterTime());
        personService.updateById(entity);
        return R.ok();
    }

    @SysLog("删除人员")
    @DeleteMapping("/del")
    @ApiOperation("删除人员")
    @RequiresPermissions("sys:person:del")
    public R del(@RequestBody Long[] ids) {
        personService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @GetMapping("/getCommunityList")
    public R getCommunityList(){
        List<CommunityVo> communityList = personService.getAgentCommunity();
        return R.ok().put("data",communityList);
    }

    @PostMapping("/exportExcel")
    @RequiresPermissions("sys:person:info")
    //导出人员明细
    public R expInit(@RequestBody Map<String, Object> params) throws Exception{
        List<PersonInfoVo> personList = personService.getList(params);
        String path=excel;
        path=new com.villege.utils.ExportToExcel().ExpPersonInfo(personList,path, 1);
        return R.ok().put("data",path);
    }
    @RequestMapping("/excelUpload")
    public R excelUpload(@RequestParam("uploadExcel") MultipartFile file1, HttpServletRequest request) throws  Exception {
        if(file1.getOriginalFilename().equals("")){
            return  R.error("没有选中要上传的文件");
        }else {
            String picName = UUID.randomUUID().toString();
            // 截取文件的扩展名(如.jpg)
            String oriName = file1.getOriginalFilename();
            System.out.println("--上传文件名-->>" + oriName);
            String extName = oriName.substring(oriName.lastIndexOf("."));

            String newFileName = picName + extName;
            File targetFile = new File(excel, newFileName);
            // 保存文件
            file1.transferTo(targetFile);
            return  R.ok().put("data",newFileName);
        }
    }
    //解析Excel导入试题是否有效及保存
    @RequestMapping("/parsefile/{filename}")
    public R parsefile(@PathVariable("filename") String filename) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        try {
            String basePath = excel + filename;
            fs = new POIFSFileSystem(new FileInputStream(basePath));
            wb = new HSSFWorkbook(fs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = wb.getSheetAt(0);
        Object[][] data = null;
        int r = sheet.getLastRowNum()+1;
//		System.out.println("r="+r);
        int c = sheet.getRow(0).getLastCellNum();
//		System.out.println("c="+c);
        int headRow = 2;//表头ͷ
        data = new Object[r - headRow][c];
        // System.out.println("共"+(r-2) +"行");
        for (int i = headRow; i < r; i++) {
            HSSFRow row = sheet.getRow(i);
            for (int j = 0; j < c; j++) {
                HSSFCell cell = null;
                try {
                    cell = row.getCell(j);
                    try {
                        cell = row.getCell(j);
                        DataFormatter dataFormater = new DataFormatter();
                        String a = dataFormater.formatCellValue(cell);
                        data[i - headRow][j] = a;
                    } catch (Exception e) {
                        data[i-headRow][j] = "";
                        if(j==0){
                            try {
                                double d = cell.getNumericCellValue();
                                data[i - headRow][j] = (int)d + "";
                            }catch(Exception ex){
                                data[i-headRow][j] = "";
                            }
                        }
                    }

                } catch (Exception e) {
                    System.out.println("i="+i+";j="+j+":"+e.getMessage());
                }
            }
        }
        for(int i=0;i<data.length;i++){
            for(int j=0;j<data[0].length;j++){
                System.out.print(data[i][j] + "\t");
            }
            System.out.println("");
        }
        String err = addorUpdate(data);
        return R.ok().put("data",err);
    }
    //保存导入人员信息
    public String addorUpdate(Object[][] data) {
        int row = data.length;
        int col = 0;// data[0].length;
        String errinfo = "";
        int headRow=3;
        String[] stitle={"ID","小区名称","所属楼栋","房号","姓名","性别","手机号码","居住性质","状态","备注"};


        errinfo = "";
        for (int i = 0; i < row; i++) {
            PersonInfoVo single = new PersonInfoVo();
            single.setPersonId(0);
            single.setState(1);
            single.setFaceUrl("");
            try {
                col=1;
                String communityName = data[i][col++].toString();
                CommunityVo community = personService.isCommunity(communityName);
                if( community == null){
                    errinfo += "第" + (i + headRow) + "行小区名称不存在<br/>";
                    continue;
                }
                single.setCommunityId(community.getCommunityId());
                single.setTermName(data[i][col++].toString());
                single.setHouseNo(data[i][col++].toString());
                single.setUserName(data[i][col++].toString());
                single.setSex(data[i][col++].toString());
                single.setMobile(data[i][col++].toString());
                single.setPersonType(data[i][col++].toString());
                single.setRemark(data[i][col++].toString());
                single.setCreater(this.getUser().getUsername());
                single.setCreaterTime(new Date());
                personService.save(single);
            } catch (Exception e) {
                // TODO: handle exception
//                errinfo += "第" + (i + headRow) + "行," + stitle[col-1] + "数据有误;";
//                System.out.print("第" + i+ "行," + col + "列数据有误;");
//                System.out.println("错误数据=" + data[i][col-1]);
                e.printStackTrace();
            }
        }
        errinfo += ",数据导入结束!";
        return errinfo;
    }
    @PostMapping(value = "/addPerson")
    @ApiOperation("[相片采集]:上传图片(4M以内)")
    public R addPerson(@RequestBody PersonInfoVo user, HttpServletRequest request) {
        String fileBase64 = user.getFileBase64();
        String extName = user.getExtName();
        PersonInfoVo p = personService.getById(user.getPersonId());
        user.setState(p.getState());
        user.setUserName(p.getUserName());
        if(user.getState()==2){
            R.error("人脸识别已通过，不需要重复识别");
        }
        if(fileBase64==null || fileBase64.equals("")){
            return R.error("请上传Base64编码的图片");
        }
        user.setFileBase64(fileBase64);
        user.setExtName(extName);

        //开始人脸识别
        user.setState(1);
        user.setFaceUrl("");
        //人脸识别
        if (appConfig.isUsed()) {
            String faceBase64 = user.getFileBase64();
            delPerson(user);
            String faceId = newPerson(user);
            if(faceId==null){
                return  R.error("人脸识别失败");
            }
            if (faceId != null) {
                String basePath = "";
                String path = request.getContextPath();
                basePath = urlPrefix + "villegePic/face/" + faceId + "." + user.getExtName();
                user.setFaceUrl(basePath);
                user.setState(2);
            }
        }
        personService.newPerson(user); //更新人脸识别状态及图片地址

        return R.ok("人脸识别成功");
    }
    // 人脸识别
    //删除人脸
    private boolean delFace(String filename,PersonInfoVo user) {
        String[] strs = filename.split("\\.");
        String faceId = strs[0];
        FaceApi2 faceApi = new FaceApi2();
        RootResp resp = faceApi.delface(appConfig, String.valueOf(user.getPersonId()), faceId);
        if(resp.getRet()==0) {

            File file = new File(face, filename);
            if (file.exists()) {
                file.delete();
            }
            return true;
        }
        else {
            System.out.println("添加人脸出现异常，错误码为:"+resp.getRet()+","+resp.getMsg());
            return false;
        }
    }
    //新增人脸
    private String addFace(PersonInfoVo user) {

        String faceId = null;
        String faceBase64 = user.getFileBase64();
        String savePath = face;

        if (faceBase64!=null && !faceBase64.equals("")) {
            FaceApi2 faceApi = new FaceApi2();
            RootResp resp = faceApi.addface(appConfig, String.valueOf(user.getPersonId()), faceBase64);
            if(resp.getRet()==0) {
                JSONObject data = JSON.parseObject(resp.getData().toString());
                //face_ids
                JSONArray faceIds = data.getJSONArray("SucFaceIds");
                if(!faceIds.isEmpty()) {
                    faceId = faceIds.getString(0);
                    String filename = faceId + "." + user.getExtName();
                    savePath += "/" + filename;

                    try {
                        Base64Util.decoderBase64File(faceBase64, savePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                System.out.println("添加人脸出现异常，错误码为:"+resp.getRet());
                return faceId;
            }
        }
        return faceId;
    }
    //新增个体
    private String newPerson(PersonInfoVo user) {

        String faceId = null;
        String faceBase64 = user.getFileBase64();
        String extname = user.getExtName();
        String personId = user.getPersonId()+"";
        String personName = user.getUserName();
        String savePath = face;
        if (faceBase64!=null && !faceBase64.equals("")) {
            FaceApi2 faceApi = new FaceApi2();
            RootResp resp = faceApi.newperson(appConfig, personId, personName, faceBase64);
            if(resp.getRet()==0) {
                JSONObject data = JSON.parseObject(resp.getData().toString());
                faceId = data.getString("FaceId");
                if(faceId!=null) {
                    String filename = faceId + "." + extname;
                    savePath += filename;
                    try {
                        Base64Util.decoderBase64File(faceBase64, savePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
//                msg=resp.getMsg();
                return faceId;
            }
        }
        return faceId;
    }
    //删除个体
    private boolean delPerson(PersonInfoVo user) {
        boolean bRet = false;
        String personId = user.getPersonId()+"";
        String faceBase64 = user.getFileBase64();
        if (faceBase64!=null && !faceBase64.equals("")) {
            FaceApi2 faceApi = new FaceApi2();
            RootResp resp = faceApi.delperson(appConfig, personId);
            if(resp.getRet()==0) {
                bRet=true;
            }
        }
        return bRet;
    }
}
