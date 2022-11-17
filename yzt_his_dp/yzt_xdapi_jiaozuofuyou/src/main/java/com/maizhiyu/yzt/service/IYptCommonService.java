package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
import com.maizhiyu.yzt.result.Result;

import java.util.List;

public interface IYptCommonService {
    Result getRecommend(Long diseaseId,Long syndromeId,String term);

    Result<List<DictSyndromeVo>> selectDictSyndromeBySymptomIdList(List<Long> symptomIdList);
}
