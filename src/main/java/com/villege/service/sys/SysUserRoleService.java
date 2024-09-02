package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.entity.sys.SysUserRoleEntity;

import java.util.List;

/**
 * 角色用户关系
 *
 * @author 赖国荣 31343080@qq.com
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    /**
     * 保存或修改
     *
     * @param userId     用户ID
     * @param roleIdList 角色ID列表
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据角色ids，删除角色用户关系
     *
     * @param roleIds 角色ids
     */
    void deleteByRoleIds(Long[] roleIds);

    /**
     * 角色ID列表
     *
     * @param userId 用户ID
     * @return
     */
    List<Long> getRoleIdList(Long userId);

}
