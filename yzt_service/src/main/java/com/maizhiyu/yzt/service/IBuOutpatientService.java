package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.BuOutpatient;

import java.util.List;
import java.util.Map;

public interface IBuOutpatientService {

    Integer addOutpatient(BuOutpatient outpatient);

    Integer setOutpatient(BuOutpatient outpatient);

    BuOutpatient getOutpatient(Long id);

    List<Map<String, Object>> getOutpatientList(
            String startDate, String endDate,
            Long customerId, Long departmentId, Long doctorId, Long patientId,
            Integer type, Integer status, String term);

    List<Map<String, Object>> getPsUserOutpatientList(Long userId, Long patientId, Integer type, Integer status);
}
