package com.villege.modules.controller.sys;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.villege.common.config.LunhuiConfig;
import com.villege.common.constant.Constant;
import com.villege.common.utils.JwtUtils;
import com.villege.common.utils.R;
import com.villege.common.utils.RedisUtils;
import com.villege.entity.sys.SysLoginEntity;
import com.villege.entity.sys.SysUserEntity;
import com.villege.modules.base.AbstractController;
import com.villege.service.sys.SysUserService;
import com.villege.shiro.ShiroUtils;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录接口
 *
 * @author Lewis 10228545@qq.com
 */
@Slf4j
@Api(tags = "登录接口")
@RestController
public class LoginController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private LunhuiConfig lunhuiConfig;


    @ApiOperation("获取验证码")
    @GetMapping("/captcha")
    public R captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String code = specCaptcha.text().toLowerCase();
        String uuid = IdUtil.simpleUUID();
        // 存入redis并设置过期时间为2分钟
        redisUtils.set(uuid, code, 120);
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("uuid", uuid);
        map.put("code", code);
        map.put("captcha", specCaptcha.toBase64());
        return R.ok().put("data", map);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(@RequestBody SysLoginEntity sysLoginEntity) {
        try {
            String captcha = (String) redisUtils.get(sysLoginEntity.getUuid());
            if (StringUtils.isEmpty(captcha)) {
                return R.error("验证码已经过期");
            }
            if (!sysLoginEntity.getCaptcha().equalsIgnoreCase(captcha)) {
                return R.error("验证码不正确");
            }
            SysUserEntity user = sysUserService.selectByUsername(sysLoginEntity.getUsername());
            //判断账号是否已经存在
            if (user == null) {
                return R.error("账户不存在");
            }
            //判断用户输入的密码是否正确
            String pwd = SecureUtil.sha256(sysLoginEntity.getPassword());
            if (!pwd.equals(user.getPassword())) {
                return R.error("账号或密码错误");
            }
            if (user.getStatus() == Constant.CLOSE) {
                return R.error("账号已被锁定,请联系管理员!");
            }
//            sysUserService.updateById(user);
            String token = jwtUtils.createToken(String.valueOf(user.getUserId()));
            redisUtils.set(lunhuiConfig.getName() + ":sysUser:" + user.getUserId(), token, jwtUtils.getExpire());
            Map<String, Object> map = new HashMap<>(2);
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            return R.ok("登录成功").put("data", map);
        } catch (Exception e) {
            log.error(">>>>>>>>登录异常,信息:", e);
            return R.error(e.getMessage());
        }
    }


    @ApiOperation("快速登录")
    @PostMapping("/fastLogin")
    public R fastLogin() {
        try {
            SysUserEntity user = sysUserService.getById(1);
            String token = jwtUtils.createToken(String.valueOf(user.getUserId()));
            redisUtils.set(lunhuiConfig.getName() + ":sysUser:" + user.getUserId(), token, jwtUtils.getExpire());
            Map<String, Object> map = new HashMap<>(2);
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            return R.ok("登录成功").put("data", map);
        } catch (Exception e) {
            log.error(">>>>>>>>登录异常,信息:", e);
            return R.error(e.getMessage());
        }
    }


    @ApiOperation("退出")
    @PostMapping("/logout")
    public R logout() {
        ShiroUtils.logout();
        return R.ok();
    }

}
