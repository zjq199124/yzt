package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuCure;
import com.maizhiyu.yzt.entity.BuDealTaskRecord;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.ro.*;
import com.maizhiyu.yzt.vo.BuPrescriptionItemTaskVo;

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
}

