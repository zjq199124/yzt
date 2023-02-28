package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointment;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.entity.DiseaseMapping;
import com.maizhiyu.yzt.mapper.BuOutpatientAppointmentMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemAppointmentMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemTaskMapper;
import com.maizhiyu.yzt.mapper.DiseaseMappingMapper;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;
import com.maizhiyu.yzt.service.IBuOutpatientAppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class BuOutpatientAppointmentServiceImpl extends ServiceImpl<BuOutpatientAppointmentMapper,BuOutpatientAppointment> implements IBuOutpatientAppointmentService {

    @Resource
    private BuOutpatientAppointmentMapper buOutpatientAppointmentMapper;

    @Resource
    private BuPrescriptionItemAppointmentMapper buPrescriptionItemAppointmentMapper;

    @Resource
    private BuPrescriptionItemTaskMapper buPrescriptionItemTaskMapper;

    @Resource
    private DiseaseMappingMapper diseaseMappingMapper;

    @Override
    public Page<BuOutpatientAppointment> list(OutpatientAppointmentRo outpatientAppointmentRo) {
        Page<BuOutpatientAppointment> page = new Page<>(outpatientAppointmentRo.getCurrentPage(), outpatientAppointmentRo.getPageSize());
        Page<BuOutpatientAppointment> resultPage = buOutpatientAppointmentMapper.list(page, outpatientAppointmentRo);
        if(CollectionUtils.isEmpty(resultPage.getRecords()))
            return resultPage;

        //如果不走云平台系统的数据此时没有疾病名称，要重新填补疾病名称
        List<Long> hisDiseaseIdList = resultPage.getRecords().stream().filter(item -> Objects.isNull(item.getDisease())).map(BuOutpatientAppointment::getDiseaseId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(hisDiseaseIdList)) {
            LambdaQueryWrapper<DiseaseMapping> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DiseaseMapping::getCustomerId, outpatientAppointmentRo.getCustomerId())
                    .in(DiseaseMapping::getHisCode, hisDiseaseIdList);

            List<DiseaseMapping> diseaseMappings = diseaseMappingMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(diseaseMappings)) {
                Map<String, DiseaseMapping> hisCodeMap = diseaseMappings.stream().collect(Collectors.toMap(DiseaseMapping::getHisCode, Function.identity(), (k1, k2) -> k1));
                resultPage.getRecords().forEach(item -> {
                    if(Objects.nonNull(item.getDisease()))
                        return;

                    DiseaseMapping diseaseMapping = hisCodeMap.get(item.getDiseaseId());
                    if(Objects.isNull(diseaseMapping))
                        return;

                    item.setDisease(diseaseMapping.getName());
                });
            }
        }

        //查询处方下面的的明细技术的预约状况
        List<Long> prescriptionIdList = resultPage.getRecords().stream().map(BuOutpatientAppointment::getPrescriptionId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(prescriptionIdList))
            return resultPage;

        //查询该处方下所开出的适宜技术小项目对应的预约情况
        List<BuPrescriptionItemAppointment> buPrescriptionItemAppointments = buPrescriptionItemAppointmentMapper.selectByPrescriptionIdList(prescriptionIdList);

        if(CollectionUtils.isEmpty(buPrescriptionItemAppointments))
            return resultPage;

        Map<Long, List<BuPrescriptionItemAppointment>> prescriptionIdMap = buPrescriptionItemAppointments.stream().collect(Collectors.groupingBy(BuPrescriptionItemAppointment::getPrescriptionId));

        resultPage.getRecords().forEach(item -> {
            List<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentList = prescriptionIdMap.get(item.getPrescriptionId());
            if(CollectionUtils.isEmpty(buPrescriptionItemAppointmentList))
                return;

            item.setBuPrescriptionItemAppointmentList(buPrescriptionItemAppointmentList);
        });
        return resultPage;
    }

    @Override
    public BuOutpatientAppointment insert(BuOutpatientAppointment buOutpatientAppointment) {
        buOutpatientAppointmentMapper.insert(buOutpatientAppointment);
        return buOutpatientAppointment;
    }

    @Override
    public BuOutpatientAppointment selectByDiagnoseId(Long diagnoseId) {
        LambdaQueryWrapper<BuOutpatientAppointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuOutpatientAppointment::getDiagnoseId, diagnoseId)
                .eq(BuOutpatientAppointment::getIsDel, 0)
                .last("limit 1");
        return buOutpatientAppointmentMapper.selectOne(queryWrapper);
    }

    @Override
    public BuOutpatientAppointment appointmentDetail(Long id) {
        //1：查询一次处方对应的预约数据
        BuOutpatientAppointment buOutpatientAppointment = buOutpatientAppointmentMapper.selectById(id);
        Preconditions.checkArgument(Objects.nonNull(buOutpatientAppointment), "id错误!");

        //2:查询此次处治所开的处治的小项目所对应的预约数据信息
        List<BuPrescriptionItemAppointment> list = buPrescriptionItemAppointmentMapper.selectByOutpatientAppointmentId(buOutpatientAppointment.getId());
        buOutpatientAppointment.setBuPrescriptionItemAppointmentList(list);

        if(CollectionUtils.isEmpty(list))
            return buOutpatientAppointment;

        //3:查询每一个处治小项目的每个任务的预约数据（已预约，未签到）
        list.forEach(item -> {
            LambdaQueryWrapper<BuPrescriptionItemTask> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BuPrescriptionItemTask::getPrescriptionItemAppointmentId, item.getId())
                    .eq(BuPrescriptionItemTask::getAppointmentStatus, 1)
                    .eq(BuPrescriptionItemTask::getSignatureStatus, 0)
                    .eq(BuPrescriptionItemTask::getCureStatus, 0)
                    .eq(BuPrescriptionItemTask::getIsDel, 0)
                    .isNotNull(BuPrescriptionItemTask::getAppointmentDate)
                    .isNotNull(BuPrescriptionItemTask::getTimeSlot);

            List<BuPrescriptionItemTask> buPrescriptionItemTaskList = buPrescriptionItemTaskMapper.selectList(queryWrapper);
            item.setAppointmentTaskList(buPrescriptionItemTaskList);
        });

        //4：查询每一个处治小项目的每个任务的治疗数据
        list.forEach(item -> {
            LambdaQueryWrapper<BuPrescriptionItemTask> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BuPrescriptionItemTask::getPrescriptionItemAppointmentId, item.getId())
                    .eq(BuPrescriptionItemTask::getCureStatus, 2)
                    .eq(BuPrescriptionItemTask::getIsDel, 0)
                    .isNotNull(BuPrescriptionItemTask::getCureStartTime)
                    .isNotNull(BuPrescriptionItemTask::getCureEndTime);

            List<BuPrescriptionItemTask> buPrescriptionItemTaskList = buPrescriptionItemTaskMapper.selectList(queryWrapper);
            item.setCureTaskList(buPrescriptionItemTaskList);
        });
        return buOutpatientAppointment;
    }

    @Override
    public List<BuOutpatientAppointment> selectByIdList(List<Long> outpatientAppointmentIdList) {
        LambdaQueryWrapper<BuOutpatientAppointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BuOutpatientAppointment::getId, outpatientAppointmentIdList);
        List<BuOutpatientAppointment> buOutpatientAppointmentList = buOutpatientAppointmentMapper.selectList(queryWrapper);
        return buOutpatientAppointmentList;
    }
}
