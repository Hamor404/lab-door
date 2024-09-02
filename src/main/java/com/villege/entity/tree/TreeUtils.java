package com.villege.entity.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类
 *
 * @author Lewis 10228545@qq.com
 */
public class TreeUtils {

    /**
     * 根据pid，构建树节点
     */
    public static <T extends BaseTree> List<T> build(List<T> treeNodes, Long parentId) {
        List<T> treeList = new ArrayList<>();
        for(T treeNode : treeNodes) {
            //判断对象是否为根节点
            if (parentId.equals(treeNode.getParentId())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }
        return treeList;
    }

    /**
     * 查找子节点
     */
    private static <T extends BaseTree> T findChildren(List<T> treeNodes, T rootNode) {
        for(T treeNode : treeNodes) {
            if(rootNode.getId().equals(treeNode.getParentId())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 构建树节点
     */
    public static <T extends BaseTree> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<>();
        Map<Long, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
        for(T treeNode : treeNodes){
            nodeMap.put(treeNode.getId(), treeNode);
        }

        for(T node : nodeMap.values()) {
            T parent = nodeMap.get(node.getParentId());
            if(parent != null && !(node.getId().equals(parent.getId()))){
                parent.getChildren().add(node);
                continue;
            }
            result.add(node);
        }
        return result;
    }

}