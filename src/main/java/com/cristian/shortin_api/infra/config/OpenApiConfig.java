package com.cristian.shortin_api.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Shortin API",
        description = "Uma api simples para encurtar URLs",
        license = @License(
                name = "MIT",
                url = "https://github.com/cristian-95/shortin-api/blob/master/LICENSE"
        )
))
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi urlApi() {
        return GroupedOpenApi.builder()
                .group("api")
                .packagesToScan("com.cristian.shortin_api.url.api")
                .build();
    }

}
