package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.entity.sys.SysMenuEntity;
import com.villege.entity.tree.Router;
import com.villege.entity.tree.SysMenuTree;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author Lewis 10228545@qq.com
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 获取用户可以操作的按钮列表
     *
     * @param userId
     * @return
     */
    Set<String> getUserButtonsList(Long userId);

    /**
     * 获取树形列表
     *
     * @return
     */
    List<SysMenuTree> getListTree();

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenuEntity> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<Router> buildMenus(List<SysMenuEntity> menus);

}
