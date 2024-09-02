package com.villege.service.sys.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.common.constant.Constant;
import com.villege.common.utils.PageUtils;
import com.villege.dao.sys.SysMenuMapper;
import com.villege.dao.sys.SysUserMapper;
import com.villege.dao.sys.SysUserRoleMapper;
import com.villege.entity.sys.SysUserEntity;
import com.villege.service.sys.SysUserRoleService;
import com.villege.service.sys.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统用户
 *
 * @author 赖国荣 31343080@qq.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    protected SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    protected SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public PageUtils selectByPage(Map<String, Object> params) {
        long page = Long.parseLong((String) params.get(Constant.PAGE));
        long limit = Long.parseLong((String) params.get(Constant.LIMIT));
        Page<SysUserEntity> pages = new Page<>(page, limit);
        List<SysUserEntity> list = baseMapper.getPageList(pages, params);
        pages.setRecords(list);
        return new PageUtils(pages);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUserEntity entity) {
        //密码加密
        entity.setPassword(SecureUtil.sha256(entity.getPassword()));
        //保存用户
        baseMapper.insert(entity);
        //保存角色用户关系
        sysUserRoleService.saveOrUpdate(entity.getUserId(), entity.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUserEntity entity) {
        SysUserEntity user = baseMapper.getUserById(entity.getUserId());
        //密码加密
        if (StringUtils.isBlank(entity.getPassword())) {
            entity.setPassword(user.getPassword());
//            entity.setPassword(null);
        } else {
            String password = SecureUtil.sha256(entity.getPassword());
            entity.setPassword(password);
        }
        //更新用户
        baseMapper.updateById(entity);
        //保存角色用户关系
        sysUserRoleService.saveOrUpdate(entity.getUserId(), entity.getRoleIdList());
    }

    @Override
    public Set<String> getUserMenus(Long userId) {
        List<String> permsList;
        permsList = sysMenuMapper.selectMenuByUserId(userId);
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }


    @Override
    public SysUserEntity selectByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    @DS("slave")
    public List<SysUserEntity> dynamicDataSource() {
        return baseMapper.selectList(null);
    }

    @Override
    public void updatePassword(Long userId, String newPassword) {
        String password = SecureUtil.sha256(newPassword);
        baseMapper.updatePassword(userId, password);
    }

    @Override
    public SysUserEntity getUserById(long userId) {

        return sysUserMapper.getUserById(userId);
    }
}
