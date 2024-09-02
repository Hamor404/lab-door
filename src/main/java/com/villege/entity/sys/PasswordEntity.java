package com.villege.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码
 *
 * @author Lewis 10228545@qq.com
 */
@Data
@ApiModel(value = "修改密码")
public class PasswordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "原密码")
    private String password;

    @ApiModelProperty(value = "新密码")
    private String newPassword;

}