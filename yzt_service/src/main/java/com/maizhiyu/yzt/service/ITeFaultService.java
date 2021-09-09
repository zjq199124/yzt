package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TeFault;
import com.maizhiyu.yzt.entity.TeFaultSolution;

import java.util.List;
import java.util.Map;

public interface ITeFaultService {

    Integer addFault(TeFault fault);

    Integer delFault(Long id);

    Integer setFault(TeFault fault);

    TeFault getFault(Long id);

    List<Map<String, Object>> getFaultList(Long customId, Integer status);

    List<TeFaultSolution> getFaultSolutionList(Integer type);
}
