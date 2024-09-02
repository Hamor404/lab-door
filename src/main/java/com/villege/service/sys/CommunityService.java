package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.common.utils.PageUtils;
import com.villege.entity.sys.CommunityVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommunityService extends IService<CommunityVo> {

    PageUtils getPageList(Map<String, Object> params);
    List<CommunityVo> getCommunityList();
    CommunityVo getCommunityName(@Param("communityName") String communityName);
    List<CommunityVo> getCommunityMap();

}
