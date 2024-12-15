package com.heliosx_consultation.heliosx_consultation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Heliosx Consultation API")
                        .version("1.0")
                        .description("API documentation for Heliosx Consultation"));
    }
}