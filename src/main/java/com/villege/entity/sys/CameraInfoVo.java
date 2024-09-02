package com.villege.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data

@TableName("camerainfo")
public class CameraInfoVo implements Serializable {

    @TableId("cameraInfoId")
    private long cameraInfoId;


    @TableField("communityId")
    private long communityId ;

    @TableField("cameraName")
    private String cameraName;

    @TableField("macID")
    private String macID ;

    //出入方向（1进，2出）
    @TableField("direction")
    private int direction ;

    //状态（1启用，2禁用）
    @TableField("state")
    private int state ;

    @TableField("seq")
    private long seq;
    @TableField("creater")
    private String creater;
    @TableField("createrTime")
    private Date createrTime;

    @TableField("remark")
    private String remark;

    @TableField(exist = false)
    private String communityName;


}
