package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.YptTreatment;

import java.util.List;

public interface IYptTreatmentService {

    Integer addTreatment(YptTreatment medicant);

    Integer delTreatment(Integer id);

    Integer setTreatment(YptTreatment medicant);

    YptTreatment getTreatment(Integer id);

    YptTreatment getTreatmentByCode(String code);

    YptTreatment getTreatmentByName(String name);

    YptTreatment getTreatmentByHisCode(String code);

    YptTreatment getTreatmentByHisName(String name);

    List<YptTreatment> getTreatmentList(String term);

}
