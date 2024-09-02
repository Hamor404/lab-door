package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.villege.entity.sys.SysUserRoleEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色用户关系
 *
 * @author Lewis 31343080@qq.com
 */
@Mapper
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {

    /**
     * 根据角色id，删除角色菜单关系
     *
     * @param roleId
     * @return
     */
    @Delete({
            "<script>",
            "DELETE FROM sys_user_role WHERE role_id in",
            "<foreach item='roleId' collection='array' open='(' separator=',' close=')'>",
            "#{roleId}",
            "</foreach>",
            "</script>"
    })
    void deleteByRoleIds(Long[] roleId);

    /**
     * 角色ID列表
     *
     * @param userId
     * @return
     */
    @Select("SELECT role_id from sys_user_role where user_id = #{userId}")
    List<Long> getRoleIdList(@Param("userId") Long userId);
}
