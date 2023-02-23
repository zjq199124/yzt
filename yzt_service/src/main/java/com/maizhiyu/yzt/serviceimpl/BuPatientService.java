package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.entity.PsUserPatient;
import com.maizhiyu.yzt.mapper.BuPatientMapper;
import com.maizhiyu.yzt.mapper.PsUserPatientMapper;
import com.maizhiyu.yzt.service.IBuPatientService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Service
@Transactional(rollbackFor = Exception.class)
public class BuPatientService extends ServiceImpl<BuPatientMapper, BuPatient> implements IBuPatientService {

    @Autowired
    private BuPatientMapper patientMapper;

    @Autowired
    private PsUserPatientMapper userPatientMapper;

    @Override
    //@ExistCheck(clazz = BuPatient.class, fname = "name|idcard", message = "患者已存在")
    //@ExistCheck(clazz = BuPatient.class, fname = "name|phone", message = "患者已存在")
    public Integer addPatient(BuPatient patient) {
        if (patient.getCode() == null) {
            String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
            patient.setCode(code);
        }
        return patientMapper.insert(patient);
    }

    @Override
    public Integer addPatientByPsUser(Long userId, BuPatient patient) {
        // 查询患者
        QueryWrapper<BuPatient> wrapper = new QueryWrapper<>();
        List<BuPatient> list = patientMapper.selectList(wrapper);
        // 患者不存在则新增
        if (list == null || list.size() == 0) {
            // 添加患者信息
            String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
            patient.setCode(code);
            patientMapper.insert(patient);
        }
        // 患者存在
        else {

        }
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
    public BuPatient getPatient(String name, String phone, String idcard) {
        QueryWrapper<BuPatient> wrapper = new QueryWrapper<>();
        /*wrapper.or(q -> q.eq("name", name).eq("idcard", idcard))
                .or(q -> q.eq("name", name).eq("phone", phone))
                .orderByDesc("update_time")
                .last("limit 1");*/
        wrapper.eq("idcard",idcard)
                .orderByDesc("update_time")
                .last("limit 1");
        return patientMapper.selectOne(wrapper);
    }

    @Override
    public BuPatient getPatientByHisId(Long customerId, Long hisId) {
        QueryWrapper<BuPatient> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(customerId)) {
            wrapper.eq("customer_id", customerId);
        }
        wrapper.eq("his_id", hisId)
                .orderByDesc("update_time")
                .last("limit 1");
        return patientMapper.selectOne(wrapper);
    }


    @Override
    public List<BuPatient> getPatientList(Long customerId, String term) {
        QueryWrapper<BuPatient> wrapper = new QueryWrapper<>();
        if (customerId == null) {
            wrapper.and(q -> q.isNull("customer_id")
                    .or().eq("customer_id", 0L));
        } else {
            wrapper.and(q -> q.eq("customer_id", customerId)
                    .or().eq("customer_id", 0L));
        }
        if (term != null && term.length() > 0) {
            wrapper.and(q -> q.like("code", term)
                    .or().like("name", term)
                    .or().like("phone", term)
                    .or().like("idcard", term));
        }
        wrapper.orderByDesc("create_time");
        return patientMapper.selectList(wrapper);
    }

    @Override
    public List<BuPatient> getPatientListByPsuser(Long uid) {
        return patientMapper.selectPatientListByPsuser(uid);
    }

    @Override
    public IPage<Map<String, Object>> getPatientListByDoctor(Page page, Long doctorId, String term) {
        return patientMapper.selectPatientListByDoctor(page, doctorId, term);
    }

    @Override
    public IPage<Map<String, Object>> getPatientListByTherapist(Page page, Long therapistId, Integer type, String term) {
        return patientMapper.selectPatientListByTherapist(page, therapistId, type, term);
    }

    @Override
    public List<Map<String, Object>> getPatientPrescriptionList(Long patientId, Integer type) {
        return patientMapper.selectPatientPrescriptionList(patientId, type);
    }

    @Override
    public BuPatient selectByHisId(Long customerId, Long hisId) {
        LambdaQueryWrapper<BuPatient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuPatient::getHisId, hisId)
                .eq(BuPatient::getCustomerId, customerId)
                .orderByDesc(BuPatient::getId)
                .last("limit 1");
        return patientMapper.selectOne(queryWrapper);
    }

}
