package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPatient;

import java.util.List;
import java.util.Map;

public interface IBuPatientService extends IService<BuPatient> {

    Integer addPatient(BuPatient patient);

    Integer addPatientByPsUser(Long userId, BuPatient patient);

    Integer delPatient(Long id);

    Integer setPatient(BuPatient patient);

    BuPatient getPatient(Long id);

    BuPatient getPatient(String name, String phone, String idcard);

    BuPatient getPatientByHisId(Long customerId, Long hisId);

    List<BuPatient> getPatientList(Long customerId, String term);

    List<BuPatient> getPatientListByPsuser(Long userId);

    IPage<Map<String, Object>> getPatientListByDoctor(Page page, Long doctorId, String term);

    IPage<Map<String, Object>> getPatientListByTherapist(Page page, Long TherapistId, Integer type, String term);

    List<Map<String, Object>> getPatientPrescriptionList(Long patientId, Integer type);
}
