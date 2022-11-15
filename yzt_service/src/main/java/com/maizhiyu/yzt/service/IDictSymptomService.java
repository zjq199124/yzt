package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.DictSymptom;
import com.maizhiyu.yzt.vo.DictSymptomVo;

import java.util.List;
import java.util.Map;

public interface IDictSymptomService {

    public Integer addSymptom(DictSymptom symptom);

    public Integer delSymptom(Long id);

    public Integer setSymptom(DictSymptom symptom);

    public DictSymptom getSymptom(Long id);

    public List<Map<String,Object>> getSymptomList(Integer status, String term);

    List<DictSymptomVo> selectByDiseaseId(Long diseaseId);
}
