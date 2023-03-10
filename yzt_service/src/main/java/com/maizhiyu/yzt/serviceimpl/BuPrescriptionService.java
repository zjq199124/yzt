package com.maizhiyu.yzt.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.*;
import com.maizhiyu.yzt.service.IBuPrescriptionItemService;
import com.maizhiyu.yzt.service.IBuPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
public class BuPrescriptionService extends ServiceImpl<BuPrescriptionMapper, BuPrescription> implements IBuPrescriptionService {

    @Autowired
    private IBuPrescriptionItemService buPrescriptionItemService;

    @Autowired
    private BuPatientMapper patientMapper;

    @Autowired
    private BuOutpatientMapper outpatientMapper;

    @Autowired
    private HsUserMapper hsUserMapper;

    @Autowired
    private HsCustomerHerbsMapper hsCustomerHerbsMapper;

    @Resource
    private BuPrescriptionMapper buPrescriptionMapper;

    @Override
    public boolean addPrescription(BuPrescription prescription) {
        // 生成编码
        if (prescription.getCode() == null) {
            String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
            prescription.setCode(code);
        }
        // 新增处方
        boolean res = saveOrUpdate(prescription);
        // 增加处方项
        boolean save = saveOrUpdateItems(prescription);
        // 返回结果
        return res && save;
    }

    @Override
    public boolean delPrescription(Long id) {
        // 删除处方
        boolean res = removeById(id);
        // 删除处方项
        Boolean dres = delItems(id);
        // 返回数据
        return res && dres;
    }

    @Override
    public Boolean setPrescription(BuPrescription prescription) {
        // 获取原来处方数据
        BuPrescription original = baseMapper.selectById(prescription.getId());
        // 更新处方
        prescription.setOutpatientId(original.getOutpatientId());
        prescription.setDoctorId(original.getDoctorId());
        prescription.setPatientId(original.getPatientId());
        Boolean res = updateById(prescription);
        // 删除处方项
        Boolean res1 = delItems(prescription.getId());
        // 增加处方项
        Boolean res2 = saveOrUpdateItems(prescription);
        // 返回结果
        return res && res1 && res2;
    }

    //TODO 前端没有给 preItemIdList 导致删除的item 没有被删除
    @Override
    public Boolean setPrescriptionByDiff(BuPrescription prescription, List<Long> preItemIdList) {
        //保存前先检查是否有删除
        List<BuPrescriptionItem> itemList = prescription.getItemList();
        if (!CollectionUtils.isEmpty(preItemIdList)) {
            List<Long> itemIdList = itemList.stream().filter(item -> Objects.nonNull(item.getId())).map(BuPrescriptionItem::getId).collect(Collectors.toList());
            List<Long> deleteIdList = preItemIdList.stream().filter(item -> !(itemIdList.contains(item))).collect(Collectors.toList());
            if (!org.springframework.util.CollectionUtils.isEmpty(deleteIdList)) {
                buPrescriptionItemService.deleteByIdList(deleteIdList);
            }
        }
        return addPrescription(prescription);
    }


    @Override
    public Boolean setPrescriptionStatus(BuPrescription prescription) {
        return updateById(prescription);
    }

    @Override
    public BuPrescription getPrescription(Long id) {
        // 获取处方
        BuPrescription prescription = baseMapper.selectById(id);
        // 获取医生
        HsUser user = hsUserMapper.selectById(prescription.getDoctorId());
        // 获取处方项
        prescription.setDoctorName(user.getRealName());
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
        List<BuPrescription> prescriptions = baseMapper.selectList(wrapper);
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
        List<BuPrescription> prescriptions = baseMapper.selectList(wrapper);
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
        return baseMapper.selectPrescriptionItemSummary(prescriptionId);
    }

    @Override
    public List<Map<String, Object>> getPatientPrescriptionItemSummary(Long patientId) {
        return baseMapper.selectPatientPrescriptionItemSummary(patientId);
    }

    @Override
    public void setPaymentStatus(Long id, Long userId) {
        BuPrescription buPrescription = baseMapper.selectById(Wrappers.<BuPrescription>lambdaQuery().eq(BuPrescription::getId, id).eq(BuPrescription::getDoctorId, userId).eq(BuPrescription::getPaymentStatus, 0).last("for update "));
        if (buPrescription == null) {
            throw new BusinessException("未找到这条数据");
        }
        if (buPrescription.getPaymentStatus() == 1) {
            throw new BusinessException("当前处方以结算，请勿重复结算");
        }
        if (buPrescription.getStatus() == 2) {
            List<BuPrescriptionItem> buPrescriptionItems = buPrescriptionItemService.getBaseMapper().selectList(Wrappers.<BuPrescriptionItem>lambdaQuery().eq(BuPrescriptionItem::getPrescriptionId, id));
            for (BuPrescriptionItem item : buPrescriptionItems) {
                if (buPrescription.getType() == 2) {
                    if (item.getHerbsId() == null || item.getCustomerHerbsId() == null) {
                        throw new BusinessException("客户中药标示和中药标示不能为空");
                    }
                    int c = hsCustomerHerbsMapper.updateDeductionInventory(item.getCustomerHerbsId(), item.getDosage());
                    if (c <= 0) {
                        throw new BusinessException(item.getName() + "库存不足");
                    }
                }
            }

        } else {
            throw new BusinessException("当前数据未打印，不能结算");
        }
    }

    @Override
    public List<BuPrescription> selectByIdList(List<Long> prescriptionIdList) {
        LambdaQueryWrapper<BuPrescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BuPrescription::getId, prescriptionIdList);
        return buPrescriptionMapper.selectList(queryWrapper);
    }

    @Override
    public List<BuPrescription> selectByHisIdList(List<Long> hisIdList) {
        LambdaQueryWrapper<BuPrescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BuPrescription::getHisId, hisIdList);
        List<BuPrescription> buPrescriptionList = buPrescriptionMapper.selectByHisIdList(hisIdList);
        return buPrescriptionList;
    }

    private boolean saveOrUpdateItems(BuPrescription prescription) {
        if (CollectionUtils.isEmpty(prescription.getItemList()))
            return false;

        List<BuPrescriptionItem> item = prescription.getItemList().stream().map(e -> {
            e.setType(prescription.getType());
            e.setCustomerId(prescription.getCustomerId());
            e.setDepartmentId(prescription.getDepartmentId());
            e.setDoctorId(prescription.getDoctorId());
            e.setPatientId(prescription.getPatientId());
            e.setOutpatientId(prescription.getOutpatientId());
            e.setPrescriptionId(prescription.getHisId());
            return e;
        }).collect(Collectors.toList());
        return buPrescriptionItemService.saveOrUpdateBatch(item);
    }


    private Boolean delItems(Long prescriptionId) {
        QueryWrapper<BuPrescriptionItem> wrapper = new QueryWrapper<>();
        wrapper.eq("prescription_id", prescriptionId);
        return buPrescriptionItemService.remove(wrapper);
    }


    private List<BuPrescriptionItem> getItems(Long prescriptionId) {
        QueryWrapper<BuPrescriptionItem> itemQueryWrapper = new QueryWrapper<>();
        itemQueryWrapper.eq("prescription_id", prescriptionId);
        return buPrescriptionItemService.list(itemQueryWrapper);
    }
}
