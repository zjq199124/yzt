package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


@Mapper
public interface BuPrescriptionItemAppointmentItemMapper extends BaseMapper<BuPrescriptionItemAppointmentItem> {
    List<BuPrescriptionItemAppointmentItem> selectByPrescriptionItemAppointmentIdList(@Param("prescriptionItemAppointmentIdList") List<Long> prescriptionItemAppointmentIdList);

    List<BuPrescriptionItemAppointmentItem> selectByTimeSlot(@Param("customerId") Long customerId, @Param("outpatientAppointmentId") Long outpatientAppointmentId, @Param("date") Date date, @Param("morningTimeSlotList") List<String> morningTimeSlotList);

    List<BuPrescriptionItemAppointmentItem> selectRemindLatestAppointmentList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<BuPrescriptionItemAppointmentItem> selectTreatmentRemindList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
