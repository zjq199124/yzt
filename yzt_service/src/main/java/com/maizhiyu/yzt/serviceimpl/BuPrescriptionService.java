package com.maizhiyu.yzt.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.*;
import com.maizhiyu.yzt.service.IBuPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuPrescriptionService implements IBuPrescriptionService {

    @Autowired
    private BuPrescriptionMapper mapper;

    @Autowired
    private BuPrescriptionItemMapper itemMapper;

    @Autowired
    private BuPatientMapper patientMapper;

    @Autowired
    private BuOutpatientMapper outpatientMapper;

    @Autowired
    private HsUserMapper hsUserMapper;

    @Autowired
    private HsCustomerHerbsMapper hsCustomerHerbsMapper;

    @Override
    public Integer addPrescription(BuPrescription prescription) {
        // 生成编码
        if (prescription.getCode() == null) {
            String code = UUID.randomUUID().toString().replace("-", "").substring(0,20);
            prescription.setCode(code);
        }

//        BigDecimal pr = new BigDecimal(0);
//        for (BuPrescriptionItem item : prescription.getItemList()) {
//            BigDecimal multiply = item.getDosage().multiply(item.getPrice());
//            pr = pr.add(multiply);
//        }
//        prescription.setPrice(pr);
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
        // 获取原来处方数据
        BuPrescription original = mapper.selectById(prescription.getId());
        // 更新处方
        prescription.setOutpatientId(original.getOutpatientId());
        prescription.setDoctorId(original.getDoctorId());
        prescription.setPatientId(original.getPatientId());

//        BigDecimal pr = new BigDecimal(0);
//        for (BuPrescriptionItem item : prescription.getItemList()) {
//            BigDecimal multiply = item.getDosage().multiply(item.getPrice());
//            pr = pr.add(multiply);
//        }
//        prescription.setPrice(pr);

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
    public List<Map<String, Object>> getPrescriptionList(Long customerId, String start, String end) {
        // 定义返回变量
        List<Map<String, Object>> list = new ArrayList<>();
        // 查询处方列表
        QueryWrapper<BuPrescription> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id", customerId)
                .eq("status", 2)
                .ge("update_time", start)
                .lt("update_time", end)
                .last("limit 100");
        List<BuPrescription> prescriptions = mapper.selectList(wrapper);
        // 查询每个处方的项目列表和患者信息
        for (BuPrescription prescription : prescriptions) {
            // 查询患者信息
            BuPatient patient = patientMapper.selectById(prescription.getPatientId());
            // 查询挂号信息
            BuOutpatient outpatient = outpatientMapper.selectById(prescription.getOutpatientId());
            // 查询处方项列表
            List<BuPrescriptionItem> itemlist = getItems(prescription.getId());
            // 整理数据结构
            JSONObject jsonObj = (JSONObject) JSONObject.toJSON(prescription);
            jsonObj.put("itemList", itemlist);
            jsonObj.put("patient", patient);
            jsonObj.put("outpatient", outpatient);
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

    @Override
    public void setPaymentStatus(Long id, Long userId) {
        BuPrescription buPrescription = mapper.selectById(Wrappers.<BuPrescription>lambdaQuery().eq(BuPrescription::getId, id).eq(BuPrescription::getDoctorId,userId).eq(BuPrescription::getPaymentStatus,0).last("for update "));

        if(buPrescription == null) {
            throw new BusinessException("未找到这条数据");
        }

        if(buPrescription.getPaymentStatus() == 1) {
            throw new BusinessException("当前处方以结算，请勿重复结算");
        }


        if(buPrescription.getStatus() == 2) {
            List<BuPrescriptionItem> buPrescriptionItems = itemMapper.selectList(Wrappers.<BuPrescriptionItem>lambdaQuery().eq(BuPrescriptionItem::getPrescriptionId, id));
            for (BuPrescriptionItem item : buPrescriptionItems) {
                if(buPrescription.getType() == 2) {
                    if(item.getHerbsId() == null || item.getCustomerHerbsId() == null) {
                        throw new BusinessException("客户中药标示和中药标示不能为空");
                    }
                    int c = hsCustomerHerbsMapper.updateDeductionInventory(item.getCustomerHerbsId(),item.getDosage());
                    if(c <= 0) {
                        throw new BusinessException(item.getName() + "库存不足");
                    }
                }
            }


        }else {
            throw new BusinessException("当前数据未打印，不能结算");
        }


    }


    private void addItems(BuPrescription prescription) {
        Date date = new Date();

        for (BuPrescriptionItem item : prescription.getItemList()) {

            item.setType(prescription.getType());
            item.setCustomerId(prescription.getCustomerId());
            item.setDepartmentId(prescription.getDepartmentId());
            item.setDoctorId(prescription.getDoctorId());
            item.setPatientId(prescription.getPatientId());
            item.setOutpatientId(prescription.getOutpatientId());
            item.setPrescriptionId(prescription.getId());
            item.setCreateTime(date);
            item.setUpdateTime(date);
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
