package com.maizhiyu.yzt.serviceimpl;

import com.maizhiyu.yzt.entity.BuTreatment;
import com.maizhiyu.yzt.mapper.BuTreatmentMapper;
import com.maizhiyu.yzt.service.IBuTreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuTreatmentService implements IBuTreatmentService {

    @Autowired
    private BuTreatmentMapper mapper;

    @Override
    public Integer addTreatment(BuTreatment treatment) {
        return mapper.insert(treatment);
    }

    @Override
    public Integer setTreatment(BuTreatment treatment) {
        return mapper.updateById(treatment);
    }

    @Override
    public BuTreatment getTreatment(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getTreatmentList(String startDate, String endDate, Long customerId, Long departmentId, Long therapistId, Long patientId, Long prescriptionId, Long projectId, Integer type, Integer status, String term) {
        return mapper.selectTreatmentList(startDate, endDate, customerId, departmentId, therapistId, patientId, prescriptionId, projectId, type, status, term);
    }

    @Override
    public List<Map<String, Object>> getPsUserTreatmentList(Long userId, Long patientId, Integer type, Integer status) {
        return mapper.selectPsUserTreatmentList(userId, patientId, type, status);
    }

    @Override
    public List<Map<String, Object>> getTreatmentStatistics(String startDate, String endDate, Long customerId, Long departmentId, Long therapistId, Long projectId) {
        return mapper.selectTreatmentStatistics(startDate, endDate, customerId, departmentId, therapistId, projectId);
    }
}
