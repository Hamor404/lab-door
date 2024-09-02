package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.villege.entity.sys.CommunityVo;
import com.villege.entity.sys.PersonInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PersonInfoMapper extends BaseMapper<PersonInfoVo> {

    @Select({
            "<script>",
            "select u.*,g.communityName from personInfo u INNER JOIN community g on u.communityId=g.communityId where 1=1",
            "<if test=\"params.userName != null and params.userName.trim() != ''\">",
            "AND userName like concat('%',#{params.userName},'%')",
            "</if>",
            "<if test=\"params.mobile != null and params.mobile.trim() != ''\">",
            "AND u.mobile like concat('%',#{params.mobile},'%')",
            "</if>",
            "<if test=\"params.communityId>0\">",
            "AND u.communityId = ${params.communityId}",
            "</if>",
            "ORDER BY personId asc",
            "</script>"
    })
    List<PersonInfoVo> getPageList(Page pages, @Param("params") Map<String, Object> params);



    @Select({
            "<script>",
            "select u.*,g.communityName from personInfo u INNER JOIN community g on u.communityId=g.communityId where 1=1",
            "<if test=\"params.userName != null and params.userName.trim() != ''\">",
            "AND userName like concat('%',#{params.userName},'%')",
            "</if>",
            "<if test=\"params.mobile != null and params.mobile.trim() != ''\">",
            "AND u.mobile like concat('%',#{params.mobile},'%')",
            "</if>",
            "<if test=\"params.communityId>0\">",
            "AND u.communityId = ${params.communityId}",
            "</if>",
            "ORDER BY personId asc",
            "</script>"
    })
    List<PersonInfoVo> getList(@Param("params") Map<String, Object> params);

    @Select({"select * from community"})
    List<CommunityVo> getAgentCommunity();

    //按小区id获取人员信息
    @Select({
            "<script>",
            "select * from personInfo where communityId=#{communityId}",
            "</script>"
    })
    List<PersonInfoVo> getPersonByCommunityId(@Param("communityId") long communityId);

    //判断小区是否存在,导入人员信息使用
    @Select({
            "<script>",
            "select * from community where communityName=#{communityName}",
            "</script>"
    })
    CommunityVo isCommunity(@Param("communityName") String communityName);

    //更新人脸识别状态
    @Update({
            "<script>",
            "update personInfo set faceUrl=#{user.faceUrl},state=#{user.state} where personId=#{user.personId}",
            "</script>"
    })
    void newPerson(@Param("user") PersonInfoVo user);
}
