package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TranPrescription;

import java.util.List;

public interface ITranPrescriptionService extends IService<TranPrescription> {


    Boolean setPrescriptionByDiff(TranPrescription tranPrescription, List<Long> preItemIdList);
}
