package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TeFault;
import com.maizhiyu.yzt.entity.TeFaultSolution;

import java.util.List;
import java.util.Map;

public interface ITeFaultService  extends IService<TeFault> {

    Integer addFault(TeFault fault);

    Integer delFault(Long id);

    Integer setFault(TeFault fault);

    TeFault getFault(Long id);

    List<Map<String, Object>> getFaultList(Long customId, Integer status, String code);

    List<TeFaultSolution> getFaultSolutionList(Integer type);
}
