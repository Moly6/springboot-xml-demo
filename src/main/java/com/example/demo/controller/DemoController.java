package com.example.demo.controller;

import com.example.demo.model.Response;
import com.example.demo.model.entity.ChargeRequest;
import com.example.demo.model.entity.DepartmentRow;
import com.example.demo.model.entity.PatientRow;
import com.example.demo.service.MockThirdPartyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 主控制器 - 演示泛型 XML/JSON 转换
 */
@RestController
@RequestMapping("/api")
@Tag(name = "XML/JSON 转换接口", description = "提供 XML 和 JSON 格式互相转换的相关接口")
public class DemoController {

  @Autowired
  private MockThirdPartyService mockService;

  @Autowired
  private XmlMapper xmlMapper;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * 1. 返回患者信息 (JSON 格式)
   */
  @Operation(summary = "获取患者信息(JSON)", description = "返回患者列表,格式为 JSON")
  @ApiResponse(responseCode = "200", description = "成功返回患者信息")
  @GetMapping("/patients/json")
  public Response<PatientRow> getPatientsJson() {
    return mockService.getPatientInfo();
  }

  /**
   * 2. 返回患者信息 (XML 格式)
   */
  @Operation(summary = "获取患者信息(XML)", description = "返回患者列表,格式为 XML")
  @GetMapping(value = "/patients/xml", produces = "application/xml")
  public String getPatientsXml() throws Exception {
    Response<PatientRow> response = mockService.getPatientInfo();
    return xmlMapper.writeValueAsString(response);
  }

