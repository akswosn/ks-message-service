package com.forlks.ksmessage.config
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@OpenAPIDefinition(info = Info(title = "Ks Message App", description = "Ks Message app api명세", version = "v1"))
@Configuration
class SwaggerConfig {
    @Bean
    fun messageOpenApi(): GroupedOpenApi {
        val paths = arrayOf("/api/v1/**")
        return GroupedOpenApi.builder()
            .group("Message API v1")
            .pathsToMatch(*paths)
            .build()
    }
}
