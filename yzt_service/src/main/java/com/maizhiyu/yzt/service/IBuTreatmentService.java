package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuTreatment;

import java.util.List;
import java.util.Map;

public interface IBuTreatmentService extends IService<BuTreatment> {

    Integer addTreatment(BuTreatment treatment);

    Integer setTreatment(BuTreatment treatment);

    BuTreatment getTreatment(Long id);

    List<Map<String, Object>> getTreatmentList(String startDate, String endDate,
            Long customerId, Long departmentId, Long therapistId, Long patientId, Long prescriptionId, Long projectId,
            Integer type, Integer status, String term);

    List<Map<String, Object>> getTreatmentWaitingList(String startDate, String endDate, Long customerId, Long departmentId, Long therapistId);

    List<Map<String, Object>> getPsUserTreatmentList(Long userId, Long patientId, Integer type, Integer status);

    List<Map<String, Object>> getTreatmentStatistics(String startDate, String endDate, Long customerId, Long departmentId, Long therapistId, List<Long> projects);

}
