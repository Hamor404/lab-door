package com.villege.modules.controller.sys;

import com.villege.common.annotation.SysLog;
import com.villege.common.utils.R;
import com.villege.common.validator.ValidatorUtils;
import com.villege.entity.sys.SysMenuEntity;
import com.villege.entity.tree.SysMenuTree;
import com.villege.modules.base.AbstractController;
import com.villege.service.sys.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 菜单管理
 *
 * @author 赖国荣 31343080@qq.com
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;


    @GetMapping("/list")
    @ApiOperation("列表")
    @RequiresPermissions("sys:menu:list")
    public R list() {
        List<SysMenuTree> list = sysMenuService.getListTree();
        return R.ok().put("data", list);
    }


    @ApiOperation("菜单信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("id") Long id) {
        SysMenuEntity data = sysMenuService.getById(id);
        return R.ok().put("data", data);
    }


    @SysLog("添加菜单")
    @ApiOperation("添加菜单")
    @PostMapping("/add")
    @RequiresPermissions("sys:menu:add")
    public R add(@RequestBody SysMenuEntity entity) {
        ValidatorUtils.validateEntity(entity);
        sysMenuService.save(entity);
        return R.ok();
    }


    @SysLog("编辑菜单")
    @ApiOperation("编辑菜单")
    @PutMapping("/edit")
    @RequiresPermissions("sys:menu:edit")
    public R edit(@RequestBody SysMenuEntity entity) {
        ValidatorUtils.validateEntity(entity);
        sysMenuService.updateById(entity);
        return R.ok();
    }


    @SysLog("删除菜单")
    @ApiOperation("删除菜单")
    @DeleteMapping("/del")
    @RequiresPermissions("sys:menu:del")
    public R del(@RequestBody Long[] id) {
        sysMenuService.removeByIds(Arrays.asList(id));
        return R.ok();
    }

}
