package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用程序入口
 */
@SpringBootApplication
public class DemoApplication {
  @Value("${server.port:}")
  private String portInstance;

  private static String port;

  @PostConstruct
  public void init() {
    port = portInstance;
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
    String url = "http://localhost:" + port;
    String healthUrl = url + "/api/health";
    String swaggerUrl = url + "/swagger-ui.html";
    String docUrl = url + "/doc.html";
    System.out.println("\n========================================");
    System.out.println(" 健康检查接口: " + healthUrl);
    System.out.println(" Swagger UI: " + swaggerUrl);
    System.out.println(" API 文档: " + docUrl);
    System.out.println("========================================\n");
  }
}
