package com.musinsa.platform.biz.core.api.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CATALOG API")
                        .version("1.0")
                        .description("무신사(상품 카탈로그 개발팀) 상품 API")
                );
    }
}
