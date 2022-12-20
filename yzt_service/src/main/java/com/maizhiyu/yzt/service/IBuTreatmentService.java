package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuTreatment;

import java.util.List;
import java.util.Map;

public interface IBuTreatmentService extends IService<BuTreatment> {

    Integer addTreatment(BuTreatment treatment);

    Integer setTreatment(BuTreatment treatment);

    BuTreatment getTreatment(Long id);

    IPage<Map<String, Object>> getTreatmentList(Page page, String startDate, String endDate, Long customerId, Long departmentId, Long therapistId, Long patientId, Long prescriptionId, Long projectId, Integer type, Integer status, String term);

    IPage<Map<String, Object>> getTreatmentWaitingList(Page page,String startDate, String endDate, Long customerId, Long departmentId, Long therapistId);

    IPage<Map<String, Object>> getPsUserTreatmentList(Page page,Long userId, Long patientId, Integer type, Integer status);

    IPage<Map<String, Object>> getTreatmentStatistics(Page page,String startDate, String endDate, Long customerId, Long departmentId, Long therapistId, List<Long> projects);

}
