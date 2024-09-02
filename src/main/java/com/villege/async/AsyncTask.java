package com.villege.async;

import com.villege.entity.sys.SysLogEntity;
import com.villege.service.sys.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步执行类
 *
 * @author 赖国荣 31343080@qq.com
 */
@Component
public class AsyncTask {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 异步获取IP地址信息
     *
     * @param id
     * @param ip
     */
    @Async
    public void getIpAddress(Long id, String ip) {
        SysLogEntity log = new SysLogEntity();
        log.setId(id);
        sysLogService.updateById(log);
    }
}
