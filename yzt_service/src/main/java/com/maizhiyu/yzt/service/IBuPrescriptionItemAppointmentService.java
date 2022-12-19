package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointment;

public interface IBuPrescriptionItemAppointmentService extends IService<BuPrescriptionItemAppointment> {
    BuPrescriptionItemAppointment selectByAppointmentIdAndItemId(Long outpatientAppointmentId, Long prescriptionItemId);
}
