package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.ro.*;
import com.maizhiyu.yzt.vo.BuPrescriptionItemTaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (BuPrescriptionItemTask)表服务接口
 *
 * @author makejava
 * @since 2023-02-01 15:10:35
 */
public interface BuPrescriptionItemTaskService extends IService<BuPrescriptionItemTask> {

    Page<BuPrescriptionItemTaskVo> selectWaitSignatureList(WaitSignatureRo waitSignatureRo);

    Boolean signature(ItemTaskRo itemTaskRo);

    Page<BuPrescriptionItemTaskVo> waitTreatmentList(WaitTreatmentRo waitTreatmentRo);

    boolean startTreatment(ItemTaskRo itemTaskRo);

    boolean endTreatment(Long id);

    Page<BuPrescriptionItemTaskVo> treatmentList(ItemTaskRo itemTaskRo);

    BuPrescriptionItemTaskVo treatmentRecordDetail(Long id);

    Boolean appointment(AppointmentRo appointmentRo);

    Boolean deleteAppointment(Long buPrescriptionItemTaskId);

    void updateNeedCancelTaskByItemAppointmentIdList(List<Long> prescriptionItemAppointmentIdList);

    List<BuPrescriptionItemTask> selectRemindLatestAppointmentList(Date startDate, Date endDate);

    List<BuPrescriptionItemTask> selectTreatmentRemindList(Date startDate, Date endDate);

    List<BuPrescriptionItemTask> selectOutpatientAppointmentByTerm(Long customerId, String term);

    List<BuPrescriptionItemTask> selectItemListByOutpatientAppointmentId(@Param("outpatientAppointmentId") Long outpatientAppointmentId);
}

