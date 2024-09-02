package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.villege.entity.sys.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色管理
 *
 * @author Lewis 31343080@qq.com
 */
@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {


    /**
     * 根据用户ID查询角色
     *
     * @param userId
     * @return
     */
    @Select("SELECT a.role_name FROM sys_role a,sys_user_role b WHERE a.role_id = b.role_id AND b.user_id= #{userId}")
    List<String> getUserRoles(Long userId);

    @Select("SELECT * FROM sys_role WHERE type= #{type}")
    List<SysRoleEntity> getRoleList(@Param("type") long type);
}
