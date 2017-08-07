/*
 * 文件名：SwaggerConfig.java
 * 版权：Copyright 2016 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2016年12月13日
 * 备注: 
 */
package com.cc.hbase.config;

import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 名称: SwaggerConfig.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2016年12月13日 下午3:48:54<br>
 * @version [版本号, V1.0]
 * @since 2016年12月13日 下午3:48:54
 * @author YanCui
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
//	public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.qf.quarkloanapi";
//	public static final String SWAGGER_SCAN_COMMON_PACKAGE = "com.qf.quarkloanapi.common.api";
//	public static final String SWAGGER_SCAN_CRM_PACKAGE = "com.qf.quarkloanapi.crm.api";
//	public static final String SWAGGER_SCAN_CAIMI_PACKAGE = "com.qf.quarkloanapi.esb.api";
//	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
	
    /**
     * 初始化swagger,扫描指定包
     * @return Docket  
     * @变更记录 2016年12月13日 下午6:41:27  YanCui 
     */
    @Bean
    public Docket swaggerSpringBootDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.genericModelSubstitutes(ResponseEntity.class)
        		.useDefaultResponseMessages(false)
        		.forCodeGeneration(false)
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .pathMapping("")// base，最终调用接口后会和paths拼接在一起
//                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))//通过requestmap过滤接口
//                .apis(doFilteringRules())//通过包名过滤接口
                .build();
    }

//    /**
//     * 设置过滤规则
//     * @return Predicate<RequestHandler>  
//     * @变更记录 2016年12月13日 下午6:40:11  YanCui 
//     */
//    @SuppressWarnings("unchecked")
//	private Predicate<RequestHandler> doFilteringRules() {
//        return Predicates.or(
//        		RequestHandlerSelectors.basePackage(SWAGGER_SCAN_COMMON_PACKAGE),
//        		RequestHandlerSelectors.basePackage(SWAGGER_SCAN_CAIMI_PACKAGE),
//        		RequestHandlerSelectors.basePackage(SWAGGER_SCAN_CRM_PACKAGE));
//    }
    
    /**
     * 项目描述基本信息
     * @return ApiInfo  
     * @变更记录 2016年12月13日 下午6:39:56  YanCui 
     */
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()  
	    	.title("hbase DEMO API")
	        .description("处理hbase DEMO api请求")
	        .license("")
	        .licenseUrl("")
	        .termsOfServiceUrl("")
	        .version("0.1")
	        .contact(new Contact("","", ""))
	        .build();
    }

}