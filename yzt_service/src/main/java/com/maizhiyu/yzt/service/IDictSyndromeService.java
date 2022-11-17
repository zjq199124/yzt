package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.vo.DictSyndromeVo;

import java.util.List;

public interface IDictSyndromeService {
    List<DictSyndromeVo> selectByDiseaseId(Long diseaseId);

    List<DictSyndromeVo> selectDictSyndromeBySymptomIdList(List<Long> symptomIdList);
}
