package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.entity.BuTreatment;
import com.maizhiyu.yzt.mapper.BuPrescriptionMapper;
import com.maizhiyu.yzt.mapper.BuTreatmentMapper;
import com.maizhiyu.yzt.mapper.TsSytechMapper;
import com.maizhiyu.yzt.service.IBuTreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuTreatmentService extends ServiceImpl<BuTreatmentMapper,BuTreatment> implements IBuTreatmentService {

    @Autowired
    private BuTreatmentMapper mapper;

    @Autowired
    private TsSytechMapper sytechMapper;

    @Autowired
    private BuPrescriptionMapper prescriptionMapper;

    @Override
    public Integer addTreatment(BuTreatment treatment) {
        // 补充治疗单编码
        if (treatment.getPrescriptionId() != null) {
            BuPrescription prescription = prescriptionMapper.selectById(treatment.getPrescriptionId());
            treatment.setTcode(prescription.getCode());
        }
        // 生成预约单编码
        String code = UUID.randomUUID().toString().replace("-", "").substring(0,20);
        treatment.setCode(code);
        // 添加数据
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
    public List<Map<String, Object>> getTreatmentWaitingList(String startDate, String endDate, Long customerId, Long departmentId, Long therapistId) {
        return mapper.selectTreatmentWaitingList(startDate, endDate, customerId, departmentId, therapistId);
    }

    @Override
    public List<Map<String, Object>> getPsUserTreatmentList(Long userId, Long patientId, Integer type, Integer status) {
        return mapper.selectPsUserTreatmentList(userId, patientId, type, status);
    }

    @Override
    public List<Map<String, Object>> getTreatmentStatistics(String startDate, String endDate, Long customerId, Long departmentId, Long therapistId, List<Long> projects) {
        return mapper.selectTreatmentStatistics(startDate, endDate, customerId, departmentId, therapistId, projects);
    }
}
