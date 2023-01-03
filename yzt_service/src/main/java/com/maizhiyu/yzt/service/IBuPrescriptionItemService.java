package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.vo.WaitSignatureVo;

import java.util.List;

public interface IBuPrescriptionItemService extends IService<BuPrescriptionItem> {

    Integer addPrescriptionItem(BuPrescriptionItem item);

    Integer delPrescriptionItem(Long id);

    Integer setPrescriptionItem(BuPrescriptionItem item);

    List<BuPrescriptionItem> getPrescriptionItemList(Long prescriptionId);

    void deleteByIdList(List<Long> deleteIdList);

    Page<WaitSignatureVo> selectWaitSignatureList(WaitSignatureRo waitSignatureRo);

    List<BuPrescriptionItem> getPrescriptionItemListByDiagnoseId(Long diagnoseId);
}

