package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.villege.entity.sys.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author 赖国荣 31343080@qq.com
 */
@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 后台分页(已在service中实现，mapper中的方法预留)
     *
     * @param pages
     * @param params
     * @return
     */
    @Select({
            "<script>",
            "SELECT * FROM sys_user WHERE 1 = 1",
            "<if test=\"params.username != null and params.username.trim() != ''\">",
            "AND username like concat('%',#{params.username},'%')",
            "</if>",
            "<if test=\"params.realName != null and params.realName.trim() != ''\">",
            "AND real_name like concat('%',#{params.realName},'%')",
            "</if>",
            "<if test=\"params.mobile != null and params.mobile.trim() != ''\">",
            "AND mobile like concat('%',#{params.mobile},'%')",
            "</if>",
            "<if test=\" null != params.userType and params.userType.trim() != ''\">",
            "AND user_type = ${params.userType}",
            "</if>",
            "ORDER BY user_id desc",
            "</script>"
    })
    List<SysUserEntity> getPageList(Page pages, @Param("params") Map<String, Object> params);

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    @Select("SELECT * FROM sys_user where username = #{username}")
    SysUserEntity selectByUsername(String username);

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     * @return
     */
    @Update("UPDATE sys_user set password = #{newPassword} where user_id = #{userId}")
    void updatePassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);

    @Select("select * from sys_user where user_id=${userId}")
    SysUserEntity getUserById(@Param("userId") long userId);
}
