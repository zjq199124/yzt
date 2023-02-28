package com.maizhiyu.yzt.task;

import cn.hutool.core.map.MapUtil;
import com.aliyun.core.utils.MapUtils;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.enums.AppointmentTypeEnum;
import com.maizhiyu.yzt.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SplitTask {

    @Resource
    private IBuPrescriptionService buPrescriptionService;

    @Resource
    private IBuPrescriptionItemService buPrescriptionItemService;

    @Resource
    private IBuPrescriptionItemAppointmentService buPrescriptionItemAppointmentService;

    @Resource
    private IBuOutpatientAppointmentService buOutpatientAppointmentService;

    @Resource
    private BuPrescriptionItemTaskService buPrescriptionItemTaskService;

    @Resource
    private IBuDealTaskRecordService buDealTaskRecordService;

    @Resource
    private IBuOutpatientService buOutpatientService;

    @Scheduled(cron = "0 0/2 * * * ?")
    private void splitTask() {
        Date startDate = null;
        Date endDate = new Date();

        BuDealTaskRecord buDealTaskRecord = buDealTaskRecordService.selectLastDealTime();

        if (Objects.nonNull(buDealTaskRecord)) {
            startDate = buDealTaskRecord.getDealTime();
        }

        //1:查询这个时间内有更改的处治小项目数据
        List<BuPrescriptionItem> buPrescriptionItemList = buPrescriptionItemService.selectHasUpdateData(startDate, endDate);

        if(CollectionUtils.isEmpty(buPrescriptionItemList))
            return;

        //查找还未拆分的处治小项目的数据集合
        List<BuPrescriptionItem> insertBuPrescriptionItemList = buPrescriptionItemList.stream().filter(item -> item.getIsSplit() == 0).collect(Collectors.toList());

        //查询出已经拆分了的但是经过了修改的数据，这里的修改只能是作废，不允许增加几次，减少几次之类的
        List<BuPrescriptionItem> cancelBuPrescriptionItemList = buPrescriptionItemList.stream().filter(item -> (item.getIsSplit() == 1 && item.getIsCancel() == 1)).collect(Collectors.toList());

        //3：处理新增的处治数据
        if (!CollectionUtils.isEmpty(insertBuPrescriptionItemList)) {
            dealInsertBuPrescriptionItemData(insertBuPrescriptionItemList);
        }

        //4：处理要作废的处治数据
        if (!CollectionUtils.isEmpty(cancelBuPrescriptionItemList)) {
            dealCancelBuPrescriptionItemData(cancelBuPrescriptionItemList);
        }

        //5：保存最后一次拆分任务的时间
        BuDealTaskRecord lastRecord = new BuDealTaskRecord();
        lastRecord.setDealTime(endDate);
        buDealTaskRecordService.save(lastRecord);
    }

    private void dealCancelBuPrescriptionItemData(List<BuPrescriptionItem> buPrescriptionItemList) {

        //1:查询出处治小项目对应的预约汇总数据
        List<Long> prescriptionItemHisIdList = buPrescriptionItemList.stream().map(BuPrescriptionItem::getHisId).collect(Collectors.toList());
        List<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentList = buPrescriptionItemAppointmentService.selectByItemHisIdList(prescriptionItemHisIdList);
        if(CollectionUtils.isEmpty(buPrescriptionItemAppointmentList))
            return;

        //2:查询出对应的处治小项目下的不是治疗中或不是已治疗的任务数据都改成已作废
        List<Long> prescriptionItemAppointmentIdList = buPrescriptionItemAppointmentList.stream().map(BuPrescriptionItemAppointment::getId).collect(Collectors.toList());
        buPrescriptionItemTaskService.updateNeedCancelTaskByItemAppointmentIdList(prescriptionItemAppointmentIdList);

        //3：将对应的处治小项目的预约汇总数据改成已作废
        buPrescriptionItemAppointmentList.forEach(item -> {
            item.setIsCancel(1);
            item.setUpdateTime(new Date());
        });
        buPrescriptionItemAppointmentService.saveOrUpdateBatch(buPrescriptionItemAppointmentList);

        //4:查询并且更新对应的处治的状态
        List<Long> outpatientAppointmentIdList = buPrescriptionItemAppointmentList.stream().map(BuPrescriptionItemAppointment::getOutpatientAppointmentId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        List<BuOutpatientAppointment>  buOutpatientAppointmentList = buOutpatientAppointmentService.selectByIdList(outpatientAppointmentIdList);
        if(CollectionUtils.isEmpty(buOutpatientAppointmentList))
            return;
        Map<Long, List<BuPrescriptionItemAppointment>> outpatientAppointmentIdMap = buPrescriptionItemAppointmentList.stream().collect(Collectors.groupingBy(BuPrescriptionItemAppointment::getOutpatientAppointmentId));

        //判断处治对应的预约数据是否需要调整
        buOutpatientAppointmentList.forEach(item -> {
            List<BuPrescriptionItemAppointment> list = outpatientAppointmentIdMap.get(item.getId());
            if(CollectionUtils.isEmpty(list))
                return;

            //5.1:查询是否有不是未预约的处治小项目预约数据
            int size = list.stream().filter(temp -> temp.getState() != AppointmentTypeEnum.NO_APPOINTMENT.code()).collect(Collectors.toList()).size();
            if (size == 0) {
                item.setState(AppointmentTypeEnum.NO_APPOINTMENT.code());
                return;
            }

            //5.1:查询是否有预约中的处治小项目预约数据
            size = list.stream().filter(temp -> temp.getState() == AppointmentTypeEnum.IN_APPOINTMENT.code()).collect(Collectors.toList()).size();
            if (size > 0) {
                item.setState(AppointmentTypeEnum.IN_APPOINTMENT.code());
                return;
            } else {
                //5.1:查询是否有预约已完成的处治小项目预约数据
                size = list.stream().filter(temp -> temp.getState() == AppointmentTypeEnum.COMPLETE_APPOINTMENT.code()).collect(Collectors.toList()).size();
                if (size > 0) {
                    item.setState(AppointmentTypeEnum.COMPLETE_APPOINTMENT.code());
                } else {
                    item.setState(AppointmentTypeEnum.HAS_CANCEL.code());
                }
            }
        });
    }

    private void dealInsertBuPrescriptionItemData(List<BuPrescriptionItem> buPrescriptionItemList) {
        List<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentList = new ArrayList<>();

        //1：创建处治小项目的预约情况汇总数据
        for (BuPrescriptionItem item : buPrescriptionItemList) {
            BuPrescriptionItemAppointment buPrescriptionItemAppointment = new BuPrescriptionItemAppointment();
            buPrescriptionItemAppointment.setCustomerId(item.getCustomerId())
                    .setPatientId(item.getPatientId())
                    .setOutpatientId(item.getOutpatientId())
                    .setEntityId(item.getEntityId())
                    .setName(item.getName())
                    .setPrescriptionId(item.getPrescriptionId())
                    .setPrescriptionItemId(item.getHisId())
                    .setState(1)
                    .setQuantity(item.getQuantity().intValue())
                    .setTreatmentQuantity(0)
                    .setAppointmentQuantity(0)
                    .setSurplusQuantity(buPrescriptionItemAppointment.getQuantity())
                    .setCreateTime(new Date())
                    .setUpdateTime(buPrescriptionItemAppointment.getCreateTime());
            buPrescriptionItemAppointmentList.add(buPrescriptionItemAppointment);
        }

        //2：查询出所对应的处治数据（统一使用hisId字段做关联）
        List<Long> prescriptionIdList = buPrescriptionItemList.stream().map(BuPrescriptionItem::getPrescriptionId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        List<BuPrescription> buPrescriptionList = buPrescriptionService.selectByHisIdList(prescriptionIdList);

        //3：创建处治预约情况汇总数据
        List<BuOutpatientAppointment> buOutpatientAppointmentList = new ArrayList<>();
        for (BuPrescription buPrescription : buPrescriptionList) {
            BuOutpatientAppointment buOutpatientAppointment = new BuOutpatientAppointment();
            buOutpatientAppointment.setCustomerId(buPrescription.getCustomerId());
            buOutpatientAppointment.setPatientId(buPrescription.getPatientId());
            buOutpatientAppointment.setOutpatientId(buPrescription.getOutpatientId());
            buOutpatientAppointment.setDepartmentId(buPrescription.getDepartmentId());
            buOutpatientAppointment.setPrescriptionId(buPrescription.getHisId());
            buOutpatientAppointment.setDiseaseId(buPrescription.getDiseaseId());
            buOutpatientAppointment.setCreateTime(new Date());
            buOutpatientAppointment.setUpdateTime(new Date());
            buOutpatientAppointmentList.add(buOutpatientAppointment);
        }

        //4:插入门诊时间
        Map<Long, List<BuOutpatientAppointment>> customerIdMap = buOutpatientAppointmentList.stream().collect(Collectors.groupingBy(BuOutpatientAppointment::getCustomerId));
        customerIdMap.keySet().forEach(customerId -> {
            List<BuOutpatientAppointment> list = customerIdMap.get(customerId);
            if(CollectionUtils.isEmpty(list))
                return;

            List<Long> outpatientIdList = list.stream().map(BuOutpatientAppointment::getOutpatientId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
            if(CollectionUtils.isEmpty(outpatientIdList))
                return;

            List<BuOutpatient> buOutpatients = buOutpatientService.selectByHisIdListAndCustomerId(customerId, outpatientIdList);
            if(CollectionUtils.isEmpty(buOutpatients))
                return;

            Map<String, BuOutpatient> hisIdMap = buOutpatients.stream().collect(Collectors.toMap(BuOutpatient::getHisId, Function.identity(), (k1, k2) -> k1));

            list.forEach(item -> {
                BuOutpatient buOutpatient = hisIdMap.get(item.getOutpatientId().toString());
                if(Objects.isNull(buOutpatient))
                    return;

                item.setOutpatientTime(buOutpatient.getCreateTime());
            });
        });


        //5：保存处治预约汇总数据
        buOutpatientAppointmentService.saveBatch(buOutpatientAppointmentList,buOutpatientAppointmentList.size());

        //6：给处治小项目对应的预约数据加上总的处治预约对应数据的id
        Map<Long, BuOutpatientAppointment> prescriptionIdMap = buOutpatientAppointmentList.stream().collect(Collectors.toMap(BuOutpatientAppointment::getPrescriptionId, Function.identity(), (k1, k2) -> k1));
        buPrescriptionItemAppointmentList.forEach(item -> {
            BuOutpatientAppointment buOutpatientAppointment = prescriptionIdMap.get(item.getPrescriptionId());
            if(Objects.isNull(buOutpatientAppointment))
                return;

            item.setOutpatientAppointmentId(buOutpatientAppointment.getId());
        });

        //7：保存处治小项目预约数据
        buPrescriptionItemAppointmentService.saveBatch(buPrescriptionItemAppointmentList, buPrescriptionItemAppointmentList.size());

        //8：根据处治小项目预约数据拆分任务
        List<BuPrescriptionItemTask> buPrescriptionItemTaskList = new ArrayList<>();
        for (BuPrescriptionItemAppointment buPrescriptionItemAppointment : buPrescriptionItemAppointmentList) {
            //根据处治小项目的数量拆分任务
            for (int i = 0; i < buPrescriptionItemAppointment.getQuantity(); i++) {
                BuPrescriptionItemTask buPrescriptionItemTask = new BuPrescriptionItemTask();
                buPrescriptionItemTask.setOutpatientAppointmentId(buPrescriptionItemAppointment.getOutpatientAppointmentId());
                buPrescriptionItemTask.setPrescriptionItemAppointmentId(buPrescriptionItemAppointment.getId());
                buPrescriptionItemTask.setCustomerId(buPrescriptionItemAppointment.getCustomerId());
                buPrescriptionItemTask.setPatientId(buPrescriptionItemAppointment.getPatientId());
                buPrescriptionItemTask.setOutpatientId(buPrescriptionItemAppointment.getOutpatientId());
                buPrescriptionItemTask.setPrescriptionId(buPrescriptionItemAppointment.getPrescriptionId());
                buPrescriptionItemTask.setPrescriptionItemId(buPrescriptionItemAppointment.getPrescriptionItemId());
                buPrescriptionItemTask.setEntityId(buPrescriptionItemAppointment.getEntityId());
                buPrescriptionItemTask.setTsName(buPrescriptionItemAppointment.getName());
                buPrescriptionItemTask.setCreateTime(new Date());
                buPrescriptionItemTask.setUpdateTime(buPrescriptionItemTask.getCreateTime());
                buPrescriptionItemTaskList.add(buPrescriptionItemTask);
            }
        }

        //9：给处治小项目设置疾病id，不走我们系统开出来的处置要带上疾病id
        Map<Long, BuPrescription> hisIdMap = buPrescriptionList.stream().collect(Collectors.toMap(BuPrescription::getHisId, Function.identity(), (k1, k2) -> k1));
        buPrescriptionItemTaskList.forEach(item -> {
            BuPrescription buPrescription = hisIdMap.get(item.getPrescriptionId());
            if(Objects.isNull(buPrescription))
                return;

            item.setDiseaseId(buPrescription.getDiseaseId());
        });

        //10：保存拆分的任务数据
        buPrescriptionItemTaskService.saveBatch(buPrescriptionItemTaskList);

        //11:设置对应的处治小项目的拆分状态为已拆分
        buPrescriptionItemList.forEach(item -> {
            item.setIsSplit(1);
        });
        buPrescriptionItemService.updateBatchById(buPrescriptionItemList);
    }
}






































