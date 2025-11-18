package com.example.demo.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * 通用返回结果
 */
@Data
public class ReturnResult {
  @JacksonXmlProperty(localName = "ErrMsg")
  private String errMsg;

  @JacksonXmlProperty(localName = "ErrCode")
  private int errCode;

  public ReturnResult() {
  }

  public ReturnResult(int errCode, String errMsg) {
    this.errCode = errCode;
    this.errMsg = errMsg;
  }
}
