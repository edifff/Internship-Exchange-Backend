package ru.rsreu.projectmanagment.identityservice.identityservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Internship Marketplace API")
                        .version("1.0.0")
                        .description("API биржи стажировок: аутентификация, профили, управление пользователями")
                        .contact(new Contact()
                                .name("edifff")
                                .email("mishapronin2015@gmail.com")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createBearerScheme()));
    }
    private SecurityScheme createBearerScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .name("bearerAuth")
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Вставь access-токен (без префикса 'Bearer ')");
    }
}
