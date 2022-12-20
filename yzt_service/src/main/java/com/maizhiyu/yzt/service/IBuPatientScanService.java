package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPatientScan;

import java.util.Map;

public interface IBuPatientScanService extends IService<BuPatientScan> {

    Integer addPatientScan(BuPatientScan patient);

    Integer delPatientScan(Long id);

    BuPatientScan getPatientScan(Long id);

    IPage<Map<String, Object>> getPatientScanList(Page page, Long customerId, String term);

}
