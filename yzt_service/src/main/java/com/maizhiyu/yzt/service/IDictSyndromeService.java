package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.DictSyndrome;
import com.maizhiyu.yzt.vo.DictSyndromeVo;

import java.util.List;

public interface IDictSyndromeService   {
    List<DictSyndromeVo> selectByDiseaseId(Long diseaseId,String search);

    List<DictSyndromeVo> selectDictSyndromeBySymptomIdList(Long diseaseId,List<Long> symptomIdList);
}
