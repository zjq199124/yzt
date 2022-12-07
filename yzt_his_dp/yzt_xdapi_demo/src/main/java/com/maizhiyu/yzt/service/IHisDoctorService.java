package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.HisDoctor;

import java.util.Date;
import java.util.List;

public interface IHisDoctorService {

    List<HisDoctor> getDoctorList(Date startTime, Date endTime);

}
