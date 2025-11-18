# Swagger API 文档配置指南

## ✅ 已完成的配置

### 1️⃣ 添加依赖 (pom.xml)

```xml
<properties>
    <springdoc.version>2.2.0</springdoc.version>
</properties>

<dependencies>
    <!-- SpringDoc OpenAPI 3 (适配 Spring Boot 3.x) -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>${springdoc.version}</version>
    </dependency>
</dependencies>
```

**注意**: ❌ **不要使用** `springfox-boot-starter` 和 `knife4j-spring-boot-starter`,它们**不支持 Spring Boot 3.x**!

### 2️⃣ 创建配置类 (OpenApiConfig.java)

```java
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("XML/JSON 转换 API 文档")
                .description("Spring Boot 实现的 XML 和 JSON 互相转换接口文档")
                .version("1.0.0")
                .contact(new Contact()
                    .name("开发团队")
                    .email("dev@example.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
```

### 3️⃣ Controller 添加注解

```java
@RestController
@RequestMapping("/api")
@Tag(name = "XML/JSON 转换接口", description = "提供 XML 和 JSON 格式互相转换的相关接口")
public class DemoController {

    @Operation(
        summary = "JSON 字符串转 XML 字符串",
        description = "接收 JSON 格式的字符串,转换为 XML 格式返回"
    )
    @ApiResponse(responseCode = "200", description = "成功转换为 XML")
    @PostMapping("/jsonToXml")
    public String jsonToXml(@RequestBody String jsonString) {
        // ...
    }
}
```

### 4️⃣ 实体类添加注解

```java
@Data
@Schema(description = "充值请求实体")
public class ChargeRequest {

    @Schema(description = "HIS操作员", example = "admin")
    private String hisOper;

    @Schema(description = "医院编码", example = "H001")
    private String hospitalCode;

    @Schema(
        description = "充值方式",
        example = "alipay",
        allowableValues = {"alipay", "wechat", "card", "cash"}
    )
    private String chargeMethod;
}
```

---

## 🚀 启动和访问

### 1. 编译并启动项目

```powershell
cd d:\desktop\javaTest\springboot-xml-demo
mvn clean install
mvn spring-boot:run
```

### 2. 访问 Swagger UI

**Swagger UI 地址**: http://localhost:8080/swagger-ui.html

或者: http://localhost:8080/swagger-ui/index.html

### 3. 访问 OpenAPI JSON

**OpenAPI JSON**: http://localhost:8080/v3/api-docs

---

## 📖 注解说明

### Controller 层注解

| 注解           | 位置   | 说明         | 示例                                 |
| -------------- | ------ | ------------ | ------------------------------------ |
| `@Tag`         | 类上   | API 分组标签 | `@Tag(name = "用户接口")`            |
| `@Operation`   | 方法上 | 接口说明     | `@Operation(summary = "获取用户")`   |
| `@ApiResponse` | 方法上 | 响应说明     | `@ApiResponse(responseCode = "200")` |
| `@Parameter`   | 参数上 | 参数说明     | `@Parameter(description = "用户ID")` |

### 实体类注解

| 注解      | 位置      | 说明     | 示例                                |
| --------- | --------- | -------- | ----------------------------------- |
| `@Schema` | 类/字段上 | 模型说明 | `@Schema(description = "用户实体")` |

---

## 📝 完整注解示例

### Controller 完整示例

```java
@RestController
@RequestMapping("/api")
@Tag(name = "XML/JSON 转换接口", description = "提供格式转换功能")
public class DemoController {

    @Operation(
        summary = "JSON 转 XML",
        description = "将 JSON 字符串转换为 XML 格式",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "JSON 字符串",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "充值请求示例",
                    value = "{\"hisOper\":\"admin\",\"hospitalCode\":\"H001\"}"
                )
            )
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "成功转换为 XML",
        content = @Content(mediaType = "application/xml")
    )
    @ApiResponse(
        responseCode = "400",
        description = "请求格式错误"
    )
    @PostMapping(value = "/jsonToXml", produces = "application/xml")
    public String jsonToXml(@RequestBody String jsonString) throws Exception {
        var jsonNode = objectMapper.readTree(jsonString);
        return xmlMapper.writeValueAsString(jsonNode);
    }
}
```

### 实体类完整示例

```java
@Data
@Schema(description = "充值请求实体", example = "{\"hisOper\":\"admin\"}")
public class ChargeRequest {

    @Schema(
        description = "HIS操作员账号",
        example = "admin",
        required = true,
        minLength = 3,
        maxLength = 20
    )
    private String hisOper;

    @Schema(
        description = "医院编码",
        example = "H001",
        required = true,
        pattern = "^H\\d{3}$"
    )
    private String hospitalCode;

    @Schema(
        description = "充值金额(元)",
        example = "100.00",
        required = true,
        minimum = "0.01",
        maximum = "99999.99"
    )
    private String chargeMoney;

    @Schema(
        description = "充值方式",
        example = "alipay",
        required = true,
        allowableValues = {"alipay", "wechat", "card", "cash"}
    )
    private String chargeMethod;
}
```

