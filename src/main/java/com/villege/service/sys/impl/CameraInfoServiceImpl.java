package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.dao.sys.CameraInfoMapper;
import com.villege.entity.sys.CameraInfoVo;
import com.villege.service.sys.CameraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CameraInfoServiceImpl extends ServiceImpl<CameraInfoMapper, CameraInfoVo> implements CameraInfoService {

    @Autowired
    CameraInfoMapper CameraInfoMapper;

    @Override
    public List<CameraInfoVo> getList(Map<String, Object> params) {
        List<CameraInfoVo> cameraList = CameraInfoMapper.getList(params);
        return cameraList;
    }

}

