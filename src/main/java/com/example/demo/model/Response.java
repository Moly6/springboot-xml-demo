package com.example.demo.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 通用响应类（泛型）
 * 
 * @param <T> RowData 中 Row 的类型
 */
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Response")
public class Response<T> {

  @JacksonXmlProperty(localName = "Returnresult")
  private ReturnResult returnResult;

  @JacksonXmlProperty(localName = "Data")
  private RowData<T> data;
}
