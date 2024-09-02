package com.villege.modules.base;

import com.villege.entity.sys.SysUserEntity;
import com.villege.shiro.ShiroUtils;

/**
 * 基类控制器
 *
 * @author Lewis 10228545@qq.com
 */
public abstract class AbstractController {

    protected SysUserEntity getUser() {
        return ShiroUtils.getUser();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }

}
