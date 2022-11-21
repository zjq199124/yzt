package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
import com.maizhiyu.yzt.result.Result;

import java.util.List;

public interface IYptCommonService {
    Result<List<DictSyndromeVo>> selectDictSyndromeBySymptomIdList(List<Long> symptomIdList);
}
