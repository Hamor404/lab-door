package com.villege.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author 赖国荣 31343080@qq.com
 */
@Data
@TableName("sys_role")
public class SysRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @TableId
    private Long roleId;
    /**
     * 角色名称
     */
    @NotBlank(message="角色名称不能为空")
    private String roleName;
    /**
     * 备注
     */
    private int type;

    @NotBlank(message="备注不能为空")
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 菜单ID列表
     */
    @TableField(exist=false)
    private List<Long> menuIdList;
}
