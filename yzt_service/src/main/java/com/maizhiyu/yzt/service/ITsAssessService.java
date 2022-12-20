package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TsAssess;

import java.util.Map;

public interface ITsAssessService extends IService<TsAssess> {

    public Integer addAssess(TsAssess assess);

    public Integer delAssess(Long id);

    public Integer setAssess(TsAssess assess);

    public TsAssess getAssess(Long id);

    IPage<Map<String,Object>> getAssessList(Page page, Long customerId, Long sytechId, String startDate, String endDate, String term);
}
