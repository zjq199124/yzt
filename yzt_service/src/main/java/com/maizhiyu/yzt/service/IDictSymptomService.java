package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.DictSymptom;
import com.maizhiyu.yzt.vo.DictSymptomVo;

import java.util.List;
import java.util.Map;

public interface IDictSymptomService extends IService<DictSymptom> {

    public Integer addSymptom(DictSymptom symptom);

    public Integer delSymptom(Long id);

    public Integer setSymptom(DictSymptom symptom);

    public DictSymptom getSymptom(Long id);

    IPage<Map<String, Object>> getSymptomList(Page page, Integer status, String term);

    List<DictSymptomVo> selectByDiseaseId(Long diseaseId);
}
