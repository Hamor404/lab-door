package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.common.constant.Constant;
import com.villege.common.utils.PageUtils;
import com.villege.dao.sys.InOutRecordMapper;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.InOutRecordVo;
import com.villege.service.sys.InOutRecordService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("inOutRecordService")
public class InOutRecordServiceImpl extends ServiceImpl<InOutRecordMapper, InOutRecordVo> implements InOutRecordService {
    @Autowired
    private InOutRecordMapper inOutRecordMapper;
    @Override
    public PageUtils getPageList(Map<String, Object> params) {
        long page = Long.parseLong(String.valueOf(params.get(Constant.PAGE)));
        long limit = Long.parseLong(String.valueOf(params.get(Constant.LIMIT)));
        Page<InOutRecordVo> pages = new Page<>();
        pages.setSize(limit);
        pages.setCurrent(page);
        List<InOutRecordVo> pageList = inOutRecordMapper.getPageList(pages,params);
        pages.setRecords(pageList);
        return new PageUtils(pages);
    }

    @Override
    public List<InOutRecordVo> getExcelList(Map<String, Object> params){
        return inOutRecordMapper.getExcelList(params);
    }

    @Override
    public List<CommunityVo> getAgentCommunity(){
        return inOutRecordMapper.getAgentCommunity();
    }
    @Override
    public InOutRecordVo getInOutRecord(@Param("rec") InOutRecordVo rec){
        return inOutRecordMapper.getInOutRecord(rec);
    }
    @Override
    public List<CommunityVo> getPersonStat(long communityId){
        return inOutRecordMapper.getPersonStat(communityId);
    }
}

