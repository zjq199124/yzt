package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.entity.BuDiagnose;

import java.util.List;
import java.util.Map;

public interface IBuDiagnoseService {

    Integer addDiagnose(BuDiagnose diagnose);

    Integer setDiagnose(BuDiagnose diagnose);

    BuDiagnose getDiagnose(Long id);

    BuDiagnose getDiagnoseOfOutpatient(Long outpatientId);

}
