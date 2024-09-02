package com.villege.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.villege.entity.tree.Meta;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理
 *
 * @author 赖国荣 31343080@qq.com
 */
@Data
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 菜单ID
     */
    @TableId
    private Long id;
    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单URL
     */
    private String path;
    /**
     * 类型     1：目录   2：菜单   3：按钮
     */
    private Integer menuType;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 是否为外链（0不是  1是）
     */
    private Integer isFrame;
    /**
     * 状态 0正常  1禁用
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;

    private String hidden;
    /**
     * vueTree属性
     */
    @TableField(exist=false)
    private Boolean alwaysShow;

    @Transient
    @TableField(exist=false)
    private Meta meta;

    @TableField(exist=false)
    private String redirect;

    private String component;

    @TableField(exist=false)
    private List<SysMenuEntity> children;
}
