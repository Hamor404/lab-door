package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.common.constant.Constant;
import com.villege.common.utils.PageUtils;
import com.villege.dao.sys.SysRoleMapper;
import com.villege.entity.sys.SysRoleEntity;
import com.villege.service.sys.SysRoleMenuService;
import com.villege.service.sys.SysRoleService;
import com.villege.service.sys.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色管理
 *
 * @author 赖国荣 31343080@qq.com
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Autowired
    protected SysRoleMenuService sysRoleMenuService;
    @Autowired
    protected SysUserRoleService sysUserRoleService;

    @Override
    public PageUtils selectByPage(Map<String, Object> params) {
        long page = Long.parseLong((String) params.get(Constant.PAGE));
        long limit = Long.parseLong((String) params.get(Constant.LIMIT));
        String roleName = (String) params.get("roleName");
        Page<SysRoleEntity> pages = new Page<>(page, limit);
        QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.like(StringUtils.isNotBlank(roleName), "role_name", roleName);
        IPage<SysRoleEntity> list = baseMapper.selectPage(pages, wrapper);
        return new PageUtils(list);
    }


    @Override
    public Set<String> getUserRoles(Long userId) {
        List<String> roleList = baseMapper.getUserRoles(userId);
        //用户权限列表
        Set<String> roleSet = new HashSet<>();
        for (String roles : roleList) {
            if (StringUtils.isBlank(roles)) {
                continue;
            }
            roleSet.addAll(Arrays.asList(roles.trim().split(",")));
        }
        return roleSet;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity entity) {
        //保存角色
        baseMapper.insert(entity);
        //保存角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getRoleId(), entity.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRoleEntity entity) {
        //更新角色
        updateById(entity);
        //更新角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getRoleId(), entity.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        //删除角色
        baseMapper.deleteBatchIds(Arrays.asList(ids));

        //删除角色用户关系
        sysUserRoleService.deleteByRoleIds(ids);

        //删除角色菜单关系
        sysRoleMenuService.deleteByRoleIds(ids);

    }
    @Override
    public List<SysRoleEntity> getRoleList(long type){
        return baseMapper.getRoleList(type);
    }
}
