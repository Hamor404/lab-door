package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.villege.entity.sys.SysMenuEntity;
import com.villege.entity.tree.SysMenuTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author Lewis 31343080@qq.com
 */
@Mapper
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {


    /**
     * 获取用户可以操作的按钮列表
     *
     * @param userId 用户ID
     * @return
     */
    @Select({
            "<script>",
            "SELECT c.perms from sys_user_role AS a",
            "LEFT JOIN sys_role_menu AS b on a.role_id = b.role_id",
            "LEFT JOIN sys_menu AS c on b.menu_id = c.id",
            "WHERE c.menu_type = '3' AND a.user_id = #{userId}",
            "</script>"
    })
    Set<String> getUserButtonsList(Long userId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId
     * @return
     */
    @Select({
            "<script>",
            "SELECT m.perms from sys_user_role ur",
            "LEFT JOIN sys_role_menu rm on ur.role_id = rm.role_id ",
            "LEFT JOIN sys_menu m on rm.menu_id = m.id ",
            "where ur.user_id = #{userId}",
            "</script>"
    })
    List<String> selectMenuByUserId(Long userId);

    /**
     * 获取所有可以操作的按钮列表
     *
     * @return
     */
    @Select("SELECT perms from sys_menu WHERE perms !='' AND menu_type = 3")
    Set<String> getAllButtonsList();

    /**
     * 查询所有并根据sort排序
     *
     * @return
     */
    @Select("SELECT * FROM sys_menu order by sort ASC")
    List<SysMenuEntity> selectAllMenus();

    /**
     * 获取所有树形数据
     *
     * @return
     */
    @Select("SELECT * FROM sys_menu ORDER BY sort ASC")
    List<SysMenuTree> getList();

    /**
     * 查询所有菜单
     *
     * @return 菜单列表
     */
    @Select({
            "<script>",
            "select distinct m.id, m.parent_id, m.name, m.path, m.component, m.status, ifnull(m.perms,'') as perms, m.is_frame, m.menu_type, m.icon, m.sort",
            "from sys_menu m where m.menu_type in (1, 2) and m.status = 00",
            "order by m.parent_id, m.sort",
            "</script>"
    })
    List<SysMenuEntity> selectMenuTreeAll();



    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Select({
            "<script>",
            "select distinct m.id, m.parent_id, m.name, m.path, m.component, m.status, ifnull(m.perms,'') as perms, m.is_frame, m.menu_type, m.icon, m.sort,m.hidden",
            "from sys_menu m",
            "left join sys_role_menu rm on m.id = rm.menu_id",
            "left join sys_user_role ur on rm.role_id = ur.role_id",
            "left join sys_role ro on ur.role_id = ro.role_id",
            "left join sys_user u on ur.user_id = u.user_id",
            "where u.user_id = #{userId} and m.menu_type in (1, 2) and m.status = 0",
            "order by m.parent_id, m.sort",
            "</script>"
    })
    List<SysMenuEntity> selectMenuTreeByUserId(Long userId);

}
