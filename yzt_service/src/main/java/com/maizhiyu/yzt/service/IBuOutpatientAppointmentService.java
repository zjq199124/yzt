package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;

import java.util.List;

public interface IBuOutpatientAppointmentService extends IService<BuOutpatientAppointment> {
    List<BuOutpatientAppointment> list(OutpatientAppointmentRo outpatientAppointmentRo);
}
