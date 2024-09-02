package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.common.constant.Constant;
import com.villege.common.utils.PageUtils;
import com.villege.dao.sys.SysLogMapper;
import com.villege.entity.sys.SysLogEntity;
import com.villege.service.sys.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 系统日志
 *
 * @author 赖国荣 31343080@qq.com
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils selectByPage(Map<String, Object> params) {
        long page = Long.parseLong((String) params.get(Constant.PAGE));
        long limit = Long.parseLong((String) params.get(Constant.LIMIT));
        String username = (String) params.get("username");
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");
        String operation = (String) params.get("operation");
        Page<SysLogEntity> pages = new Page<>(page, limit);
        QueryWrapper<SysLogEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.like(StringUtils.isNotBlank(username), "username", username);
        wrapper.like(StringUtils.isNotBlank(operation), "operation", operation);

        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            wrapper.between("create_time", startDate, endDate);
        }
        IPage<SysLogEntity> list = baseMapper.selectPage(pages, wrapper);
        return new PageUtils(list);
    }

}



