package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;

public interface IBuPrescriptionItemAppointmentItemService extends IService<BuPrescriptionItemAppointmentItem> {
    Boolean makeAppointment(BuPrescriptionItemAppointmentItem buPrescriptionItemAppointmentItem);
}
