package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Request")
@Schema(description = "充值请求实体")
public class ChargeRequest {

  @JacksonXmlProperty(localName = "HisOper")
  @Schema(description = "HIS操作员", example = "admin")
  private String hisOper;

  @JacksonXmlProperty(localName = "HospitalCode")
  @Schema(description = "医院编码", example = "H001")
  private String hospitalCode;

  @JacksonXmlProperty(localName = "PatientId")
  @Schema(description = "患者ID", example = "P12345")
  private String patientId;

  @JacksonXmlProperty(localName = "RefNum")
  @Schema(description = "参考编号", example = "REF001")
  private String refNum;

  @JacksonXmlProperty(localName = "ChargeMoney")
  @Schema(description = "充值金额", example = "100.00")
  private String chargeMoney;

  @JacksonXmlProperty(localName = "ChargeMethod")
  @Schema(description = "充值方式", example = "alipay", allowableValues = { "alipay", "wechat", "card", "cash" })
  private String chargeMethod;
}
