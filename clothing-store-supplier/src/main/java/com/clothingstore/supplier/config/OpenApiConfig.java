package com.clothingstore.supplier.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clothing Store Supplier API")
                        .version("1.0.0")
                        .description("Supplier management API for Clothing Store"))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Local server")
                ));
    }
}
