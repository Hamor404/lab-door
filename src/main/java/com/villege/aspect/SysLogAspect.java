package com.villege.aspect;

import com.google.gson.Gson;
import com.villege.common.annotation.SysLog;
import com.villege.common.utils.HttpContextUtils;
import com.villege.common.utils.IPUtils;
import com.villege.entity.sys.SysLogEntity;
import com.villege.entity.sys.SysUserEntity;
import com.villege.service.sys.SysLogService;
import com.villege.shiro.ShiroUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志
 *
 * @author 赖国荣 31343080@qq.com
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 指定自定义注解
     */
    @Pointcut("@annotation(com.villege.common.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveSysLog(point, time);
        return result;
    }


    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLogEntity sysLogEntity = new SysLogEntity();
        SysLog log = method.getAnnotation(SysLog.class);
        if (log != null) {
            //注解上的描述
            sysLogEntity.setOperation(log.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogEntity.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = new Gson().toJson(args[0]);
            sysLogEntity.setParams(params);
        } catch (Exception e) {

        }

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        sysLogEntity.setIp(IPUtils.getIpAddr(request));
        sysLogEntity.setTime(time);
        sysLogEntity.setCreateTime(new Date());
        //登录用户信息
        SysUserEntity user = ShiroUtils.getUser();
        if (user != null) {
            sysLogEntity.setUsername(user.getUsername());
            sysLogService.save(sysLogEntity);
        }
    }
}
