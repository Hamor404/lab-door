package com.villege.entity.tree;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统菜单
 *
 * @author 赖国荣 31343080@qq.com
 */
@Data
public class SysMenuTree extends BaseTree<SysMenuTree> implements Serializable {

    private Long id;

    private Long parentId;

    private String name;

    private String path;

    private Integer menuType;

    private String perms;

    private Integer status;

    private String icon;

    private Integer sort;

    private String hidden;
}
