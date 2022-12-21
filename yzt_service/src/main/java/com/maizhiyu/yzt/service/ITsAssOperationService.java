package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TsAssOperation;

import java.util.List;
import java.util.Map;

public interface ITsAssOperationService extends IService<TsAssOperation> {

    public List<Map<String,Object>> getAssDetail(Long sytechId);
}
