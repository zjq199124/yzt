package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.DictDisease;

import java.util.Map;

public interface IDictDiseaseService extends IService<DictDisease> {

    public Integer addDisease(DictDisease agency);

    public Integer delDisease(Long id);

    public Integer setDisease(DictDisease agency);

    public DictDisease getDisease(Long id);

    public IPage<Map<String, Object>> getDiseaseList(Page page, Integer status, String term);
}
