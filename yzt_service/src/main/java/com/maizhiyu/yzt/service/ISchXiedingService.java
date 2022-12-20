package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.SchXieding;

import java.util.Map;

public interface ISchXiedingService extends IService<SchXieding> {

    public Integer addXieding(SchXieding agency);

    public Integer delXieding(Long id);

    public Integer setXieding(SchXieding agency);

    public SchXieding getXieding(Long id);

    public IPage<Map<String, Object>> getXiedingList(Page page, Long diseaseId, Integer status, String term);
}
