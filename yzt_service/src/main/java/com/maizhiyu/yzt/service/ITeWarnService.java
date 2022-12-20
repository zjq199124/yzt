package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TeWarn;

import java.util.List;
import java.util.Map;

public interface ITeWarnService extends IService<TeWarn> {

    Integer addWarn(TeWarn warn);

    IPage<Map<String, Object>> getWarnList(Page page, String date, Long agencyId, Long customId, Integer type, Long modelId, Long equipId);

    List<Map<String, Object>> getWarnListOfRun(
            String code, String runid);
}
