package fr.istic.m2.mcq_api.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * The class to configure openAPI documentation, to describe the REST API.
 */
@Configuration
public class SwaggerConfig {
    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-apis")
                .pathsToMatch("/**")
        .build();
    }
    @Bean
    OpenAPI customOpenAPIO() {
            return new OpenAPI()
                    .info(new Info().title("QCM APP - API").version("1.0"))
                    .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                    .components(
                            new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme ()
                                .type (SecurityScheme.Type.HTTP)
                                .scheme ("bearer").bearerFormat ("JWT" ))
                    );
}
}
