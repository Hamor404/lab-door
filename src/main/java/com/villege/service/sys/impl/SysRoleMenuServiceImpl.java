package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.dao.sys.SysRoleMenuMapper;
import com.villege.entity.sys.SysRoleMenuEntity;
import com.villege.service.sys.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色对应权限表
 *
 * @author Lewis 31343080@qq.com
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    public List<Long> getMenuListByRoleId(Long roleId) {
        return baseMapper.getMenuListByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色菜单关系
        deleteByRoleIds(new Long[]{roleId});

        //角色没有一个菜单权限的情况
        if (menuIdList.size() == 0) {
            return;
        }

        //保存角色菜单关系
        for (Long menuId : menuIdList) {
            SysRoleMenuEntity entity = new SysRoleMenuEntity();
            entity.setRoleId(roleId);
            entity.setMenuId(menuId);
            //保存
            baseMapper.insert(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleIds(Long[] roleIds) {
        baseMapper.deleteByRoleIds(roleIds);
    }
}
