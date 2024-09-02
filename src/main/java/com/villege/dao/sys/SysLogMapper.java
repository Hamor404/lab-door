package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.villege.entity.sys.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 系统日志
 *
 * @author 赖国荣 31343080@qq.com
 */
@Mapper
@Repository
public interface SysLogMapper extends BaseMapper<SysLogEntity> {


}
