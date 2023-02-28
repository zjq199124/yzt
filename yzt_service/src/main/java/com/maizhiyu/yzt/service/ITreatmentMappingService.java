package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TreatmentMapping;

import java.util.List;

public interface ITreatmentMappingService extends IService<TreatmentMapping> {


    List<TreatmentMapping> selectByCodeList(Long customerId, List<Long> codeList);
}
