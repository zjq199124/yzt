package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import com.maizhiyu.yzt.entity.TranPrescription;
import com.maizhiyu.yzt.entity.TranPrescriptionItem;
import com.maizhiyu.yzt.mapper.TranPrescriptionMapper;
import com.maizhiyu.yzt.service.ITranPrescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
public class TranPrescriptionService extends ServiceImpl<TranPrescriptionMapper, TranPrescription> implements ITranPrescriptionService {

    @Resource
    private TranPrescriptionItemService tranPrescriptionItemService;

    @Override
    public Boolean setPrescriptionByDiff(TranPrescription tranPrescription, List<Long> preItemIdList) {
        //保存前先检查是否有删除
        List<TranPrescriptionItem> itemList = tranPrescription.getItemList();
        if (!CollectionUtils.isEmpty(preItemIdList)) {
            List<Long> itemIdList = itemList.stream().filter(item -> Objects.nonNull(item.getId())).map(TranPrescriptionItem::getId).collect(Collectors.toList());
            List<Long> deleteIdList = preItemIdList.stream().filter(item -> !(itemIdList.contains(item))).collect(Collectors.toList());
            if (!org.springframework.util.CollectionUtils.isEmpty(deleteIdList)) {
                tranPrescriptionItemService.deleteByIdList(deleteIdList);
            }
        }
        return addPrescription(tranPrescription);
    }

    public boolean addPrescription(TranPrescription tranPrescription) {
        // 生成编码
        if (tranPrescription.getCode() == null) {
            String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
            tranPrescription.setCode(code);
        }
        // 新增处方
        boolean res = saveOrUpdate(tranPrescription);
        // 增加处方项
        boolean save = saveOrUpdateItems(tranPrescription);
        // 返回结果
        return res && save;
    }

    private boolean saveOrUpdateItems(TranPrescription tranPrescription) {
        if (CollectionUtils.isEmpty(tranPrescription.getItemList()))
            return false;

        List<TranPrescriptionItem> item = tranPrescription.getItemList().stream().map(e -> {
            e.setType(tranPrescription.getType());
            e.setCustomerId(tranPrescription.getCustomerId());
            e.setDepartmentId(tranPrescription.getDepartmentId());
            e.setDoctorId(tranPrescription.getDoctorId());
            e.setPatientId(tranPrescription.getPatientId());
            e.setOutpatientId(tranPrescription.getOutpatientId());
            e.setPrescriptionId(tranPrescription.getId());
            return e;
        }).collect(Collectors.toList());
        return tranPrescriptionItemService.saveOrUpdateBatch(item);
    }
}
