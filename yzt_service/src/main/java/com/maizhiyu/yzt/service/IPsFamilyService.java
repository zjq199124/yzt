package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.PsFmaily;

import java.util.List;
import java.util.Map;

public interface IPsFamilyService extends IService<PsFmaily> {
    List<Map<String, Object>> getFamily(Long userId);

    Boolean addFamily(PsFmaily psFmaily);
}
