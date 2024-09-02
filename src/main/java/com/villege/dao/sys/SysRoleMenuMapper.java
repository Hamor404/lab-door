package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.villege.entity.sys.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色对应权限表
 *
 * @author Lewis 31343080@qq.com
 */
@Mapper
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {


    /**
     * 获取角色对应的菜单
     *
     * @param roleId
     * @return
     */
    @Select("SELECT menu_id FROM sys_role_menu WHERE role_id = #{roleId}")
    List<Long> getMenuListByRoleId(Long roleId);

    /**
     * 根据角色id，删除角色菜单关系
     *
     * @param roleId
     * @return
     */
    @Delete({
            "<script>",
            "DELETE FROM sys_role_menu WHERE role_id in",
            "<foreach item='roleId' collection='array' open='(' separator=',' close=')'>",
            "#{roleId}",
            "</foreach>",
            "</script>"
    })
    void deleteByRoleIds(Long[] roleId);

}
