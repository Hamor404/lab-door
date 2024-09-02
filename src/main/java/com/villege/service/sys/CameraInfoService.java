package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.entity.sys.CameraInfoVo;

import java.util.List;
import java.util.Map;

public interface CameraInfoService extends IService<CameraInfoVo> {

    List<CameraInfoVo> getList(Map<String, Object> params);

}
