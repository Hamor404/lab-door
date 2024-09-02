package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.dao.sys.SysUserRoleMapper;
import com.villege.entity.sys.SysUserRoleEntity;
import com.villege.service.sys.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色用户关系
 *
 * @author 赖国荣 31343080@qq.com
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        Map<String, Object> map = new HashMap<>(16);
        map.put("user_id", userId);
        this.removeByMap(map);

        //用户没有一个角色权限的情况
        if (roleIdList == null || roleIdList.size() == 0) {
            return;
        }

        //保存角色用户关系
        for (Long roleId : roleIdList) {
            SysUserRoleEntity entity = new SysUserRoleEntity();
            entity.setUserId(userId);
            entity.setRoleId(roleId);
            baseMapper.insert(entity);
        }
    }

    @Override
    public void deleteByRoleIds(Long[] roleIds) {
        baseMapper.deleteByRoleIds(roleIds);
    }


    @Override
    public List<Long> getRoleIdList(Long userId) {
        return baseMapper.getRoleIdList(userId);
    }

}
