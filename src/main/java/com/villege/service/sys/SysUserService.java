package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.common.utils.PageUtils;
import com.villege.entity.sys.SysUserEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统用户
 *
 * @author 赖国荣 15374363882
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 后台分页
     *
     * @param params
     * @return
     */
    PageUtils selectByPage(Map<String, Object> params);

    /**
     * 保存用户
     *
     * @param sysUserEntity
     */
    void saveUser(SysUserEntity sysUserEntity);

    /**
     * 编辑用户
     *
     * @param sysUserEntity
     */
    void updateUser(SysUserEntity sysUserEntity);

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    SysUserEntity selectByUsername(String username);

    /**
     * 获取用户权限列表
     *
     * @param userId
     * @return
     */
    Set<String> getUserMenus(Long userId);

    /**
     * 获取第二个数据库的数据
     *
     * @return
     */
    List<SysUserEntity> dynamicDataSource();

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     */
    void updatePassword(Long userId, String password);

    SysUserEntity getUserById(long id) ;
}
