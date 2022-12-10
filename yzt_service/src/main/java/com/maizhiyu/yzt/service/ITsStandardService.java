package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TsStandard;

import java.util.List;
import java.util.Map;

public interface ITsStandardService extends IService<TsStandard> {

    Integer addStandard(TsStandard standard);

    Integer delStandard(Long id);

    Integer setStandard(TsStandard standard);

    TsStandard getStandard(Long id);

    List<Map<String,Object>> getStandardList();
}
