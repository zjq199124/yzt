package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;
import com.maizhiyu.yzt.ro.AppointmentRo;

import java.util.Date;
import java.util.List;

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

    /**
     * 批量预约接口
     * @param appointmentRo
     * @return
     */
    Boolean appointment(AppointmentRo appointmentRo);

    /**
     * 查询当天预约的距离最近的预约数据
     * @param startDate
     * @param endDate
     * @return
     */
    List<BuPrescriptionItemAppointmentItem> selectRemindLatestAppointmentList(Date startDate, Date endDate);

    /**
     * 查询预约在了明天的数据
     * @param startDate
     * @param endDate
     * @return
     */
    List<BuPrescriptionItemAppointmentItem> selectTreatmentRemindList(Date startDate, Date endDate);
}
