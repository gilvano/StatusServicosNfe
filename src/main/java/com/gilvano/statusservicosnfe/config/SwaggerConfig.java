package com.gilvano.statusservicosnfe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI SwaggerConfig() {
        return new OpenAPI()
                .info(new Info().title("Status Serviço NFe API")
                        .description("API de consulta do status dos serviços da NFe")
                        .version("v0.0.1"));
    }
}
