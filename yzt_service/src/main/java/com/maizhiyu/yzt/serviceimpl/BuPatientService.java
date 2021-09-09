package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import com.maizhiyu.yzt.entity.PsUserPatient;
import com.maizhiyu.yzt.mapper.BuPatientMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemMapper;
import com.maizhiyu.yzt.mapper.PsUserPatientMapper;
import com.maizhiyu.yzt.service.IBuPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
public class BuPatientService implements IBuPatientService {

    @Autowired
    private BuPatientMapper patientMapper;

    @Autowired
    private PsUserPatientMapper userPatientMapper;

    @Autowired
    private BuPrescriptionItemMapper prescriptionItemMapper;


    @Override
    public Integer addPatient(BuPatient patient) {
        return patientMapper.insert(patient);
    }

    @Override
    public Integer addPatientByPsUser(Long userId, BuPatient patient) {
        // 添加患者信息
        patientMapper.insert(patient);
        // 添加关系信息
        PsUserPatient psUserPatient = new PsUserPatient();
        psUserPatient.setUserId(userId);
        psUserPatient.setPatientId(patient.getId());
        return userPatientMapper.insert(psUserPatient);
    }

    @Override
    public Integer delPatient(Long id) {
        return patientMapper.deleteById(id);
    }

    @Override
    public Integer setPatient(BuPatient patient) {
        return patientMapper.updateById(patient);
    }

    @Override
    public BuPatient getPatient(Long id) {
        return patientMapper.selectById(id);
    }

    @Override
    public List<BuPatient> getPatientList(String term) {
        QueryWrapper<BuPatient> wrapper = new QueryWrapper<>();
        if (term != null && term.length() > 0) {
            wrapper.like("code", term)
                    .or().like("name", term)
                    .or().like("phone", term)
                    .or().like("idcard", term);
        }
        return patientMapper.selectList(wrapper);
    }

    @Override
    public List<BuPatient> getPatientListByPsuser(Long uid) {
        return patientMapper.selectPatientListByPsuser(uid);
    }

    @Override
    public List<Map<String, Object>> getPatientListByDoctor(Long doctorId, String term) {
        return patientMapper.selectPatientListByDoctor(doctorId, term);
    }

    @Override
    public List<Map<String, Object>> getPatientListByTherapist(Long therapistId, Integer type, String term) {
        return patientMapper.selectPatientListByTherapist(therapistId, type, term);
    }

    @Override
    public List<Map<String, Object>> getPatientPrescriptionList(Long patientId, Integer type) {
        return patientMapper.selectPatientPrescriptionList(patientId, type);
    }

}
