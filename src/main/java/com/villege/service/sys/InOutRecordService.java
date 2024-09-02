package com.villege.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.villege.common.utils.PageUtils;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.InOutRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InOutRecordService extends IService<InOutRecordVo> {
    PageUtils getPageList(Map<String, Object> params);

    List<InOutRecordVo> getExcelList(@Param("params")  Map<String, Object> params);

    List<CommunityVo> getAgentCommunity();
    //判断是否有进入信息
    InOutRecordVo getInOutRecord(@Param("rec") InOutRecordVo rec);
    //小区人数分类统计
    List<CommunityVo> getPersonStat(long communityId);
}
