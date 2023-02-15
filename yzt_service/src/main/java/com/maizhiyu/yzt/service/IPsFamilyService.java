package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.PsFamily;

import java.util.List;
import java.util.Map;

public interface IPsFamilyService extends IService<PsFamily> {
    List<PsFamily> getFamily(Long userId);

    Boolean addFamily(PsFamily psFamily);
}