---

## 🎨 自定义配置

### 1. 修改文档路径 (application.yml)

```yaml
springdoc:
  # Swagger UI 路径 (默认: /swagger-ui.html)
  swagger-ui:
    path: /doc.html
    tags-sorter: alpha # 按字母排序
    operations-sorter: alpha

  # API 文档路径 (默认: /v3/api-docs)
  api-docs:
    path: /api-docs

  # 扫描的包路径
  packages-to-scan: com.example.demo.controller

  # 扫描的路径
  paths-to-match: /api/**
```

访问地址变为: http://localhost:8080/doc.html

### 2. 分组配置

```java
@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("转换接口")
            .pathsToMatch("/api/**")
            .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
            .group("管理接口")
            .pathsToMatch("/admin/**")
            .build();
    }
}
```

### 3. 全局安全配置

```java
@Bean
public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(...)
        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        .components(new Components()
            .addSecuritySchemes("Bearer Authentication",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
}
```

---

## ⚠️ 常见问题

### Q1: 为什么不能用 Springfox 和 Knife4j?

**A**: Springfox 已停止维护,不支持 Spring Boot 3.x。Knife4j 2.x 也不支持。

| 工具        | Spring Boot 2.x | Spring Boot 3.x | 推荐 |
| ----------- | --------------- | --------------- | ---- |
| Springfox   | ✅ 支持         | ❌ 不支持       | ❌   |
| Knife4j 2.x | ✅ 支持         | ❌ 不支持       | ❌   |
| Knife4j 4.x | ❌ 不支持       | ✅ 支持         | ⚠️   |
| SpringDoc   | ✅ 支持         | ✅ 支持         | ✅   |

**推荐使用**: SpringDoc OpenAPI 3 (官方推荐,持续维护)

### Q2: 访问 /swagger-ui.html 404?

**A**: 尝试以下地址:

- http://localhost:8080/swagger-ui.html
- http://localhost:8080/swagger-ui/index.html
- http://localhost:8080/swagger-ui/

### Q3: 需要给每个接口都加注解吗?

**A**:

- ❌ **不是必须的** - 没有注解也能生成文档
- ✅ **推荐添加** - 注解可以让文档更详细易懂

**最小配置** (无注解):

```java
@RestController
public class DemoController {

    @PostMapping("/test")
    public String test(@RequestBody String data) {
        return "ok";
    }
}
```

也能生成文档,但信息较少。

**完整配置** (有注解):

```java
@RestController
@Tag(name = "测试接口")
public class DemoController {

    @Operation(summary = "测试接口", description = "这是一个测试")
    @PostMapping("/test")
    public String test(
        @Parameter(description = "请求数据")
        @RequestBody String data
    ) {
        return "ok";
    }
}
```

文档更详细,更易理解。

### Q4: 实体类必须加 @Schema 吗?

**A**:

- ❌ 不是必须的
- ✅ 推荐添加,可以显示字段说明和示例值

---

## 🎯 注解优先级建议

### 必须添加 (高优先级)

1. ✅ Controller 类上的 `@Tag` - 接口分组
2. ✅ 核心接口方法上的 `@Operation` - 接口说明

### 推荐添加 (中优先级)

3. ⚠️ 重要实体类上的 `@Schema` - 模型说明
4. ⚠️ 重要字段上的 `@Schema` - 字段说明

### 可选添加 (低优先级)

5. 💡 `@ApiResponse` - 响应说明
6. 💡 `@Parameter` - 参数说明
7. 💡 `requestBody` 中的 `@ExampleObject` - 示例值

---

## 📦 完整依赖对比

### ❌ 旧方案 (Spring Boot 2.x)

```xml
<!-- 已过时,不要使用! -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>

<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>2.0.9</version>
</dependency>
```

### ✅ 新方案 (Spring Boot 3.x)

```xml
<!-- 推荐使用! -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
</dependency>

<!-- 或者使用 Knife4j 4.x (基于 SpringDoc) -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
    <version>4.3.0</version>
</dependency>
```

---

## 🚀 快速开始

1. **添加依赖** (pom.xml) ✅ 已完成
2. **创建配置类** (OpenApiConfig.java) ✅ 已完成
3. **添加注解** (DemoController.java) ✅ 已完成
4. **启动项目**:
   ```powershell
   mvn spring-boot:run
   ```
5. **访问文档**: http://localhost:8080/swagger-ui.html

就这么简单! 🎉

---

## 💡 总结

### 核心要点

1. ✅ Spring Boot 3.x 使用 **SpringDoc OpenAPI 3**
2. ✅ 添加一个依赖 + 一个配置类即可
3. ✅ 注解**不是必须的**,但推荐添加
4. ✅ Controller 类上 `@Tag` + 方法上 `@Operation` 是最常用的
5. ✅ 实体类字段上 `@Schema` 可以显示字段说明

### 最小配置

- 依赖: `springdoc-openapi-starter-webmvc-ui`
- 配置: `OpenApiConfig.java` (可选)
- 注解: 无注解也能用,有注解更详细

现在您的 API 文档已经配置完成! 🎉
