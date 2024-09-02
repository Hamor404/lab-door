package com.villege.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("personinfo")
@ApiModel(value="PersonInfoVo",description="人员信息")
public class PersonInfoVo implements Serializable {
    @TableId("personId")
    @ApiModelProperty("人员唯一ID")
    private long personId;
    @TableField("communityId")
    @ApiModelProperty("小区ID")
    private long communityId;
    @TableField("termName")
    @ApiModelProperty("所在楼栋")
    private String termName;

    @TableField("houseNo")
    @ApiModelProperty("房号")
    private String houseNo;

    @TableField("userName")
    @ApiModelProperty("姓名")
    private String userName;
    @TableField("sex")
    @ApiModelProperty("性别(男|女)")
    private String sex;
    @TableField("mobile")
    @ApiModelProperty("手机号码")
    private String mobile;

    @TableField("faceUrl")
    @ApiModelProperty("人脸图片地址")
    private String faceUrl;

    @TableField("personType")
    @ApiModelProperty("居住性质（常住居民，企业员工，租户）")
    private String personType;


    @TableField("state")
    @ApiModelProperty("状态（1未识别，2已识别）")
    private int state;

    @TableField("creater")
    @ApiModelProperty("创建人")
    private String creater;
    @TableField("createrTime")
    @ApiModelProperty("创建时间")
    private Date createrTime;

    @TableField("remark")
    @ApiModelProperty("备注")
    private String remark;

    @TableField(exist = false)
    @ApiModelProperty("小区名称")
    private String communityName;

    @ApiModelProperty("需要识别的Base64编码图片字符串")
    @TableField(exist = false)
    private String fileBase64;
    @ApiModelProperty("图片文件的扩展名")
    @TableField(exist = false)
    private String extName;


}
