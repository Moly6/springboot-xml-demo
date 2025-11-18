package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 配置类
 * Swagger UI 访问: http://localhost:8080/swagger-ui.html
 * Knife4j UI 访问: http://localhost:8080/doc.html (更美观的界面)
 */
@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("XML/JSON 转换 API 文档")
            .description("Spring Boot 实现的 XML 和 JSON 互相转换接口文档\n\n" +
                "支持两种 UI 界面:\n" +
                "- Swagger UI: /swagger-ui.html\n" +
                "- Knife4j UI: /doc.html (推荐,更美观)")
            .version("1.0.0")
            .contact(new Contact()
                .name("开发团队")
                .email("dev@example.com"))
            .license(new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
  }
}
