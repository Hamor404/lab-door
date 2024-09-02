package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.common.utils.PageUtils;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.ManualRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ManualRecordService extends IService<ManualRecordVo> {
    PageUtils getPageList(Map<String, Object> params);
    List<ManualRecordVo> getList(@Param("params") Map<String, Object> params);
    List<CommunityVo> getAgentCommunity();
    List<ManualRecordVo> getPersonByCommunityId(@Param("communityId") long communityId);
}
