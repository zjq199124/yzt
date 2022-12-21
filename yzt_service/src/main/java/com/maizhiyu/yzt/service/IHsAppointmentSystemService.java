package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.HsAppointmentSystem;
import com.maizhiyu.yzt.vo.TimeSlotDetailVo;

import java.util.Date;
import java.util.List;

public interface IHsAppointmentSystemService extends IService<HsAppointmentSystem> {
    Boolean add(HsAppointmentSystem hsAppointmentSystem);

    HsAppointmentSystem getNowTimeSlot(Long customerId);

    HsAppointmentSystem getLastNowTimeSlot(Long customerId);

    /**
     * 根据日期和客户id查询当前的时段设置数据
     * @param customerId
     * @param date
     * @return
     */
    List<TimeSlotDetailVo> getTimeSlotByDate(Long customerId, Long outpatientAppointmentId, Date date);
}
