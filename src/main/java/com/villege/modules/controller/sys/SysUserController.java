package com.villege.modules.controller.sys;

import cn.hutool.crypto.SecureUtil;
import com.villege.common.annotation.SysLog;
import com.villege.common.constant.Constant;
import com.villege.common.utils.PageUtils;
import com.villege.common.utils.R;
import com.villege.common.validator.Assert;
import com.villege.common.validator.ValidatorUtils;
import com.villege.entity.sys.PasswordEntity;
import com.villege.entity.sys.SysMenuEntity;
import com.villege.entity.sys.SysUserEntity;
import com.villege.modules.base.AbstractController;
import com.villege.service.sys.SysMenuService;
import com.villege.service.sys.SysRoleService;
import com.villege.service.sys.SysUserRoleService;
import com.villege.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * 用户管理
 *
 * @author Lewis 31343080@qq.com
 */
@Api(tags = "系统用户")
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysMenuService sysPermissionService;


    @ApiOperation("用户列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "第几页", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页几条", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", paramType = "query", dataType = "string")
    })
    public R list(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageUtils list = sysUserService.selectByPage(params);
        return R.ok().put("data", list);
    }


    @ApiOperation("获取用户信息")
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);
        Assert.isNull(user, "系统中没有该用户");
        //用户角色列表
        List<Long> roleIdList = sysUserRoleService.getRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        return R.ok().put("data", user);
    }


    @ApiOperation("获取用户权限")
    @GetMapping("/getRouters")
    public R getRouters() {
        Long userId = getUserId();
        Map<String, Object> map = new HashMap<>(16);
        SysUserEntity user = sysUserService.getUserById(userId);
        //获取用户角色
        Set<String> roleSet = sysRoleService.getUserRoles(userId);
        //获取用户可以操作的菜单列表
        List<SysMenuEntity> menus = sysPermissionService.selectMenuTreeByUserId(userId);
        //获取用户可以操作的按钮列表
        Set<String> buttons = sysPermissionService.getUserButtonsList(userId);
        map.put("data", user);
        map.put("routers", sysPermissionService.buildMenus(menus));
        map.put("roles", roleSet);
        map.put("buttons", buttons);
        return R.ok(map);
    }


    @SysLog("添加用户")
    @PostMapping("/add")
    @ApiOperation("添加用户")
    @RequiresPermissions("sys:user:add")
    public R add(@RequestBody SysUserEntity entity) {
        ValidatorUtils.validateEntity(entity);
        sysUserService.saveUser(entity);
        return R.ok();
    }


    @SysLog("编辑用户")
    @PutMapping("/edit")
    @ApiOperation("编辑用户")
    @RequiresPermissions("sys:user:edit")
    public R edit(@RequestBody SysUserEntity entity) {
        ValidatorUtils.validateEntity(entity);
        sysUserService.updateUser(entity);
        return R.ok();
    }


    @SysLog("删除用户")
    @DeleteMapping("/del")
    @ApiOperation("删除用户")
    @RequiresPermissions("sys:user:del")
    public R del(@RequestBody Long[] ids) {
        if (ArrayUtils.contains(ids, 1L)) {
            return R.error("系统管理员不能删除");
        }
        if (ArrayUtils.contains(ids, getUserId())) {
            return R.error("当前用户不能删除");
        }
        sysUserService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    @SysLog("修改密码")
    @PutMapping("/updatePassword")
    @ApiOperation("修改密码")
    public R updatePassword(@RequestBody PasswordEntity entity) {
        //效验数据
        ValidatorUtils.validateEntity(entity);
        Long userId = getUserId();
        SysUserEntity user = sysUserService.getById(userId);
        String password = SecureUtil.sha256(entity.getPassword());
        //原密码不正确
        if (!password.equals(user.getPassword()) && password != null) {
            return R.error("输入密码与旧密码不匹配");
        }
        sysUserService.updatePassword(user.getUserId(), entity.getNewPassword());
        return R.ok();
    }

}
