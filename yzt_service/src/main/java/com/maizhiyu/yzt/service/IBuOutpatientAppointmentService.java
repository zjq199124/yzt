package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;

public interface IBuOutpatientAppointmentService extends IService<BuOutpatientAppointment> {
    Page<BuOutpatientAppointment> list(OutpatientAppointmentRo outpatientAppointmentRo);

    BuOutpatientAppointment insert(BuOutpatientAppointment buOutpatientAppointment);

    BuOutpatientAppointment selectByDiagnoseId(Long diagnoseId);

    /**
     * 查询当前诊断下的所有适宜技术的预约信息
     * @param id
     * @return
     */
    BuOutpatientAppointment appointmentDetail(Long id);
}
