package com.villege.modules.controller.sys;

import com.villege.common.annotation.SysLog;
import com.villege.common.utils.PageUtils;
import com.villege.common.utils.R;
import com.villege.common.validator.ValidatorUtils;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.ManualRecordVo;
import com.villege.entity.sys.SysUserEntity;
import com.villege.modules.base.AbstractController;
import com.villege.service.sys.ManualRecordService;
import com.villege.shiro.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/sys/manual")
public class ManualRecordController extends AbstractController {
    @Autowired
    private ManualRecordService personService;


    @PostMapping("/list")
    @RequiresPermissions("sys:manual:list")
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
    @RequiresPermissions("sys:manual:info")
    public R info(@PathVariable("personId") Long personId) {

        List<CommunityVo> communityList = null;
        Map map =  new HashMap();
        if(personId>0) {
            ManualRecordVo person = personService.getById(personId);
            map.put("manualInfo", person);
        }
        communityList = personService.getAgentCommunity();
        map.put("communityList",communityList);

        return R.ok().put("data", map);
    }
    @SysLog("添加人员")
    @PostMapping("/add")
    @ApiOperation("添加人员")
    @RequiresPermissions("sys:manual:add")
    public R add(@RequestBody ManualRecordVo entity, HttpServletRequest request) throws  Exception{
        entity.setSignTime(new Date());
        SysUserEntity user = ShiroUtils.getUser();
        entity.setUserName(user.getUsername());
        personService.save(entity);
        return R.ok();
    }


    @SysLog("编辑人员")
    @PutMapping("/edit")
    @ApiOperation("编辑人员")
    @RequiresPermissions("sys:manual:info")
    public R edit(@RequestBody ManualRecordVo entity, HttpServletRequest request) throws Exception {
        ValidatorUtils.validateEntity(entity);
        ManualRecordVo user = personService.getById(entity.getManualRecordId());

        entity.setUserName(user.getUserName());
        entity.setSignTime(user.getSignTime());
        personService.updateById(entity);
        return R.ok();
    }

    @SysLog("删除人员")
    @DeleteMapping("/del")
    @ApiOperation("删除人员")
    @RequiresPermissions("sys:manual:del")
    public R del(@RequestBody Long[] ids) {
        personService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @GetMapping("/getCommunityList")
    @RequiresPermissions("sys:manual:info")
    public R getCommunityList(){
        List<CommunityVo> communityList = personService.getAgentCommunity();
        return R.ok().put("data",communityList);
    }
}
