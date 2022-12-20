package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional(rollbackFor = Exception.class)
public class BuTreatmentService extends ServiceImpl<BuTreatmentMapper, BuTreatment> implements IBuTreatmentService {

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
        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
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
    public IPage<Map<String, Object>> getTreatmentList(Page page, String startDate, String endDate, Long customerId, Long departmentId, Long therapistId, Long patientId, Long prescriptionId, Long projectId, Integer type, Integer status, String term) {
        return mapper.selectTreatmentList(page, startDate, endDate, customerId, departmentId, therapistId, patientId, prescriptionId, projectId, type, status, term);
    }

    @Override
    public IPage<Map<String, Object>> getTreatmentWaitingList(Page page,String startDate, String endDate, Long customerId, Long departmentId, Long therapistId) {
        return mapper.selectTreatmentWaitingList(page,startDate, endDate, customerId, departmentId, therapistId);
    }

    @Override
    public IPage<Map<String, Object>> getPsUserTreatmentList(Page page,Long userId, Long patientId, Integer type, Integer status) {
        return mapper.selectPsUserTreatmentList(page,userId, patientId, type, status);
    }

    @Override
    public IPage<Map<String, Object>> getTreatmentStatistics(Page page,String startDate, String endDate, Long customerId, Long departmentId, Long therapistId, List<Long> projects) {
        return mapper.selectTreatmentStatistics(page,startDate, endDate, customerId, departmentId, therapistId, projects);
    }
}
