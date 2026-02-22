package org.codeit.sb06.team03.mopl.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("Bearer Authentication")
                                .addList("CSRF")
                )
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer Authentication", createTokenSecurityScheme())
                                .addSecuritySchemes("CSRF", createCsrfSecurityScheme())
                )
                .info(
                        new Info()
                                .title("Mopl REST API")
                                .description("codeit-sb06 team03 mopl")
                                .version("1.0")
                );
    }

    private SecurityScheme createTokenSecurityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private SecurityScheme createCsrfSecurityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-XSRF-TOKEN");
    }
}
