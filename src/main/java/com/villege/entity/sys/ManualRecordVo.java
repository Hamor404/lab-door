package com.villege.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("manualrecord")
public class ManualRecordVo implements Serializable {
    @TableId("manualRecordId")
    @ApiModelProperty("唯一ID,主键")
    private long manualRecordId;
    @TableField("communityId")
    @ApiModelProperty("小区ID")
    private long communityId;

    @TableField("visitor")
    @ApiModelProperty("访客姓名")
    private String visitor;

    @TableField("mobile")
    @ApiModelProperty("联系电话")
    private String mobile;


    @TableField("cardNo")
    @ApiModelProperty("身份证号码")
    private String cardNo;


    @TableField("termName")
    @ApiModelProperty("楼栋名称")
    private String termName;

    @TableField("houseNo")
    @ApiModelProperty("房号")
    private String houseNo;

    @TableField("interviewee")
    @ApiModelProperty("受访者")
    private String interviewee;

    @TableField("remark")
    @ApiModelProperty("备注")
    private String remark;

    @TableField("inTime")
    @ApiModelProperty("进入时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date inTime;

    @TableField("outTime")
    @ApiModelProperty("离开时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date outTime;

    @TableField("userName")
    @ApiModelProperty("操作人")
    private String userName;

    @TableField("signTime")
    @ApiModelProperty("操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date signTime;

    @TableField(exist = false)
    @ApiModelProperty("小区名称")
    private String communityName;

}
