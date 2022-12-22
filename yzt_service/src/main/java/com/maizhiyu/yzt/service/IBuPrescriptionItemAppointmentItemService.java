package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;

public interface IBuPrescriptionItemAppointmentItemService extends IService<BuPrescriptionItemAppointmentItem> {
    /**
     * 进行预约操作
     * @param buPrescriptionItemAppointmentItem
     * @return
     */
    Boolean makeAppointment(BuPrescriptionItemAppointmentItem buPrescriptionItemAppointmentItem);

    /**
     * 删除预约
     * @param buPrescriptionItemAppointmentItemId
     * @return
     */
    Boolean deleteAppointment(Long buPrescriptionItemAppointmentItemId);
}
