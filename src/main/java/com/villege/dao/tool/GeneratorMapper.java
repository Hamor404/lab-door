package com.villege.dao.tool;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Lewis 31343080@qq.com
 */
@Mapper
@Repository
public interface GeneratorMapper {

    /**
     * 后台分页
     *
     * @param params
     * @return
     */
    @Select({
            "<script>",
            "SELECT table_name tableName, engine, table_comment tableComment, create_time createTime, table_collation tableCollation from information_schema.tables",
            "where table_schema = (select database())",
            "AND table_name NOT LIKE 'qrtz_%'",
            "<if test=\"params.tableName != null and params.tableName.trim() != ''\">",
            "AND table_name like concat('%',#{params.tableName},'%')",
            "</if>",
            "order by create_time desc",
            "</script>"
    })
    List<Map<String, Object>> getPageList(@Param("params") Map<String, Object> params);

    @Select({
            "<script>",
            "SELECT table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables",
            "where table_schema = (select database()) and table_name = #{tableName}",
            "</script>"
    })
    Map<String, String> selectTable(String tableName);

    @Select({
            "<script>",
            "SELECT column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns",
            "where table_name = #{tableName} and table_schema = (select database()) order by ordinal_position",
            "</script>"
    })
    List<Map<String, String>> selectColumns(String tableName);

}
