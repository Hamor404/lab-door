package com.villege.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录表单
 *
 * @author 赖国荣 31343080@qq.com
 */
@Data
@ApiModel(value="登录对象", description="登录对象")
public class SysLoginEntity {

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "验证码Key")
    private String uuid;

}
