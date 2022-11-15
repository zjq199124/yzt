package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.bean.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.result.Result;

public interface IYptRecommendService {
    Result getRecommend(Long diseaseId,Long syndromeId,String term);
}
