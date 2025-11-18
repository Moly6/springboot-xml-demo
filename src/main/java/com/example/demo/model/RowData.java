package com.example.demo.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

/**
 * 通用数据容器（泛型）
 * 包含多个 Row 的数据包装类
 * 
 * @param <T> Row 的类型
 */
@Data
public class RowData<T> {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "Row")
  private List<T> rows;

  public RowData() {
  }

  public RowData(List<T> rows) {
    this.rows = rows;
  }
}
