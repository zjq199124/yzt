package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPatientScan;

import java.util.List;
import java.util.Map;

public interface IBuPatientScanService extends IService<BuPatientScan> {

    Integer addPatientScan(BuPatientScan patient);

    Integer delPatientScan(Long id);

    BuPatientScan getPatientScan(Long id);

    List<Map<String, Object>> getPatientScanList(Long customerId, String term);

}
