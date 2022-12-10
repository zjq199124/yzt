package com.maizhiyu.yzt.service;

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

    public List<Map<String,Object>> getSymptomList(Integer status, String term);

    List<DictSymptomVo> selectByDiseaseId(Long diseaseId);
}
