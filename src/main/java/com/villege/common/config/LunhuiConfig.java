package com.villege.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置
 *
 * @author 赖国荣 31343080@qq.com
 */
@Component
@ConfigurationProperties(prefix = "system")
public class LunhuiConfig {

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }
}
