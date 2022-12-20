package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TeMaintain;

import java.util.Map;

public interface ITeMaintainService extends IService<TeMaintain> {

    public Integer addMaintain(TeMaintain maintain);

    public Integer delMaintain(Long id);

    public Integer setMaintain(TeMaintain maintain);

    public TeMaintain getMaintain(Long id);

    IPage<Map<String, Object>> getMaintainList(Page page, Long customerId, Long equipId, Integer type, String startDate, String endDate);
}
