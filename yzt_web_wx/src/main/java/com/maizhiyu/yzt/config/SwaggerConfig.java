package com.maizhiyu.yzt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

/**
 * swagger文档
 *
 * @author wang
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${swagger.enable}")
	private boolean swaggerEnable;

	@Bean
	public Docket createRestApi() {

		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("一真堂适宜技术平台-微信端")
				.description("医院端接口说明文档")
				.version("0.1")
				.build();

		return new Docket(SWAGGER_2)
				.enable(swaggerEnable)
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com"))
				.paths(PathSelectors.any())
				.build();
	}

}
