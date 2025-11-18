package com.example.demo.model.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * 患者信息实体
 */
@Data
public class PatientRow {

  @JacksonXmlProperty(localName = "PatientId")
  private String patientId;

  @JacksonXmlProperty(localName = "ClinicId")
  private String clinicId;

  @JacksonXmlProperty(localName = "InhosId")
  private String inhosId;

  @JacksonXmlProperty(localName = "PatientName")
  private String patientName;

  @JacksonXmlProperty(localName = "Sex")
  private String sex;

  @JacksonXmlProperty(localName = "Birthday")
  private String birthday;

  @JacksonXmlProperty(localName = "UserCardType")
  private String userCardType;

  @JacksonXmlProperty(localName = "Age")
  private String age;

  @JacksonXmlProperty(localName = "IdentityName")
  private String identityName;

  @JacksonXmlProperty(localName = "IdentityCardNO")
  private String identityCardNO;

  @JacksonXmlProperty(localName = "Career")
  private String career;

  @JacksonXmlProperty(localName = "MaritalStatus")
  private String maritalStatus;

  @JacksonXmlProperty(localName = "Address")
  private String address;

  @JacksonXmlProperty(localName = "TelePhone")
  private String telePhone;

  @JacksonXmlProperty(localName = "AccountBalance")
  private String accountBalance;
}
