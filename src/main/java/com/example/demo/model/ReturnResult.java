package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * 通用返回结果
 */
@Data
public class ReturnResult {
  @JacksonXmlProperty(localName = "ErrMsg")
  @JsonProperty("ErrMsg")
  private String errMsg;

  @JacksonXmlProperty(localName = "ErrCode")
  @JsonProperty("ErrCode")
  private String errCode;

  public ReturnResult() {
  }

  public ReturnResult(String errCode, String errMsg) {
    this.errCode = errCode;
    this.errMsg = errMsg;
  }
}
