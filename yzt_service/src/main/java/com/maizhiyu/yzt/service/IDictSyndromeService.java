package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.vo.DictSyndromeVo;

import java.util.List;

public interface IDictSyndromeService {
    List<DictSyndromeVo> selectByDiseaseId(Long diseaseId,String search);

    List<DictSyndromeVo> selectDictSyndromeBySymptomIdList(List<Long> symptomIdList);
}
