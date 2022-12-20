package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointment;

public interface IBuPrescriptionItemAppointmentService extends IService<BuPrescriptionItemAppointment> {
    /**
     * 根据门诊预约信息表的主键id和处方技术小项目主键id查询处方技术小项目的预约信息
     * @param outpatientAppointmentId
     * @param prescriptionItemId
     * @return
     */
    BuPrescriptionItemAppointment selectByAppointmentIdAndItemId(Long outpatientAppointmentId,Long prescriptionItemId);
}
