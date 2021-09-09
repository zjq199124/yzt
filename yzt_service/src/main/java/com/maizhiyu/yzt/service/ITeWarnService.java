package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TeWarn;

import java.util.List;
import java.util.Map;

public interface ITeWarnService {

    Integer addWarn(TeWarn warn);

    List<Map<String,Object>> getWarnList(
            String date, Long agencyId, Long customId, Integer type, Long modelId);

    List<Map<String,Object>> getWarnListOfRun(
            String code, String runid);
}
