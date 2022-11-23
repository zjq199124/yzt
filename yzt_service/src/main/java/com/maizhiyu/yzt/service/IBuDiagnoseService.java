package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.BuDiagnoseRO;

import java.util.List;
import java.util.Map;

public interface IBuDiagnoseService {

    Integer addDiagnose(BuDiagnose diagnose);

    Integer setDiagnose(BuDiagnose diagnose);

    BuDiagnose getDiagnose(Long id);

    BuDiagnose getDiagnoseOfOutpatient(Long outpatientId);

    List<Map<String, Object>> getDiagnoseList(Long customerId, String start, String end);

    Integer saveOrUpdate(BuDiagnose buDiagnose);

    Result getDetails(BuDiagnoseRO.GetRecommendRO ro) throws Exception;
}
