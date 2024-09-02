package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.common.constant.Constant;
import com.villege.dao.sys.SysMenuMapper;
import com.villege.entity.sys.SysMenuEntity;
import com.villege.entity.tree.Meta;
import com.villege.entity.tree.Router;
import com.villege.entity.tree.SysMenuTree;
import com.villege.entity.tree.TreeUtils;
import com.villege.service.sys.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 菜单管理
 *
 * @author Lewis 31343080@qq.com
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @Override
    public Set<String> getUserButtonsList(Long userId) {
        Set<String> buttonsList;
//        if (userId == Constant.SUPER_ADMIN) {
//            buttonsList = baseMapper.getAllButtonsList();
//        } else {
            buttonsList = baseMapper.getUserButtonsList(userId);
//        }
        return buttonsList;
    }

    @Override
    public List<SysMenuTree> getListTree() {
        List<SysMenuTree> list = baseMapper.getList();
        return TreeUtils.build(list, 0L);
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenuEntity> selectMenuTreeByUserId(Long userId) {
        List<SysMenuEntity> menus = null;
//        if (userId == Constant.SUPER_ADMIN) {
//            menus = baseMapper.selectMenuTreeAll();
//        } else {
            menus = baseMapper.selectMenuTreeByUserId(userId);
//        }
        return getChildPerms(menus, 0);
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenuEntity> getChildPerms(List<SysMenuEntity> list, int parentId) {
        List<SysMenuEntity> returnList = new ArrayList<SysMenuEntity>();
        for (Iterator<SysMenuEntity> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenuEntity t = (SysMenuEntity) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenuEntity> list, SysMenuEntity t) {
        // 得到子节点列表
        List<SysMenuEntity> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenuEntity tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysMenuEntity> it = childList.iterator();
                while (it.hasNext()) {
                    SysMenuEntity n = (SysMenuEntity) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuEntity> list, SysMenuEntity t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenuEntity> getChildList(List<SysMenuEntity> list, SysMenuEntity t) {
        List<SysMenuEntity> subList = new ArrayList<SysMenuEntity>();
        Iterator<SysMenuEntity> it = list.iterator();
        while (it.hasNext()) {
            SysMenuEntity n = (SysMenuEntity) it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                subList.add(n);
            }
        }
        return subList;
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<Router> buildMenus(List<SysMenuEntity> menus) {
        List<Router> routers = new LinkedList<Router>();
        for (SysMenuEntity menu : menus) {
            Router router = new Router();
            router.setName(StringUtils.capitalize(menu.getPath()));
            router.setHidden(menu.getHidden());
            router.setPath(getRouterPath(menu));
            router.setComponent(StringUtils.isEmpty(menu.getComponent()) ? "Layout" : menu.getComponent());
            router.setMeta(new Meta(menu.getName(), menu.getIcon()));

            List<SysMenuEntity> cMenus = menu.getChildren();
            if (cMenus != null && !cMenus.isEmpty() && cMenus.size() > 0 && menu.getMenuType() == Constant.MenuType.CATALOG.getValue()) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenuEntity menu) {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录
        if (0 == menu.getParentId() && 0 == menu.getIsFrame()) {
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }

}
