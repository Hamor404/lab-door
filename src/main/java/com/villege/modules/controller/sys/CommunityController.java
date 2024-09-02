package com.villege.modules.controller.sys;

import com.villege.common.annotation.SysLog;
import com.villege.common.utils.PageUtils;
import com.villege.common.utils.R;
import com.villege.common.validator.Assert;
import com.villege.common.validator.ValidatorUtils;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.SysUserEntity;
import com.villege.modules.base.AbstractController;
import com.villege.service.sys.CommunityService;
import com.villege.shiro.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
//小区信息管理

@RestController
@RequestMapping("/sys/community")
public class CommunityController extends AbstractController {
    @Autowired
    private CommunityService communityService;

    @PostMapping("/list")
    @RequiresPermissions("sys:community:list")
    public R list(@RequestBody Map<String, Object> params) {

        PageUtils pageList = communityService.getPageList(params);
        return R.ok().put("data", pageList);
    }

    @ApiOperation("获取小区信息")
    @GetMapping("/info/{communityId}")
    @RequiresPermissions("sys:community:info")
    public R info(@PathVariable("communityId") Long communityId) {
        CommunityVo community = communityService.getById(communityId);
        Assert.isNull(community, "系统中没有该记录");
        return R.ok().put("data", community);
    }
    @SysLog("添加小区")
    @PostMapping("/add")
    @ApiOperation("添加小区")
    @RequiresPermissions("sys:community:add")
    public R add(@RequestBody CommunityVo community) {
        ValidatorUtils.validateEntity(community);
        SysUserEntity user = ShiroUtils.getUser();
        community.setCreater(user.getUsername());
        community.setCreaterTime(new Date());
        try {
            communityService.save(community);
        }catch (Exception e){
            return R.error("小区名称重复。");
        }
        community.setSeq(community.getCommunityId()*10);

        communityService.updateById(community);
        return R.ok();
    }


    @SysLog("编辑小区")
    @PutMapping("/edit")
    @ApiOperation("编辑小区")
    @RequiresPermissions("sys:community:info")
    public R edit(@RequestBody CommunityVo community) {
        ValidatorUtils.validateEntity(community);
        CommunityVo g = communityService.getById(community.getCommunityId());
        community.setCreater(g.getCreater());
        community.setCreaterTime(g.getCreaterTime());
        communityService.updateById(community);
        return R.ok();
    }


    @SysLog("删除小区")
    @DeleteMapping("/del")
    @ApiOperation("删除小区")
    @RequiresPermissions("sys:community:del")
    public R del(@RequestBody Long[] ids) {
        communityService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
    @PostMapping("/getCommunityMap")
    @ApiOperation("获取小区地图数据")
    @RequiresPermissions("sys:community:list")
    public R getCommunityMap() {

        List<CommunityVo> communityList = communityService.getCommunityMap();
        return R.ok().put("data",communityList);
    }
}
