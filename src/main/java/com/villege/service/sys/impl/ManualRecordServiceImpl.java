package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.common.constant.Constant;
import com.villege.common.utils.PageUtils;
import com.villege.dao.sys.ManualRecordMapper;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.ManualRecordVo;
import com.villege.service.sys.ManualRecordService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManualRecordServiceImpl extends ServiceImpl<ManualRecordMapper, ManualRecordVo> implements ManualRecordService {
    @Autowired
    private ManualRecordMapper personMapper;
    @Override
    public PageUtils getPageList(Map<String, Object> params) {
        long page = Long.parseLong(String.valueOf(params.get(Constant.PAGE)));
        long limit = Long.parseLong(String.valueOf(params.get(Constant.LIMIT)));
        Page<ManualRecordVo> pages = new Page<>(page, limit);
        pages.setSize(limit);
        pages.setCurrent(page);
        List<ManualRecordVo> pageList = personMapper.getPageList(pages,params);
        pages.setRecords(pageList);
        return new PageUtils(pages);
    }
    @Override
    public List<ManualRecordVo> getList(@Param("params") Map<String, Object> params){
        return personMapper.getList(params);
    }
    @Override
    public List<CommunityVo> getAgentCommunity(){
        return  personMapper.getAgentCommunity();
    }

    @Override
    public List<ManualRecordVo> getPersonByCommunityId(@Param("communityId") long communityId){
        return personMapper.getPersonByCommunityId(communityId);
    }
}
