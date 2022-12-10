package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TeBgq;
import com.maizhiyu.yzt.entity.TsAssess;

import java.util.List;
import java.util.Map;

public interface ITsAssessService extends IService<TsAssess> {

    public Integer addAssess(TsAssess assess);

    public Integer delAssess(Long id);

    public Integer setAssess(TsAssess assess);

    public TsAssess getAssess(Long id);

    public List<Map<String,Object>> getAssessList(Long customerId, Long sytechId, String startDate, String endDate, String term);
}
