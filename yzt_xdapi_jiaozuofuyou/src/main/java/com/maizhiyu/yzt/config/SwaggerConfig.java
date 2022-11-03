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


@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${swagger.enable}")
	private boolean swaggerEnable;

	@Bean
	public Docket createRestApi() {

		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("ypt")
				.description("YPT-XDAPI接口说明文档")
				.version("1.0")
				.build();

		return new Docket(SWAGGER_2)
				.enable(swaggerEnable)
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.maizhiyu"))
				.paths(PathSelectors.any())
				.build();
	}
}
