package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.common.utils.PageUtils;
import com.villege.entity.sys.SysRoleEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色管理
 *
 * @author 赖国荣 31343080@qq.com
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 后台分页数据
     *
     * @param params
     * @return
     */
    PageUtils selectByPage(Map<String, Object> params);

    /**
     * 根据用户ID查询角色
     *
     * @param userId
     * @return
     */
    Set<String> getUserRoles(Long userId);

    /**
     * 保存
     *
     * @param entity
     */
    void saveRole(SysRoleEntity entity);

    /**
     * 更新
     *
     * @param entity
     */
    void updateRole(SysRoleEntity entity);

    /**
     * 删除
     *
     * @param ids
     */
    void delete(Long[] ids);

    List<SysRoleEntity> getRoleList(long type);
}


