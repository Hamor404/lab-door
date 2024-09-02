package com.villege.modules.controller.sys;

import com.villege.common.annotation.SysLog;
import com.villege.common.constant.Constant;
import com.villege.common.utils.PageUtils;
import com.villege.common.utils.R;
import com.villege.common.validator.ValidatorUtils;
import com.villege.entity.sys.SysRoleEntity;
import com.villege.modules.base.AbstractController;
import com.villege.service.sys.SysRoleMenuService;
import com.villege.service.sys.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author 赖国荣 31343080@qq.com
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    @ApiOperation("角色列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "第几页", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页几条", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "roleName", value = "角色名", paramType = "query", dataType = "string")
    })
    public R list(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageUtils list = sysRoleService.selectByPage(params);
        return R.ok().put("data", list);
    }


    @ApiOperation("获取全部角色")
    @GetMapping("/getRoleList")
    public R getRoleList() {
        List<SysRoleEntity> list = null;
        if(getUser().getUserType()==1){
            list = sysRoleService.list();
        }else {
            list = sysRoleService.getRoleList(getUser().getUserType());
        }
        return R.ok().put("data", list);

    }
    @ApiOperation("获取角色,新增管理员使用")
    @GetMapping("/getUserRoleList/{userType}")
    public R getUserRoleList(@PathVariable("userType") Long userType) {
        List<SysRoleEntity> list = null;
        if(getUser().getUserType()==2 ){
            list = sysRoleService.getRoleList(getUser().getUserType());
        }else {
            list = sysRoleService.getRoleList(userType);
        }
        return R.ok().put("data", list);

    }


    @SysLog("添加角色")
    @ApiOperation("添加角色")
    @PostMapping("/add")
    @RequiresPermissions("sys:role:add")
    public R add(@RequestBody SysRoleEntity entity) {
        ValidatorUtils.validateEntity(entity);
        entity.setCreateTime(new Date());
        sysRoleService.saveRole(entity);
        return R.ok();
    }


    @ApiOperation("获取角色信息")
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.getById(roleId);
        List<Long> list = sysRoleMenuService.getMenuListByRoleId(roleId);
        role.setMenuIdList(list);
        return R.ok().put("data", role);
    }


    @SysLog("编辑角色")
    @ApiOperation("编辑角色")
    @PutMapping("/edit")
    @RequiresPermissions("sys:role:edit")
    public R edit(@RequestBody SysRoleEntity entity) {
        ValidatorUtils.validateEntity(entity);
        sysRoleService.updateRole(entity);
        return R.ok();
    }


    @SysLog("删除角色")
    @DeleteMapping("/del")
    @ApiOperation("删除角色")
    @RequiresPermissions("sys:role:del")
    public R del(@RequestBody Long[] ids) {
        if (ArrayUtils.contains(ids, 1L)) {
            return R.error("超级管理员角色不能删除");
        }
        sysRoleService.delete(ids);
        return R.ok();
    }

}
