package com.maizhiyu.yzt.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.ro.ItemTaskRo;
import com.maizhiyu.yzt.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@Slf4j
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

    @Scheduled(cron = "0 0/2 * * * ?")
    private void splitTask() {
        Date startDate = null;
        Date endDate = new Date();

        BuDealTaskRecord buDealTaskRecord = buDealTaskRecordService.selectLastDealTime();

        if (Objects.nonNull(buDealTaskRecord)) {
            startDate = buDealTaskRecord.getDealTime();
        }
        //新增处治小项目的id列表
        List<Long> insertItemIdList = new ArrayList<>();
        //更新的处治小项目的id列表
        List<Long> updateItemIdList = new ArrayList<>();


        //1:查询这个时间内有更改的处治小项目数据
        List<BuPrescriptionItem> buPrescriptionItemList = buPrescriptionItemService.selectHasUpdateData(startDate, endDate);

        if(CollectionUtils.isEmpty(buPrescriptionItemList))
            return;

        //查找还未拆分的处治小项目的数据集合
        List<BuPrescriptionItem> insertBuPrescriptionItemList = buPrescriptionItemList.stream().filter(item -> item.getIsSplit() == 0).collect(Collectors.toList());

        //查询出已经拆分了的但是经过了修改的数据，这里的修改只能是作废，不允许增加几次，减少几次之类的
        List<BuPrescriptionItem> cancelBuPrescriptionItemList = buPrescriptionItemList.stream().filter(item -> (item.getIsSplit() == 0 && item.getIsCancel() == 1)).collect(Collectors.toList());

        //3：处理新增的处治数据
        if (!CollectionUtils.isEmpty(insertBuPrescriptionItemList)) {
            dealInsertBuPrescriptionItemData(insertBuPrescriptionItemList);
        }

        //4：处理要作废的处治数据
        if (!CollectionUtils.isEmpty(cancelBuPrescriptionItemList)) {
            dealCancelBuPrescriptionItemData(cancelBuPrescriptionItemList);
        }
    }

    private void dealCancelBuPrescriptionItemData(List<BuPrescriptionItem> buPrescriptionItemList) {

        //1:查询出处治小项目对应的预约汇总数据
        List<Long> prescriptionItemIdList = buPrescriptionItemList.stream().map(BuPrescriptionItem::getId).collect(Collectors.toList());
        List<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentList = buPrescriptionItemAppointmentService.selectByItemIdList(prescriptionItemIdList);

        //2:查询出对应的处治小项目下的不是治疗中或不是已治疗的任务数据都改成已作废
        List<Long> prescriptionItemAppointmentIdList = buPrescriptionItemAppointmentList.stream().map(BuPrescriptionItemAppointment::getId).collect(Collectors.toList());
        buPrescriptionItemTaskService.updateNeedCancelTaskByItemAppointmentIdList(prescriptionItemAppointmentIdList);

        //3：将对应的处治小项目的预约汇总数据改成已作废
        buPrescriptionItemAppointmentList.forEach(item -> {
            item.setIsCancel(1);
            item.setUpdateTime(new Date());
        });
        buPrescriptionItemAppointmentService.saveOrUpdateBatch(buPrescriptionItemAppointmentList);

    }

    private void dealInsertBuPrescriptionItemData(List<BuPrescriptionItem> buPrescriptionItemList) {
        List<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentList = new ArrayList<>();

        //1：创建处治小项目的预约情况汇总数据
        for (BuPrescriptionItem item : buPrescriptionItemList) {
            BuPrescriptionItemAppointment buPrescriptionItemAppointment = new BuPrescriptionItemAppointment();
            buPrescriptionItemAppointment.setPrescriptionItemId(item.getId())
                    .setCustomerId(item.getCustomerId())
                    .setPatientId(item.getPatientId())
                    .setOutpatientId(item.getOutpatientId())
                    .setEntityId(item.getEntityId())
                    .setPrescriptionId(item.getPrescriptionId())
                    .setPrescriptionItemId(item.getId())
                    .setState(1)
                    .setQuantity(item.getQuantity().intValue())
                    .setTreatmentQuantity(0)
                    .setAppointmentQuantity(0)
                    .setSurplusQuantity(buPrescriptionItemAppointment.getQuantity())
                    .setCreateTime(new Date())
                    .setUpdateTime(buPrescriptionItemAppointment.getCreateTime());
            buPrescriptionItemAppointmentList.add(buPrescriptionItemAppointment);
        }

        //2：查询出所对应的处治数据
        List<Long> prescriptionIdList = buPrescriptionItemAppointmentList.stream().map(BuPrescriptionItemAppointment::getPrescriptionId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        List<BuPrescription> buPrescriptionList = buPrescriptionService.selectByIdList(prescriptionIdList);

        //3：创建处治预约情况汇总数据
        List<BuOutpatientAppointment> buOutpatientAppointmentList = new ArrayList<>();
        for (BuPrescription buPrescription : buPrescriptionList) {
            BuOutpatientAppointment buOutpatientAppointment = new BuOutpatientAppointment();
            buOutpatientAppointment.setCustomerId(buPrescription.getCustomerId());
            buOutpatientAppointment.setPatientId(buPrescription.getPatientId());
            buOutpatientAppointment.setOutpatientId(buPrescription.getOutpatientId());
            buOutpatientAppointment.setDepartmentId(buPrescription.getDepartmentId());
            buOutpatientAppointment.setDiagnoseId(buPrescription.getDiagnoseId());
            buOutpatientAppointment.setPrescriptionId(buPrescription.getId());
            //门诊时间这里取的是处治的开具时间
            buOutpatientAppointment.setOutpatientTime(buPrescription.getCreateTime());
            buOutpatientAppointment.setCreateTime(new Date());
            buOutpatientAppointment.setUpdateTime(new Date());
            buOutpatientAppointmentList.add(buOutpatientAppointment);
        }

        //4：保存处治预约汇总数据
        buOutpatientAppointmentService.saveBatch(buOutpatientAppointmentList,buOutpatientAppointmentList.size());

        //5：给出治小项目对应的预约数据加上总的处治预约对应数据的id
        Map<Long, BuOutpatientAppointment> prescriptionIdMap = buOutpatientAppointmentList.stream().collect(Collectors.toMap(BuOutpatientAppointment::getPrescriptionId, Function.identity(), (k1, k2) -> k1));
        buPrescriptionItemAppointmentList.forEach(item -> {
            BuOutpatientAppointment buOutpatientAppointment = prescriptionIdMap.get(item.getPrescriptionId());
            if(Objects.isNull(buOutpatientAppointment))
                return;

            item.setOutpatientAppointmentId(buOutpatientAppointment.getId());
        });

        //6：保存处治小项目预约数据
        buPrescriptionItemAppointmentService.saveBatch(buPrescriptionItemAppointmentList, buPrescriptionItemAppointmentList.size());

        //7：根据处治小项目数据拆分任务
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
                buPrescriptionItemTask.setDiagnoseId(buPrescriptionItemAppointment.getDiagnoseId());
                buPrescriptionItemTask.setPrescriptionId(buPrescriptionItemAppointment.getPrescriptionId());
                buPrescriptionItemTask.setPrescriptionItemId(buPrescriptionItemAppointment.getId());
                buPrescriptionItemTask.setEntityId(buPrescriptionItemAppointment.getEntityId());
                buPrescriptionItemTask.setTsName(buPrescriptionItemAppointment.getTsName());
                buPrescriptionItemTask.setCreateTime(new Date());
                buPrescriptionItemTask.setUpdateTime(buPrescriptionItemTask.getCreateTime());
                buPrescriptionItemTaskList.add(buPrescriptionItemTask);
            }
            //8：保存拆分的任务数据
            buPrescriptionItemTaskService.saveBatch(buPrescriptionItemTaskList);
        }
    }
}






































