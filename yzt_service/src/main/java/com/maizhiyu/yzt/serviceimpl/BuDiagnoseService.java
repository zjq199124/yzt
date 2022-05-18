package com.maizhiyu.yzt.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.mapper.BuDiagnoseMapper;
import com.maizhiyu.yzt.mapper.BuOutpatientMapper;
import com.maizhiyu.yzt.mapper.BuPatientMapper;
import com.maizhiyu.yzt.service.IBuDiagnoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuDiagnoseService implements IBuDiagnoseService {

    @Autowired
    private BuDiagnoseMapper mapper;

    @Autowired
    private BuPatientMapper patientMapper;

    @Autowired
    private BuOutpatientMapper outpatientMapper;

    @Override
    public Integer addDiagnose(BuDiagnose diagnose) {
        return mapper.insert(diagnose);
    }

    @Override
    public Integer setDiagnose(BuDiagnose diagnose) {
        return mapper.updateById(diagnose);
    }

    @Override
    public BuDiagnose getDiagnose(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public BuDiagnose getDiagnoseOfOutpatient(Long outpatientId) {
        QueryWrapper<BuDiagnose> wrapper = new QueryWrapper<>();
        wrapper.eq("outpatient_id", outpatientId);
        return mapper.selectOne(wrapper);
    }

    @Override
    public List<Map<String, Object>> getDiagnoseList(Long customerId, String start, String end) {
        // 定义返回变量
        List<Map<String, Object>> list = new ArrayList<>();
        // 查询处方列表
        QueryWrapper<BuDiagnose> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id", customerId)
                .ge("update_time", start)
                .lt("update_time", end)
                .last("limit 100");
        List<BuDiagnose> diagnoses = mapper.selectList(wrapper);
        // 查询挂号信息
        for (BuDiagnose diagnose : diagnoses) {
            // 查询患者信息
            BuPatient patient = patientMapper.selectById(diagnose.getPatientId());
            // 查询挂号信息
            BuOutpatient outpatient = outpatientMapper.selectById(diagnose.getOutpatientId());
            // 整理数据结构
            JSONObject jsonObj = (JSONObject) JSONObject.toJSON(diagnose);
            jsonObj.put("patient", patient);
            jsonObj.put("outpatient", outpatient);
            // 添加到结果集
            list.add(jsonObj);
        }
        // 返回数据
        return list;
    }

}
