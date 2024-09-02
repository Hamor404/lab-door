package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.common.constant.Constant;
import com.villege.common.utils.PageUtils;
import com.villege.dao.sys.CommunityMapper;
import com.villege.entity.sys.CommunityVo;
import com.villege.service.sys.CommunityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, CommunityVo> implements CommunityService {

    @Autowired
    CommunityMapper parkMapper;

    @Override
    public PageUtils getPageList(Map<String, Object> params) {
        long page = Long.parseLong(String.valueOf(params.get(Constant.PAGE)));
        long limit = Long.parseLong(String.valueOf(params.get(Constant.LIMIT)));
        Page<CommunityVo> pages = new Page<>();
        pages.setSize(limit);
        pages.setCurrent(page);
        List<CommunityVo> pageList = parkMapper.getPageList(pages,params);
        pages.setRecords(pageList);
        return new PageUtils(pages);
    }
    @Override
    public List<CommunityVo> getCommunityList(){
        return  parkMapper.getCommunityList();
    }

    @Override
    public CommunityVo getCommunityName( @Param("communityName") String communityName){
        return parkMapper.getCommunityName(communityName);
    }
    @Override
    public List<CommunityVo> getCommunityMap(){
        return parkMapper.getCommunityMap();
    }
}

