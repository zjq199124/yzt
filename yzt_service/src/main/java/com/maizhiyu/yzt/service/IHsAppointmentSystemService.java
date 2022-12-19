package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.HsAppointmentSystem;

public interface IHsAppointmentSystemService extends IService<HsAppointmentSystem> {
    Boolean add(HsAppointmentSystem hsAppointmentSystem);

    HsAppointmentSystem getNowTimeSlot(Long customerId);
}