  /**
   * 3. 接收 XML 患者数据,返回 JSON
   */
  @Operation(summary = "接收患者信息(XML)并返回JSON", description = "接收患者信息,格式为 XML,返回格式为 JSON", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "患者信息(XML格式)", required = true, content = @Content(mediaType = "application/xml")))
  @PostMapping(value = "/patients/xml-to-json", consumes = "application/xml")
  public Response<PatientRow> patientsXmlToJson(@RequestBody String xml) throws Exception {
    // 使用 TypeReference 保留泛型信息
    return xmlMapper.readValue(xml, new TypeReference<Response<PatientRow>>() {
    });
  }

  /**
   * 4. 返回科室信息 (JSON 格式)
   */
  @Operation(summary = "获取科室信息(JSON)", description = "返回科室列表,格式为 JSON")
  @GetMapping("/departments/json")
  public Response<DepartmentRow> getDepartmentsJson() {
    return mockService.getDepartmentInfo();
  }

  /**
   * 5. 返回科室信息 (XML 格式)
   */
  @Operation(summary = "获取科室信息(XML)", description = "返回科室列表,格式为 XML")
  @GetMapping(value = "/departments/xml", produces = "application/xml")
  public String getDepartmentsXml() throws Exception {
    Response<DepartmentRow> response = mockService.getDepartmentInfo();
    return xmlMapper.writeValueAsString(response);
  }

  /**
   * 6. 接收 XML 科室数据,返回 JSON
   */
  @Operation(summary = "接收科室信息(XML)并返回JSON", description = "接收科室信息,格式为 XML,返回格式为 JSON", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "科室信息(XML格式)", required = true, content = @Content(mediaType = "application/xml")))
  @PostMapping(value = "/departments/xml-to-json", consumes = "application/xml")
  public Response<DepartmentRow> departmentXmlToJson(@RequestBody String xml) throws Exception {
    return xmlMapper.readValue(xml, new TypeReference<Response<DepartmentRow>>() {
    });
  }

  /**
   * 7. 接收充值请求 (支持 JSON 和 XML)
   * Spring Boot 会根据 Content-Type 自动转换
   */
  @Operation(summary = "处理充值请求", description = "接收充值请求,支持 JSON 和 XML 格式", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "充值请求实体", required = true, content = {
      @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"hisOper\":\"admin\",\"hospitalCode\":\"H001\",\"patientId\":\"P12345\",\"refNum\":\"REF001\",\"chargeMoney\":\"100.00\",\"chargeMethod\":\"alipay\"}")),
      @Content(mediaType = "application/xml", examples = @ExampleObject(value = "<Request><HisOper>admin</HisOper><HospitalCode>H001</HospitalCode><PatientId>P12345</PatientId><RefNum>REF001</RefNum><ChargeMoney>100.00</ChargeMoney><ChargeMethod>alipay</ChargeMethod></Request>"))
  }))
  @PostMapping("/charge")
  public String processChargeRequest(@RequestBody ChargeRequest chargeRequest) throws JsonProcessingException {
    // 直接使用实体对象,Spring Boot 自动完成 JSON/XML 转换
    // Content-Type: application/json -> JSON 转对象
    // Content-Type: application/xml -> XML 转对象

    // 演示: 将对象转换为 XML 字符串
    String xml = xmlMapper.writeValueAsString(chargeRequest);
    System.out.println("转换为 XML 格式:\n" + xml);

    // 将对象转成 json 字符串
    String json = objectMapper.writeValueAsString(chargeRequest);
    System.out.println("转换为 JSON 格式:\n" + json);

    return "JSON:\n" + json + "\n\nXML:\n" + xml;
  }

  /**
   * 8. JSON 字符串转 XML 字符串 (使用 Jackson)
   * 输入: JSON 字符串
   * 输出: XML 字符串
   */
  @Operation(summary = "JSON 字符串转 XML 字符串", description = "接收 JSON 格式的字符串,转换为 XML 格式返回", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "JSON 字符串", required = true, content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"hisOper\":\"admin\",\"hospitalCode\":\"H001\",\"patientId\":\"P12345\"}"))))
  @ApiResponse(responseCode = "200", description = "成功转换为 XML")
  @PostMapping(value = "/jsonToXml", produces = "application/xml")
  public String jsonToXml(@RequestBody String jsonString) throws Exception {
    // 1. 将 JSON 字符串解析为 JsonNode
    var jsonNode = objectMapper.readTree(jsonString);

    // 2. 使用 XmlMapper 将 JsonNode 转换为 XML 字符串
    return xmlMapper.writeValueAsString(jsonNode);
  }

  /**
   * 9. XML 字符串转 JSON 字符串 (使用 Jackson)
   * 输入: XML 字符串
   * 输出: JSON 字符串
   */
  @Operation(summary = "XML 字符串转 JSON 字符串", description = "接收 XML 格式的字符串,转换为 JSON 格式返回", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "XML 字符串", required = true, content = @Content(mediaType = "application/xml", examples = @ExampleObject(value = "<Request><hisOper>admin</hisOper><hospitalCode>H001</hospitalCode></Request>"))))
  @ApiResponse(responseCode = "200", description = "成功转换为 JSON")
  @PostMapping(value = "/xmlToJson", consumes = "application/xml", produces = "application/json")
  public String xmlToJson(@RequestBody String xmlString) throws Exception {
    // 1. 使用 XmlMapper 将 XML 字符串解析为 JsonNode
    var xmlNode = xmlMapper.readTree(xmlString);
    // 设置首字母小写

    // 2. 递归转换字段名首字母小写
    JsonNode lowerNode = lowerFirstLetter(xmlNode);

    // 2. 使用 ObjectMapper 将 JsonNode 转换为 JSON 字符串
    return objectMapper.writeValueAsString(lowerNode);
  }

  private JsonNode lowerFirstLetter(JsonNode node) {
    if (node.isObject()) {
      ObjectNode objectNode = (ObjectNode) node;
      ObjectNode newNode = objectMapper.createObjectNode();

      objectNode.fields().forEachRemaining(entry -> {
        String key = entry.getKey();
        String newKey = lowerCaseFirst(key);
        newNode.set(newKey, lowerFirstLetter(entry.getValue()));
      });
      return newNode;
    } else if (node.isArray()) {
      ArrayNode arrayNode = objectMapper.createArrayNode();
      node.forEach(item -> arrayNode.add(lowerFirstLetter(item)));
      return arrayNode;
    }

    return node;
  }

  private String lowerCaseFirst(String s) {
    if (s == null || s.isEmpty())
      return s;
    return Character.toLowerCase(s.charAt(0)) + s.substring(1);
  }

  /**
   * 10. 健康检查
   */
  @Operation(summary = "健康检查", description = "检查服务是否运行正常")
  @GetMapping("/health")
  public String health() {
    return "OK - Spring Boot XML/JSON 泛型转换服务运行中";
  }
}
