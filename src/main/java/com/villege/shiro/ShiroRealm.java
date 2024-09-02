package com.villege.shiro;

import com.villege.common.config.LunhuiConfig;
import com.villege.common.constant.Constant;
import com.villege.common.utils.JwtUtils;
import com.villege.common.utils.RedisUtils;
import com.villege.entity.sys.SysUserEntity;
import com.villege.service.sys.SysRoleService;
import com.villege.service.sys.SysUserService;
import com.villege.shiro.jwt.JwtToken;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 * @author Lewis 31343080@qq.com
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtil;
    @Autowired
    private LunhuiConfig lunhuiConfig;


    /**
     * JwtToken 类型的Token，如果在JwtFilter类中返回的是UsernamePasswordToken，那么将会报如下错误信息：
     * Please ensure that the appropriate Realm implementation is configured correctly or
     * that the realm accepts AuthenticationTokens of this type.JwtFilter.isAccessAllowed()
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    /**
     * 授权(只有当需要检测用户权限的时候才会调用此方法)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("=================校验用户权限=================");
        SysUserEntity sysUserEntity = (SysUserEntity) principals.getPrimaryPrincipal();
        Long userId = sysUserEntity.getUserId();
        // 设置用户拥有的角色集合，比如“超级管理员,测试员”
        Set<String> roleSet = sysRoleService.getUserRoles(userId);
        // 设置用户拥有的权限集合，比如“sys:role:add,sys:user:add”
        Set<String> permissionSet = sysUserService.getUserMenus(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleSet);
        info.addStringPermissions(permissionSet);
        log.debug("=================权限校验完成=================");
        return info;
    }


    /**
     * 认证(登录时调用,使用此方法进行用户名正确与否验证)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getPrincipal();

        // 校验token有效性
        Claims claims = jwtUtils.getClaimByToken(token);
        if (claims == null || !jwtUtils.validateToken(token)) {
            throw new AuthenticationException(jwtUtils.getHeader() + "非法无效!");
        }
        String userId = claims.getSubject();
        String oldToken = (String) redisUtil.get(lunhuiConfig.getName() + ":sysUser:" + userId);
        if (!oldToken.equals(token)) {
            throw new AuthenticationException(jwtUtils.getHeader() + "过期，请重新登录!");
        }
        // 校验token是否过期
        if (!jwtTokenRefresh(userId)) {
            throw new AuthenticationException(jwtUtils.getHeader() + "过期，请重新登录!");
        }
        // 查询用户信息
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        if (sysUserEntity == null) {
            throw new AuthenticationException("用户不存在!");
        }
        // 判断用户状态
        if (sysUserEntity.getStatus().equals(Constant.CLOSE)) {
            throw new AuthenticationException("账号已被锁定,请联系管理员!");
        }
        return new SimpleAuthenticationInfo(sysUserEntity, token, getName());
    }


    /**
     * JwtToken刷新生命周期 （解决用户一直在线操作，突然token失效问题）
     * 1、登录成功后将用户的JWT生成的token存储到redis中
     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
     * 3、当用户请求时，token值还在生命周期内，过期时间重新计算
     * 4、当用户请求时，token值已经超时，但该token对应redis中的key还存在，则表示该用户一直在操作只是token失效了，则会重新生成token并覆盖redis中的值，过期时间重新计算
     * 5、当用户请求时，token值已经超时，并在redis中不存在对应的key，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
     * 7、注：当前端接收到Response的Header中的token值会存储起来，作为以后请求token使用
     *
     * @param userId
     * @return
     */
    public boolean jwtTokenRefresh(String userId) {
        // 从redis中获取token
        String redisToken = (String) redisUtil.get(lunhuiConfig.getName() + ":sysUser:" + userId);
        if (StringUtils.isNotBlank(redisToken)) {
            // 校验token是否过期
            if (jwtUtils.isTokenExpired(redisToken)) {
                // 生成新的token
                String newToken = jwtUtils.createToken(userId);
                redisUtil.set(lunhuiConfig.getName() + ":sysUser:" + userId, newToken, jwtUtils.getExpire());
            } else {
                // 更新token的过期时间
                redisUtil.expire(lunhuiConfig.getName() + ":sysUser:" + userId, jwtUtils.getExpire());
            }
            return true;
        }
        return false;
    }
}
