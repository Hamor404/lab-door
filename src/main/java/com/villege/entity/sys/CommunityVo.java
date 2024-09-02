package com.villege.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("community")
public class CommunityVo implements Serializable {
    @TableId("communityId")
    private long communityId;
    @TableField("communityName")
    @NotBlank(message="小区名称不能为空")
    private String communityName;

    @TableField("termCount")
    private int termCount; //楼栋数量

    @TableField("seq")
    private long seq;
    @TableField("creater")
    private String creater;
    @TableField("createrTime")
    private Date createrTime;

    @TableField("lng")
    private float lng;  //经度
    @TableField("lat")
    private float lat; //纬度
    //小区人数
    @TableField(exist = false)
    private int personCnt;
    //做echarts统计时使用
    @TableField(exist = false)
    @ApiModelProperty("小区人数分类统计")
    private String sex;

}
