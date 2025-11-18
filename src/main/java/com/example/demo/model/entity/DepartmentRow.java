package com.example.demo.model.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * 科室信息实体
 */
@Data
public class DepartmentRow {

  @JacksonXmlProperty(localName = "DeptCode")
  private String deptCode;

  @JacksonXmlProperty(localName = "DeptName")
  private String deptName;

  @JacksonXmlProperty(localName = "DeptType")
  private String deptType;

  @JacksonXmlProperty(localName = "Location")
  private String location;
}
