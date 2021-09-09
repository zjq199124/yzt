package com.maizhiyu.yzt.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionMapper;
import com.maizhiyu.yzt.mapper.HsUserMapper;
import com.maizhiyu.yzt.service.IBuPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuPrescriptionService implements IBuPrescriptionService {

    @Autowired
    private BuPrescriptionMapper mapper;

    @Autowired
    private BuPrescriptionItemMapper itemMapper;

    @Autowired
    private HsUserMapper hsUserMapper;

    @Override
    public Integer addPrescription(BuPrescription prescription) {
        // 新增处方
        int res = mapper.insert(prescription);
        // 增加处方项
        addItems(prescription);
        // 返回结果
        return res;
    }

    @Override
    public Integer delPrescription(Long id) {
        // 删除处方
        int res = mapper.deleteById(id);
        // 删除处方项
        delItems(id);
        // 返回数据
        return res;
    }

    @Override
    public Integer setPrescription(BuPrescription prescription) {
        // 更新处方
        int res = mapper.updateById(prescription);
        // 删除处方项
        delItems(prescription.getId());
        // 增加处方项
        addItems(prescription);
        // 返回结果
        return res;
    }

    @Override
    public Integer setPrescriptionStatus(BuPrescription prescription) {
        return mapper.updateById(prescription);
    }

    @Override
    public BuPrescription getPrescription(Long id) {
        // 获取处方
        BuPrescription prescription = mapper.selectById(id);
        // 获取医生
        HsUser user = hsUserMapper.selectById(prescription.getDoctorId());
        // 获取处方项
        prescription.setDoctorName(user.getRealname());
        prescription.setItemList(getItems(id));
        // 返回结果
        return prescription;
    }

    @Override
    public List<Map<String, Object>> getPrescriptionList(Long outpatientId) {
        // 定义返回变量
        List<Map<String, Object>> list = new ArrayList<>();
        // 查询处方列表
        QueryWrapper<BuPrescription> wrapper = new QueryWrapper<>();
        wrapper.eq("outpatient_id", outpatientId);
        List<BuPrescription> prescriptions = mapper.selectList(wrapper);
        // 查询每个处方的项目列表
        for (BuPrescription prescription : prescriptions) {
            // 查询处方项列表
            List<BuPrescriptionItem> itemlist = getItems(prescription.getId());
            // 整理数据结构
            JSONObject jsonObj = (JSONObject) JSONObject.toJSON(prescription);
            jsonObj.put("itemList", itemlist);
            // 添加到结果集
            list.add(jsonObj);
        }
        // 返回数据
        return list;
    }

    @Override
    public List<Map<String, Object>> getPrescriptionItemSummary(Long prescriptionId) {
        return mapper.selectPrescriptionItemSummary(prescriptionId);
    }

    @Override
    public List<Map<String, Object>> getPatientPrescriptionItemSummary(Long patientId) {
        return mapper.selectPatientPrescriptionItemSummary(patientId);
    }


    private void addItems(BuPrescription prescription) {
        for (BuPrescriptionItem item : prescription.getItemList()) {
            item.setCustomerId(prescription.getCustomerId());
            item.setDepartmentId(prescription.getDepartmentId());
            item.setDoctorId(prescription.getDoctorId());
            item.setPatientId(prescription.getPatientId());
            item.setOutpatientId(prescription.getOutpatientId());
            item.setPrescriptionId(prescription.getId());
            itemMapper.insert(item);
        }
    }


    private void delItems(Long prescriptionId) {
        QueryWrapper<BuPrescriptionItem> wrapper = new QueryWrapper<>();
        wrapper.eq("prescription_id", prescriptionId);
        itemMapper.delete(wrapper);
    }


    private List<BuPrescriptionItem> getItems(Long prescriptionId) {
        QueryWrapper<BuPrescriptionItem> itemQueryWrapper = new QueryWrapper<>();
        itemQueryWrapper.eq("prescription_id", prescriptionId);
        return itemMapper.selectList(itemQueryWrapper);
    }
}
