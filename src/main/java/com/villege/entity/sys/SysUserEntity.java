package com.villege.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 系统用户
 *
 * @author 赖国荣 31343080@qq.com
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long userId;
    /**
     * 用户名
     */
    @NotBlank(message="用户名不能为空")
    @Length(min=2,max=20,message="用户名长度必须在2-20位之间")
    private String username;
    /**
     * 密码
     */
    //@NotBlank(message="密码不能为空")
    //@Length(min=6,max=20,message="密码长度必须在6-20位之间")
    private String password;

    /**
     * 管理员类别
     */
    private Long userType;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 联系人
     */

    private String contact;

    /**
     * 手机号
     */
//    @NotBlank(message="手机号不能为空")
    private String mobile;
    /**
     * 状态  0正常   1禁用
     */
    private Integer status;

    /**
     * 角色ID列表
     */
    @TableField(exist=false)
    private List<Long> roleIdList;

}
