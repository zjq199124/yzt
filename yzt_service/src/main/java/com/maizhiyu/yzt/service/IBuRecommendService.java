package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.ro.BuDiagnoseRO;
import com.maizhiyu.yzt.serviceimpl.BuRecommendService;
import com.maizhiyu.yzt.vo.BuDiagnoseVO;

import java.util.List;
import java.util.Map;

public interface IBuRecommendService  {

    Map<String, Object> getRecommend(BuDiagnose diagnose);

    Map<String, Object> getRecommendByDisease(BuDiagnose diagnose);

    Map<String, Object> getRecommendBySymptom(BuDiagnose diagnose);

    Map<String, Object> selectRecommend(BuDiagnoseRO.GetRecommendRO ro) throws Exception;
}
