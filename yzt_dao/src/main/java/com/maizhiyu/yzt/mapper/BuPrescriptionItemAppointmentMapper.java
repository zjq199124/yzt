package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BuPrescriptionItemAppointmentMapper extends BaseMapper<BuPrescriptionItemAppointment> {
    List<BuPrescriptionItemAppointment> selectByDiagnoseIdList(@Param("diagnoseIdList") List<Long> diagnoseIdList);

    List<BuPrescriptionItemAppointment> selectByOutpatientAppointmentId(@Param("outpatientAppointmentId") Long outpatientAppointmentId);

    List<BuPrescriptionItemAppointment> selectByPrescriptionIdList(@Param("prescriptionIdList") List<Long> prescriptionIdList);
}
