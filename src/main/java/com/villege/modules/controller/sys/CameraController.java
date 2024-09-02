package com.villege.modules.controller.sys;

import com.villege.common.annotation.SysLog;
import com.villege.common.utils.R;
import com.villege.common.validator.ValidatorUtils;
import com.villege.entity.sys.CameraInfoVo;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.SysUserEntity;
import com.villege.modules.base.AbstractController;
import com.villege.service.sys.CameraInfoService;
import com.villege.service.sys.CommunityService;
import com.villege.shiro.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/camera")
public class CameraController extends AbstractController {
    @Autowired
    private CameraInfoService cameraService;
    @Autowired
    private CommunityService parkService;
    @PostMapping("/list")
    @RequiresPermissions("sys:camera:list")
    public R list(@RequestBody Map<String, Object> params) {
        List<CameraInfoVo> cameraList = cameraService.getList(params);
        return R.ok().put("data", cameraList);
    }

    @ApiOperation("获取摄像头信息")
    @GetMapping("/info/{cameraInfoId}")
    @RequiresPermissions("sys:camera:info")
    public R info(@PathVariable("cameraInfoId") Long cameraInfoId) {
        CameraInfoVo camera = null;
        if(cameraInfoId>0) {
            camera = cameraService.getById(cameraInfoId);
        }

        return R.ok().put("data", camera);
    }
    @SysLog("添加摄像头")
    @PostMapping("/add")
    @ApiOperation("添加摄像头")
    @RequiresPermissions("sys:camera:add")
    public R add(@RequestBody CameraInfoVo entity, HttpServletRequest request){
        CommunityVo park = parkService.getById(entity.getCommunityId());
        entity.setCreaterTime(new Date());
        SysUserEntity user = ShiroUtils.getUser();
        entity.setCreater(user.getUsername());
        try {
            cameraService.save(entity);
        }catch (Exception e){
            return R.error("IP地址重复。");
        }
        return R.ok();
    }


    @SysLog("编辑摄像头")
    @PutMapping("/edit")
    @ApiOperation("编辑摄像头")
    @RequiresPermissions("sys:camera:info")
    public R edit(@RequestBody CameraInfoVo entity, HttpServletRequest request) throws Exception {
        ValidatorUtils.validateEntity(entity);
        CameraInfoVo user = cameraService.getById(entity.getCameraInfoId());
        entity.setCreater(user.getCreater());
        entity.setCreaterTime(user.getCreaterTime());
        cameraService.updateById(entity);
        return R.ok();
    }

    @SysLog("删除摄像头")
    @DeleteMapping("/del")
    @ApiOperation("删除摄像头")
    @RequiresPermissions("sys:camera:del")
    public R del(@RequestBody Long[] ids) {
        cameraService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
