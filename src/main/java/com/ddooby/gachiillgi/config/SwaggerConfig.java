package com.ddooby.gachiillgi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {@Server(url = "http://localhost:8080"),
                @Server(url = "https://jaksim31.xyz")},

        info = @Info(
                title = "가치일기 API",
                description = "가치 읽을래?",
                version = "v1"))

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi nonSecurityGroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("Gachi Diary Open API Group")
                .pathsToMatch("/**")
                .build();
    }
}
