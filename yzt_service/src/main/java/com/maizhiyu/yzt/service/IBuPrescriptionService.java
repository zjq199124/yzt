package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.BuPrescription;

import java.util.List;
import java.util.Map;

public interface IBuPrescriptionService {

    Integer addPrescription(BuPrescription prescription);

    Integer delPrescription(Long id);

    Integer setPrescription(BuPrescription prescription);

    Integer setPrescriptionStatus(BuPrescription prescription);

    BuPrescription getPrescription(Long id);

    List<Map<String, Object>> getPrescriptionList(Long outpatientId);

    List<Map<String, Object>> getPrescriptionList(Long customerId, String start, String end);

    List<Map<String, Object>> getPrescriptionItemSummary(Long prescriptionId);

    List<Map<String, Object>> getPatientPrescriptionItemSummary(Long prescriptionId);

    void setPaymentStatus(Long id, Long userId);
}
