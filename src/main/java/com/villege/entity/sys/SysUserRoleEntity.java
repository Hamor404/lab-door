package com.villege.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author 赖国荣 31343080@qq.com
 */
@Data
@TableName("sys_user_role")
public class SysUserRoleEntity implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;

}
