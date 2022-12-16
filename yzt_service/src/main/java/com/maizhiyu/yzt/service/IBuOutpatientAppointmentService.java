package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;

public interface IBuOutpatientAppointmentService extends IService<BuOutpatientAppointment> {
    Page<BuOutpatientAppointment> list(OutpatientAppointmentRo outpatientAppointmentRo);
}
