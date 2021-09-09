package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TsStandard;

import java.util.List;
import java.util.Map;

public interface ITsStandardService {

    Integer addStandard(TsStandard standard);

    Integer delStandard(Long id);

    Integer setStandard(TsStandard standard);

    TsStandard getStandard(Long id);

    List<Map<String,Object>> getStandardList();
}
