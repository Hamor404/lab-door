package com.villege.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.villege.common.constant.Constant;
import com.villege.common.utils.PageUtils;
import com.villege.dao.sys.PersonInfoMapper;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.PersonInfoVo;
import com.villege.service.sys.PersonInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PersonInfoServiceImpl extends ServiceImpl<PersonInfoMapper, PersonInfoVo> implements PersonInfoService {
    @Autowired
    private PersonInfoMapper personMapper;
    @Override
    public PageUtils getPageList(Map<String, Object> params) {
        long page = Long.parseLong(String.valueOf(params.get(Constant.PAGE)));
        long limit = Long.parseLong(String.valueOf(params.get(Constant.LIMIT)));
        Page<PersonInfoVo> pages = new Page<>(page, limit);
        pages.setSize(limit);
        pages.setCurrent(page);
        List<PersonInfoVo> pageList = personMapper.getPageList(pages,params);
        pages.setRecords(pageList);
        return new PageUtils(pages);
    }
    @Override
    public List<PersonInfoVo> getList(@Param("params") Map<String, Object> params){
        return personMapper.getList(params);
    }
    @Override
    public List<CommunityVo> getAgentCommunity(){
        return  personMapper.getAgentCommunity();
    }

    @Override
    public List<PersonInfoVo> getPersonByCommunityId(@Param("communityId") long communityId){
        return personMapper.getPersonByCommunityId(communityId);
    }
    @Override
    public CommunityVo isCommunity(@Param("communityName") String communityName){
        return  personMapper.isCommunity(communityName);
    }
    @Override
    public void newPerson(@Param("user") PersonInfoVo user){
        personMapper.newPerson(user);
    }
}
