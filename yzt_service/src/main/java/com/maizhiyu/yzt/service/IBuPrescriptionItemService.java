package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.entity.BuPrescriptionItem;

import java.util.List;
import java.util.Map;

public interface IBuPrescriptionItemService extends IService<BuPrescriptionItem> {

    Integer addPrescriptionItem(BuPrescriptionItem item);

    Integer delPrescriptionItem(Long id);

    Integer setPrescriptionItem(BuPrescriptionItem item);

    List<BuPrescriptionItem> getPrescriptionItemList(Long prescriptionId);

    void deleteByIdList(List<Long> deleteIdList);
}
