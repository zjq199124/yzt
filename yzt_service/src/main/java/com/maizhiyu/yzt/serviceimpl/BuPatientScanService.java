package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuPatientScan;
import com.maizhiyu.yzt.mapper.BuPatientScanMapper;
import com.maizhiyu.yzt.service.IBuPatientScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
public class BuPatientScanService extends ServiceImpl<BuPatientScanMapper,BuPatientScan> implements IBuPatientScanService {

    @Autowired
    private BuPatientScanMapper mapper;

    @Override
    public Integer addPatientScan(BuPatientScan scan) {
        return mapper.insert(scan);
    }

    @Override
    public Integer delPatientScan(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public BuPatientScan getPatientScan(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public IPage<Map<String, Object>> getPatientScanList(Page page, Long customerId, String term) {
        return mapper.selectPatientScanList(page,customerId, term);
    }

}
