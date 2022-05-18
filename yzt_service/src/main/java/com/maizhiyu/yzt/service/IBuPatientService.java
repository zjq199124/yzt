package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.BuPatient;

import java.util.List;
import java.util.Map;

public interface IBuPatientService {

    Integer addPatient(BuPatient patient);

    Integer addPatientByPsUser(Long userId, BuPatient patient);

    Integer delPatient(Long id);

    Integer setPatient(BuPatient patient);

    BuPatient getPatient(Long id);

    BuPatient getPatient(String name, String phone, String idcard);

    List<BuPatient> getPatientList(Long customerId, String term);

    List<BuPatient> getPatientListByPsuser(Long userId);

    List<Map<String, Object>> getPatientListByDoctor(Long doctorId, String term);

    List<Map<String, Object>> getPatientListByTherapist(Long TherapistId, Integer type, String term);

    List<Map<String, Object>> getPatientPrescriptionList(Long patientId, Integer type);
}
