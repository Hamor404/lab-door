package com.villege.entity.tree;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 基层树形结构实体类，所有需要实现树节点的，都需要继承该类
 *
 * @author Lewis 31343080@qq.com
 */
@Data
public class BaseTree<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 子节点列表
     */
    private List<T> children = new ArrayList<>();

}