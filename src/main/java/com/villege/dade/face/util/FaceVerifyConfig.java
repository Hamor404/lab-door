package com.villege.dade.face.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(value="facecheck")
@Component
@Data
@ApiModel(value = "FaceVerifyConfig",description = "人脸核身系统参数描述")
public class FaceVerifyConfig {
	/**
	 *人脸核身
	 * **/
//	@ApiModelProperty("人脸核身iosbundleId")
//	private String iosbundleId;
//	@ApiModelProperty("人脸核身androidPkgName")
//	private String androidPkgName;
	@ApiModelProperty("人脸核身wbappid")
	private String wbappid;
	@ApiModelProperty("人脸核身secret")
	private String secret;
	@ApiModelProperty("人脸核身wbappIDLicense")
	private String wbappIDLicense;
	@ApiModelProperty("授权类别")
	private String grantType;
	@ApiModelProperty("版本")
	private String version;
	private String nonce;
}
