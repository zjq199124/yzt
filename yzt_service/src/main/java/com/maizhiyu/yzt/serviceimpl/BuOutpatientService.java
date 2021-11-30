package com.maizhiyu.yzt.serviceimpl;

import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.mapper.BuOutpatientMapper;
import com.maizhiyu.yzt.service.IBuOutpatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuOutpatientService implements IBuOutpatientService {

    @Autowired
    private BuOutpatientMapper mapper;


    @Override
    public Integer addOutpatient(BuOutpatient outpatient) {
        String code = UUID.randomUUID().toString().replace("-", "").substring(0,20);
        outpatient.setCode(code);
        return mapper.insert(outpatient);
    }

    @Override
    public Integer setOutpatient(BuOutpatient outpatient) {
        return mapper.updateById(outpatient);
    }

    @Override
    public BuOutpatient getOutpatient(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getOutpatientList(String createStartDate, String createEndDate, String startDate, String endDate, Long customerId, Long departmentId, Long doctorId, Long patientId, Integer type, Integer status, String term) {
        return mapper.selectOutpatientList(createStartDate, createEndDate, startDate, endDate, customerId, departmentId, doctorId, patientId, type, status, term);
    }

    @Override
    public List<Map<String, Object>> getPsUserOutpatientList(Long userId, Long patientId, Integer type, Integer status) {
        return mapper.selectPsUserOutpatientList(userId, patientId, type, status);
    }
}
