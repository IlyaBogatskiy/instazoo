package com.ibdev.instazoo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(
                new Info().title("InstaZoo API")
                        .description("Swagger documentation for InstaZoo application")
                        .version("v1.0.0")
                        .license(
                                new License().name("Apache 2.0")
                                        .url("http://springdoc.org")
                        )
        );
    }
}
