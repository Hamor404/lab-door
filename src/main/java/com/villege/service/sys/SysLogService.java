package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.common.utils.PageUtils;
import com.villege.entity.sys.SysLogEntity;

import java.util.Map;

/**
 * 系统日志
 *
 * @author 赖国荣 31343080@qq.com
 */
public interface SysLogService extends IService<SysLogEntity> {


    /**
     * 后台分页
     *
     * @param params
     * @return
     */
    PageUtils selectByPage(Map<String, Object> params);
}
