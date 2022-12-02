package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.TxInfraredDetails;

import java.util.List;
import java.util.Map;

public interface IBuOutpatientService extends IService<BuOutpatient> {

    Integer addOutpatient(BuOutpatient outpatient);

    Integer setOutpatient(BuOutpatient outpatient);

    BuOutpatient getOutpatient(Long id);

    BuOutpatient  getOutpatientByHisId(Long customerId, Long hisId);

    List<Map<String, Object>> getOutpatientList(
            String createStartDate, String createEndDate,
            String startDate, String endDate,
            Long customerId, Long departmentId, Long doctorId, Long patientId,
            Integer type, Integer status, String term);

    List<Map<String, Object>> getPsUserOutpatientList(Long userId, Long patientId, Integer type, Integer status);
}
