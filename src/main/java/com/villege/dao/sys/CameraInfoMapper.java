package com.villege.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.villege.entity.sys.CameraInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CameraInfoMapper extends BaseMapper<CameraInfoVo> {
    @Select({
            "<script>",
            "select g.communityName,p.* from camerainfo p INNER JOIN community g on p.communityId=g.communityId where 1=1 ",
            "and p.communityId=${params.communityId}",
            "order by cameraInfoId",
            "</script>"
    })
    List<CameraInfoVo> getList(@Param("params") Map<String, Object> params);
}
