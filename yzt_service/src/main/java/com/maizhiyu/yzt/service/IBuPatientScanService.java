package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.BuPatientScan;

import java.util.List;
import java.util.Map;

public interface IBuPatientScanService {

    Integer addPatientScan(BuPatientScan patient);

    Integer delPatientScan(Long id);

    BuPatientScan getPatientScan(Long id);

    List<Map<String, Object>> getPatientScanList(Long customerId, String term);

}
