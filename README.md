# Spring Boot XML/JSON 泛型转换 Demo

一个完整的 Spring Boot 示例,演示如何使用 Jackson 和泛型优雅地处理 XML 和 JSON 之间的转换。

## 🎯 项目特点

- ✅ 使用 **泛型** `Response<T>` 和 `RowData<T>` 实现通用数据结构
- ✅ 基于 **Jackson XML** 模块自动序列化/反序列化
- ✅ 支持 **JSON ↔ XML** 双向转换
- ✅ **强类型**,编译时类型检查
- ✅ 可扩展,支持任意实体类型(患者、科室、医生等)

## 📦 技术栈

- Spring Boot 3.2.0
- Java 17
- Jackson Dataformat XML
- Lombok
- Maven

## 🚀 快速开始

### 1. 编译项目

```powershell
cd springboot-xml-demo
mvn clean package
```

### 2. 运行服务器

```powershell
java -jar target/springboot-xml-demo-1.0.0.jar
```

或者使用 Maven:

```powershell
mvn spring-boot:run
```

服务器将在 **http://localhost:8080** 启动

## 📖 API 接口

### 1️⃣ 患者信息接口

#### 获取患者信息 (JSON 格式)

```http
GET http://localhost:8080/api/patients/json
```

**响应示例:**

```json
{
  "returnResult": {
    "errMsg": "查询成功",
    "errCode": 0
  },
  "data": {
    "rows": [
      {
        "patientId": "1234567896",
        "clinicId": "MZ20170718",
        "patientName": "张三",
        "sex": "1",
        "birthday": "2017-07-18",
        "age": "10Y"
      }
    ]
  }
}
```

#### 获取患者信息 (XML 格式)

```http
GET http://localhost:8080/api/patients/xml
```

**响应示例:**

```xml
<Response>
  <Returnresult>
    <ErrMsg>查询成功</ErrMsg>
    <ErrCode>0</ErrCode>
  </Returnresult>
  <Data>
    <Row>
      <PatientId>1234567896</PatientId>
      <PatientName>张三</PatientName>
      <Sex>1</Sex>
      <Birthday>2017-07-18</Birthday>
    </Row>
  </Data>
</Response>
```

#### XML 转 JSON

```http
POST http://localhost:8080/api/patients/xml-to-json
Content-Type: application/xml

<Response>
  <Returnresult>
    <ErrMsg>成功</ErrMsg>
    <ErrCode>1</ErrCode>
  </Returnresult>
  <Data>
    <Row>
      <PatientId>123</PatientId>
      <PatientName>测试</PatientName>
    </Row>
  </Data>
</Response>
```

### 2️⃣ 科室信息接口

#### 获取科室信息 (JSON 格式)

```http
GET http://localhost:8080/api/departments/json
```

#### 获取科室信息 (XML 格式)

```http
GET http://localhost:8080/api/departments/xml
```

#### XML 转 JSON

```http
POST http://localhost:8080/api/departments/xml-to-json
Content-Type: application/xml
```

### 3️⃣ 健康检查

```http
GET http://localhost:8080/api/health
```

## 🧪 使用 PowerShell 测试

### 测试 JSON 接口

```powershell
# 获取患者信息 (JSON)
Invoke-RestMethod -Uri "http://localhost:8080/api/patients/json" -Method GET

# 获取科室信息 (JSON)
Invoke-RestMethod -Uri "http://localhost:8080/api/departments/json" -Method GET
```

### 测试 XML 接口

```powershell
# 获取患者信息 (XML)
Invoke-RestMethod -Uri "http://localhost:8080/api/patients/xml" -Method GET

# XML 转 JSON
$xml = @"
<Response>
  <Returnresult>
    <ErrMsg>成功</ErrMsg>
    <ErrCode>1</ErrCode>
  </Returnresult>
  <Data>
    <Row>
      <PatientId>999</PatientId>
      <PatientName>测试患者</PatientName>
    </Row>
  </Data>
</Response>
"@

Invoke-RestMethod -Uri "http://localhost:8080/api/patients/xml-to-json" -Method POST -Body $xml -ContentType "application/xml"
```

## 🧩 核心代码结构

### 泛型响应类

```java
@JacksonXmlRootElement(localName = "Response")
public class Response<T> {
    private ReturnResult returnResult;
    private RowData<T> data;
}
```

### 泛型数据容器

```java
public class RowData<T> {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Row")
    private List<T> rows;
}
```

### 使用示例

```java
// 患者信息
Response<PatientRow> patientResponse = ...

// 科室信息
Response<DepartmentRow> deptResponse = ...

// XML 解析 (保留泛型信息)
Response<PatientRow> response = xmlMapper.readValue(
    xml,
    new TypeReference<Response<PatientRow>>() {}
);
```

## 🔧 扩展新类型

要添加新的数据类型(如医生、账单等),只需:

1. 创建新的实体类(如 `DoctorRow.java`)
2. 添加 `@JacksonXmlProperty` 注解
3. 使用 `Response<DoctorRow>` 即可

完全不需要修改 `Response` 或 `Data` 类!

## 📂 项目结构

```
springboot-xml-demo/
├── pom.xml
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java          # 主入口
│   ├── controller/
│   │   └── DemoController.java       # REST 控制器
│   ├── service/
│   │   └── MockThirdPartyService.java # 模拟服务
│   └── model/
│       ├── Response.java              # 泛型响应类
│       ├── Data.java                  # 泛型数据容器
│       ├── ReturnResult.java          # 返回结果
│       └── entity/
│           ├── PatientRow.java        # 患者实体
│           └── DepartmentRow.java     # 科室实体
└── src/main/resources/
    └── application.yml                # 配置文件
```

## ✅ 优点总结

| 特点              | 说明                                  |
| ----------------- | ------------------------------------- |
| 🧱 **强类型泛型** | `Response<T>` 支持任意类型,编译时检查 |
| 🪶 **可复用**     | 同一套代码支持 JSON/XML               |
| 🔄 **自动转换**   | Jackson 自动序列化/反序列化           |
| 🧩 **易扩展**     | 新增类型无需修改核心代码              |
| 🎯 **企业级**     | 完全符合 Spring Boot 最佳实践         |

## 📝 注意事项

- 需要 Java 17 或更高版本
- 确保已安装 Maven
- 如果使用 IDE,需要启用 Lombok 注解处理器

## 🤝 贡献

欢迎提交 Issue 和 Pull Request!

## 📄 许可证

MIT License

