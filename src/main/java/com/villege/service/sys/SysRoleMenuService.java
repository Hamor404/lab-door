package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.entity.sys.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色对应权限表
 *
 * @author 赖国荣 31343080@qq.com
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

    /**
     * 获取角色对应的菜单
     *
     * @param roleId
     * @return
     */
    List<Long> getMenuListByRoleId(Long roleId);

    /**
     * 保存或修改
     *
     * @param roleId           角色ID
     * @param permissionIdList 菜单ID列表
     */
    void saveOrUpdate(Long roleId, List<Long> permissionIdList);

    /**
     * 根据角色id，删除角色菜单关系
     *
     * @param roleIds 角色id
     */
    void deleteByRoleIds(Long[] roleIds);
}
