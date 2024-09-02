package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.common.utils.PageUtils;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.PersonInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PersonInfoService extends IService<PersonInfoVo> {
    PageUtils getPageList(Map<String, Object> params);
    List<PersonInfoVo> getList(@Param("params") Map<String, Object> params);
    List<CommunityVo> getAgentCommunity();

    List<PersonInfoVo> getPersonByCommunityId(@Param("communityId") long communityId);

    CommunityVo isCommunity(@Param("communityName") String communityName);
    void newPerson(@Param("user") PersonInfoVo user);
}
