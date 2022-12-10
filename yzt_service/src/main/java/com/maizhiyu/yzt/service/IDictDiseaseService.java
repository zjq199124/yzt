package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.DictDisease;
import com.maizhiyu.yzt.serviceimpl.DictDiseaseService;

import java.util.List;
import java.util.Map;

public interface IDictDiseaseService extends IService<DictDisease> {

    public Integer addDisease(DictDisease agency);

    public Integer delDisease(Long id);

    public Integer setDisease(DictDisease agency);

    public DictDisease getDisease(Long id);

    public List<Map<String,Object>> getDiseaseList(Integer status, String term);
}
