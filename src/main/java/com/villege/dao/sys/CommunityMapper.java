package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.villege.entity.sys.CommunityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CommunityMapper extends BaseMapper<CommunityVo> {
    @Select({
            "<script>",
            "select c.*,(select count(*) from personinfo p where p.communityId= c.communityId ) personCnt from community c where 1=1 ",
            "<if test=\"params.communityName !=null and params.communityName !=''\">",
            "and c.communityName=#{params.communityName}",
            "</if>",
            "order by c.seq desc",
            "</script>"
    })
    List<CommunityVo> getPageList(Page pages, @Param("params") Map<String, Object> params);
    @Select({
            "<script>",
            "select * from community where 1=1 order by seq",
            "</script>"
    })
    List<CommunityVo> getCommunityList();

    @Select({
            "<script>",
            "select * from community where communityName=#{communityName} limit 0,1",
            "</script>"
    })
    CommunityVo getCommunityName(@Param("communityName") String communityName);
    @Select({
            "<script>",
            "select communityId,communityName,lng,lat from community where lng>0 and lat > 0",
            "</script>"
    })
    List<CommunityVo> getCommunityMap();


}
