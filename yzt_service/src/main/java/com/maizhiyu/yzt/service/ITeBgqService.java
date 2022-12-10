package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TeBgq;

import java.util.List;
import java.util.Map;

public interface ITeBgqService extends IService<TeBgq> {

    Integer addBgq(TeBgq bgq);

    Integer delBgq(Long id);

    Integer setBgq(TeBgq bgq);

    TeBgq getBgq(Long id);

    List<TeBgq> getBgqList(Long agencyId, Long customId, Integer status, String term);

    List<Map<String, Object>> getBgqListWithRunData(Long agencyId, Long customId, Integer status, String term);
}
