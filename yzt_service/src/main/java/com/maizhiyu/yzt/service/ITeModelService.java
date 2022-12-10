package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TeModel;

import java.util.List;
import java.util.Map;

public interface ITeModelService extends IService<TeModel> {

    public Integer addModel(TeModel model);

    public Integer delModel(Long id);

    public Integer setModel(TeModel model);

    public TeModel getModel(Long id);

    public List<Map<String, Object>> getModelList(Integer status, Integer type, String term);

//    public List<TeModel> getModelList(Integer status, Integer type, String term);
}
