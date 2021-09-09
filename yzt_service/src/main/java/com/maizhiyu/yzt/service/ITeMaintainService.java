package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TeMaintain;

import java.util.List;
import java.util.Map;

public interface ITeMaintainService {

    public Integer addMaintain(TeMaintain maintain);

    public Integer delMaintain(Long id);

    public Integer setMaintain(TeMaintain maintain);

    public TeMaintain getMaintain(Long id);

    public List<Map<String, Object>> getMaintainList(Long equipId, Integer type, String startDate, String endDate);
}
