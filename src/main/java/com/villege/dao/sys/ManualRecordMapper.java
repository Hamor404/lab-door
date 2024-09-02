package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.ManualRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ManualRecordMapper extends BaseMapper<ManualRecordVo> {

    @Select({
            "<script>",
            "select u.*,g.communityName from manualrecord u INNER JOIN community g on u.communityId=g.communityId where 1=1",
            "<if test=\"params.visitor != null and params.visitor.trim() != ''\">",
            "AND visitor like concat('%',#{params.visitor},'%')",
            "</if>",
            "<if test=\"params.mobile != null and params.mobile.trim() != ''\">",
            "AND u.mobile like concat('%',#{params.mobile},'%')",
            "</if>",
            "<if test=\"params.communityId>0\">",
            "AND u.communityId = ${params.communityId}",
            "</if>",
            "ORDER BY manualRecordId desc",
            "</script>"
    })
    List<ManualRecordVo> getPageList(Page pages, @Param("params") Map<String, Object> params);



    @Select({
            "<script>",
            "select u.*,g.communityName from manualrecord u INNER JOIN community g on u.communityId=g.communityId where 1=1",
            "<if test=\"params.visitor != null and params.visitor.trim() != ''\">",
            "AND visitor like concat('%',#{params.visitor},'%')",
            "</if>",
            "<if test=\"params.mobile != null and params.mobile.trim() != ''\">",
            "AND u.mobile like concat('%',#{params.mobile},'%')",
            "</if>",
            "<if test=\"params.communityId>0\">",
            "AND u.communityId = ${params.communityId}",
            "</if>",
            "ORDER BY manualRecordId desc",
            "</script>"
    })
    List<ManualRecordVo> getList(@Param("params") Map<String, Object> params);

    @Select({"select * from community"})
    List<CommunityVo> getAgentCommunity();

    //按小区id获取人员信息
    @Select({
            "<script>",
            "select * from manualrecord where communityId=#{communityId}",
            "</script>"
    })
    List<ManualRecordVo> getPersonByCommunityId(@Param("communityId") long communityId);

}
