package com.villege.modules.controller;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.villege.common.constant.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Swagger2配置
 *
 * @author 赖国荣 qq31343080,wx:15374363882
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config implements WebMvcConfigurer {

    public static final String SWAGGER_SCAN_PACKAGE = "com.villege.modules.controller.sys";

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("token").description("JwtToken")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("默认接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("管理系统接口 APIs")
                .description("企业级安全快速开发框架")
                .termsOfServiceUrl("https://starry.plus")
                .contact("赖国荣 qq31343080,wx:15374363882")
                .version("SpringBoot 2.2.6")
                .build();
    }

    private List<ApiKey> security() {
        return newArrayList(
                new ApiKey(Constant.TOKEN_HEADER, Constant.TOKEN_HEADER, "header")
        );
    }

}
