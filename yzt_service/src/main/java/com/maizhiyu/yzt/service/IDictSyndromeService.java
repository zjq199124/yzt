package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.DictSymptom;
import com.maizhiyu.yzt.vo.DictSymptomVo;
import com.maizhiyu.yzt.vo.DictSyndromeVo;

import java.util.List;
import java.util.Map;

public interface IDictSyndromeService {
    List<DictSyndromeVo> selectByDiseaseId(Long diseaseId);

    List<DictSyndromeVo> selectDictSyndromeBySymptomIdList(List<Long> symptomIdList);
}
