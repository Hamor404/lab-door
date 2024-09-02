package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.InOutRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface InOutRecordMapper extends BaseMapper<InOutRecordVo> {
    @Select({
            "<script>",
            "select r.*,c.communityName,p.termName,p.houseNo,p.userName from inoutrecord r INNER JOIN community c on c.communityId=r.communityId inner join personinfo p on p.personId = r.personId where 1=1 ",
            "<if test=\"params.communityId>0\">",
            "and r.communityId=#{params.communityId}",
            "</if>",
            "<if test=\"params.userName !=null and params.userName !=''\">",
            "and p.userName=#{params.userName}",
            "</if>",
            "<if test=\"params.startDate !=null and params.startDate !='' and params.endDate !=null and params.endDate !=''\">",
            "and r.inTime between #{params.startDate} and #{params.endDate}",
            "</if>",
            "order by r.communityId,r.inOutRecordId desc",
            "</script>"
    })
    List<InOutRecordVo> getPageList(Page pages, @Param("params") Map<String, Object> params);
    @Select({
            "<script>",
            "select r.*,c.communityName,p.termName,p.houseNo,p.userName from inoutrecord r INNER JOIN community c on c.communityId=r.communityId inner join personinfo p on p.personId = r.personId where 1=1 ",
            "<if test=\"params.communityId>0\">",
            "and r.communityId=#{params.communityId}",
            "</if>",
            "<if test=\"params.userName !=null and params.userName !=''\">",
            "and p.userName=#{params.userName}",
            "</if>",
            "<if test=\"params.startDate !=null and params.startDate !='' and params.endDate !=null and params.endDate !=''\">",
            "and r.inTime between #{params.startDate} and #{params.endDate}",
            "</if>",
            "order by r.agentId,r.communityId,r.inOutRecordId desc",
            "</script>"
    })
    List<InOutRecordVo> getExcelList(@Param("params")  Map<String, Object> params);

    @Select({"select * from community order by communityId" })
    List<CommunityVo> getAgentCommunity();

    @Select({
            "<script>",
            "select * from inoutrecord where communityId=#{rec.communityId} and personId=#{rec.personId} and outTime is null ",
            "</script>"
    })
    InOutRecordVo getInOutRecord(@Param("rec") InOutRecordVo rec);

    //按小区分类统计人数
    @Select({
            "<script>",
            "select sex,count(*) personCnt from personinfo where communityId=#{communityId} group by sex",
            "</script>"
    })
    List<CommunityVo> getPersonStat(long communityId);
}
