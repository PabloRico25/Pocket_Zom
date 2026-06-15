package com.example.partida.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI partidaOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("API 2026 Partida")
                .version("1.0")
                .description("Documentacion de la API sobre el sistema de Partida"));
    }

}
