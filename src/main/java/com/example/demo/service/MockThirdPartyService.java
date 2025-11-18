package com.example.demo.service;

import com.example.demo.model.RowData;
import com.example.demo.model.Response;
import com.example.demo.model.ReturnResult;
import com.example.demo.model.entity.DepartmentRow;
import com.example.demo.model.entity.PatientRow;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 模拟第三方服务
 */
@Service
public class MockThirdPartyService {

  /**
   * 模拟返回患者信息
   */
  public Response<PatientRow> getPatientInfo() {
    PatientRow patient1 = new PatientRow();
    patient1.setPatientId("1234567896");
    patient1.setClinicId("MZ20170718");
    patient1.setInhosId("");
    patient1.setPatientName("张三");
    patient1.setSex("1");
    patient1.setBirthday("2017-07-18");
    patient1.setUserCardType("1");
    patient1.setAge("10Y");
    patient1.setIdentityName("身份证");
    patient1.setIdentityCardNO("612424198702074561");
    patient1.setCareer("IT");
    patient1.setMaritalStatus("1");
    patient1.setAddress("万达广场");
    patient1.setTelePhone("13645612345");
    patient1.setAccountBalance("200.00");

    PatientRow patient2 = new PatientRow();
    patient2.setPatientId("9876543210");
    patient2.setClinicId("MZ20170719");
    patient2.setInhosId("");
    patient2.setPatientName("李四");
    patient2.setSex("2");
    patient2.setBirthday("1990-05-20");
    patient2.setUserCardType("1");
    patient2.setAge("35Y");
    patient2.setIdentityName("身份证");
    patient2.setIdentityCardNO("110101199005201234");
    patient2.setCareer("医生");
    patient2.setMaritalStatus("1");
    patient2.setAddress("朝阳区");
    patient2.setTelePhone("13812345678");
    patient2.setAccountBalance("500.00");

    List<PatientRow> patients = Arrays.asList(patient1, patient2);

    RowData<PatientRow> data = new RowData<>(patients);
    ReturnResult result = new ReturnResult(0, "查询成功");

    return new Response<>(result, data);
  }

  /**
   * 模拟返回科室信息
   */
  public Response<DepartmentRow> getDepartmentInfo() {
    DepartmentRow dept1 = new DepartmentRow();
    dept1.setDeptCode("D001");
    dept1.setDeptName("内科");
    dept1.setDeptType("临床科室");
    dept1.setLocation("1号楼3层");

    DepartmentRow dept2 = new DepartmentRow();
    dept2.setDeptCode("D002");
    dept2.setDeptName("外科");
    dept2.setDeptType("临床科室");
    dept2.setLocation("2号楼4层");

    DepartmentRow dept3 = new DepartmentRow();
    dept3.setDeptCode("D003");
    dept3.setDeptName("儿科");
    dept3.setDeptType("临床科室");
    dept3.setLocation("1号楼2层");

    List<DepartmentRow> departments = Arrays.asList(dept1, dept2, dept3);

    RowData<DepartmentRow> data = new RowData<>(departments);
    ReturnResult result = new ReturnResult(0, "查询成功");

    return new Response<>(result, data);
  }
}
