package com.villege.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("inoutrecord")
public class InOutRecordVo implements Serializable {
    @TableId("inOutRecordId")
    private long inOutRecordId;
    @TableField("communityId")
    private long communityId;
    @TableField("personId")
    private long personId;

    @TableField("inTime")
    private Date inTime;
    @TableField("outTime")
    private Date outTime;

    @TableField("inPic")
    private String inPic;
    @TableField("outPic")
    private String outPic;

    @TableField(exist = false)
    private String extName;

    @TableField(exist = false)
    private String fileBase64;

    @TableField(exist = false)
    @ApiModelProperty("小区名称")
    private String communityName;

    @TableField(exist = false)
    @ApiModelProperty("所在楼栋")
    private String termName;

    @TableField(exist = false)
    @ApiModelProperty("房号")
    private String houseNo;

    @TableField(exist = false)
    @ApiModelProperty("姓名")
    private String userName;

    @TableField(exist = false)
    @ApiModelProperty("手机号码")
    private String mobile;

}
