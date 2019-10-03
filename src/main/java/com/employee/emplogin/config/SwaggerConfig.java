package com.employee.emplogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * ** Indicates that Swagger support should be enabled.
 *
 * This should be applied to a Spring java config and should have an
 * accompanying '@Configuration' annotation.
 *
 * Loads all required beans defined in @see SpringSwaggerConfig
 *
 * *
 * 
 * @author 764432
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * Creates a new instance of {@code SimplePluginMetadata.
	 * 
	 * @return
	 */
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.employee.emplogin.controller")).paths(regex("/.*"))
				.build();
	}
}
