package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Jackson 配置类
 * 统一配置 JSON 和 XML 的序列化/反序列化
 */
@Configuration
public class JacksonConfig {

  /**
   * 配置全局 ObjectMapper (用于 JSON 处理)
   * 
   * @Primary 注解确保这是 Spring Boot 默认使用的 ObjectMapper
   *          这样 @RequestBody/@ResponseBody 也会使用这个配置
   */
  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    // 启用 JSON 美化输出 (带缩进和换行)
    mapper.enable(SerializationFeature.INDENT_OUTPUT);

    // 其他常用配置:
    // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 日期转字符串
    // mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // 忽略未知字段

    return mapper;
  }

  /**
   * 配置全局 XmlMapper (用于 XML 处理)
   */
  @Bean
  public XmlMapper xmlMapper() {
    XmlMapper mapper = new XmlMapper();

    // 启用 XML 美化输出 (带缩进和换行)
    mapper.enable(SerializationFeature.INDENT_OUTPUT);

    return mapper;
  }
}
