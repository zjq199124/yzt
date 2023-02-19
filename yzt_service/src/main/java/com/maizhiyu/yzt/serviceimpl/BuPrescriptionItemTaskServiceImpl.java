package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.mapper.BuOutpatientAppointmentMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemAppointmentMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemTaskMapper;
import com.maizhiyu.yzt.ro.*;
import com.maizhiyu.yzt.service.BuPrescriptionItemTaskService;
import com.maizhiyu.yzt.vo.BuPrescriptionItemTaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (BuPrescriptionItemTask)表服务实现类
 *
 * @author makejava
 * @since 2023-02-01 15:16:12
 */
@Slf4j
@Service("buPrescriptionItemTaskService")
@Transactional(rollbackFor = Exception.class)
public class BuPrescriptionItemTaskServiceImpl extends ServiceImpl<BuPrescriptionItemTaskMapper, BuPrescriptionItemTask> implements BuPrescriptionItemTaskService {

    @Resource
    private BuPrescriptionItemTaskMapper buPrescriptionItemTaskMapper;

    @Resource
    private BuPrescriptionItemMapper buPrescriptionItemMapper;

    @Resource
    private BuPrescriptionItemAppointmentMapper buPrescriptionItemAppointmentMapper;

    @Resource
    private BuOutpatientAppointmentMapper buOutpatientAppointmentMapper;

    @Override
    public Page<BuPrescriptionItemTaskVo> selectWaitSignatureList(WaitSignatureRo waitSignatureRo) {
        Page<BuPrescriptionItemTaskVo> page = new Page<>(waitSignatureRo.getCurrentPage(), waitSignatureRo.getPageSize());
        Page<BuPrescriptionItemTaskVo> pageResult = buPrescriptionItemTaskMapper.selectWaitSignatureList(page,waitSignatureRo);
        return pageResult;
    }

    @Override
    public Boolean signature(ItemTaskRo itemTaskRo) {
        int result = 0;
        if (Objects.nonNull(itemTaskRo.getPrescriptionItemTaskId())) {
            //1:传了任务id过来的，直接签到
            LambdaUpdateWrapper<BuPrescriptionItemTask> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(BuPrescriptionItemTask::getId, itemTaskRo.getPrescriptionItemTaskId())
                    .set(BuPrescriptionItemTask::getSignatureStatus, 1)
                    .set(BuPrescriptionItemTask::getSignatureTime, new Date())
                    .set(BuPrescriptionItemTask::getRegistrantId, itemTaskRo.getRegistrantId())
                    .set(BuPrescriptionItemTask::getUpdateTime, new Date());
            result = buPrescriptionItemTaskMapper.update(null, updateWrapper);
        } else {
            //直接新建的签到，要查询一个没有签到的任务，进行签到
            LambdaQueryWrapper<BuPrescriptionItemTask> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BuPrescriptionItemTask::getPrescriptionItemId, itemTaskRo.getPrescriptionItemId())
                    .eq(BuPrescriptionItemTask::getIsDel, 0)
                    .eq(BuPrescriptionItemTask::getSignatureStatus, 0)
                    .last("limit 1");

            BuPrescriptionItemTask buPrescriptionItemTask = buPrescriptionItemTaskMapper.selectOne(queryWrapper);

            if (Objects.nonNull(buPrescriptionItemTask)) {
                buPrescriptionItemTask.setSignatureStatus(1);
                buPrescriptionItemTask.setSignatureTime(new Date());
                buPrescriptionItemTask.setUpdateTime(new Date());

                result = buPrescriptionItemTaskMapper.updateById(buPrescriptionItemTask);
            }
        }
        return result > 0;
    }

    @Override
    public Page<BuPrescriptionItemTaskVo> waitTreatmentList(WaitTreatmentRo waitTreatmentRo) {
        Page<BuPrescriptionItemTaskVo> page = new Page<>(waitTreatmentRo.getCurrentPage(), waitTreatmentRo.getPageSize());
        Page<BuPrescriptionItemTaskVo> pageResult = buPrescriptionItemTaskMapper.selectWaitTreatmentList(page,waitTreatmentRo);
        return pageResult;
    }

    @Override
    public boolean startTreatment(ItemTaskRo itemTaskRo) {
        //1:设置治疗状态为开始治疗
        LambdaUpdateWrapper<BuPrescriptionItemTask> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BuPrescriptionItemTask::getId, itemTaskRo.getPrescriptionItemTaskId())
                .set(BuPrescriptionItemTask::getCureStatus, 1)
                .set(BuPrescriptionItemTask::getCureStartTime, new Date())
                .set(BuPrescriptionItemTask::getCureUserId, itemTaskRo.getCureUserId())
                .set(BuPrescriptionItemTask::getUpdateTime, new Date());

        int update = buPrescriptionItemTaskMapper.update(null, updateWrapper);
        if (update > 0) {
            //2：开始治疗后已预约的次数要减去1
            BuPrescriptionItemTask buPrescriptionItemTask = buPrescriptionItemTaskMapper.selectById(itemTaskRo.getPrescriptionItemTaskId());
            if (Objects.nonNull(buPrescriptionItemTask)) {
                BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectById(buPrescriptionItemTask.getPrescriptionItemAppointmentId());
                if (Objects.nonNull(buPrescriptionItemAppointment)) {
                    buPrescriptionItemAppointment.setAppointmentQuantity(buPrescriptionItemAppointment.getAppointmentQuantity() - 1);
                    buPrescriptionItemAppointment.setUpdateTime(new Date());
                    buPrescriptionItemAppointmentMapper.updateById(buPrescriptionItemAppointment);
                }
            }
        }
        return update > 0;
    }

    @Override
    public boolean endTreatment(Long id) {
        //1：修改治疗状态为已治疗
        LambdaUpdateWrapper<BuPrescriptionItemTask> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BuPrescriptionItemTask::getId, id)
                .set(BuPrescriptionItemTask::getCureStatus, 2)
                .set(BuPrescriptionItemTask::getCureEndTime, new Date())
                .set(BuPrescriptionItemTask::getUpdateTime, new Date());

        int update = buPrescriptionItemTaskMapper.update(null, updateWrapper);

        if (update > 0) {
            //2：处方小项目中的已治疗次数加1
            BuPrescriptionItemTask buPrescriptionItemTask = buPrescriptionItemTaskMapper.selectById(id);
            if (Objects.nonNull(buPrescriptionItemTask)) {
                BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectById(buPrescriptionItemTask.getPrescriptionItemAppointmentId());
                if (Objects.nonNull(buPrescriptionItemAppointment)) {
                    buPrescriptionItemAppointment.setTreatmentQuantity(buPrescriptionItemAppointment.getTreatmentQuantity() + 1);
                    buPrescriptionItemAppointment.setUpdateTime(new Date());
                    buPrescriptionItemAppointmentMapper.updateById(buPrescriptionItemAppointment);
                }
            }
        }
        return update > 0;
    }

    @Override
    public Page<BuPrescriptionItemTaskVo> treatmentList(ItemTaskRo itemTaskRo) {
        Page<BuPrescriptionItemTaskVo> page = new Page<>(itemTaskRo.getCurrentPage(), itemTaskRo.getPageSize());
        Page<BuPrescriptionItemTaskVo> pageResult = buPrescriptionItemTaskMapper.selectTreatmentList(page, itemTaskRo);
        return pageResult;
    }

    @Override
    public BuPrescriptionItemTaskVo treatmentRecordDetail(Long id) {
        BuPrescriptionItemTaskVo buPrescriptionItemTaskVo = buPrescriptionItemTaskMapper.treatmentRecordDetail(id);
        if (Objects.nonNull(buPrescriptionItemTaskVo)) {
            LambdaQueryWrapper<BuPrescriptionItem> query = new LambdaQueryWrapper<>();
            query.eq(BuPrescriptionItem::getPrescriptionId, buPrescriptionItemTaskVo.getPrescriptionId())
                    .eq(BuPrescriptionItem::getIsDel, 0);
            List<BuPrescriptionItem> buPrescriptionItems = buPrescriptionItemMapper.selectList(query);
            buPrescriptionItemTaskVo.setBuPrescriptionItemList(buPrescriptionItems);
        }
        return buPrescriptionItemTaskVo;
    }

    @Override
    public Boolean deleteAppointment(Long buPrescriptionItemTaskId) {
        int del = 0;
        int update = 0;
        int result = 0;
        BuPrescriptionItemTask buPrescriptionItemTask = buPrescriptionItemTaskMapper.selectById(buPrescriptionItemTaskId);
        Preconditions.checkArgument(Objects.nonNull(buPrescriptionItemTask), "处方明细适宜技术具体任务id错误!");

        //清楚对应的预约数据
        buPrescriptionItemTask.setAppointmentStatus(0);
        buPrescriptionItemTask.setAppointmentDate(null);
        buPrescriptionItemTask.setTimeSlot(null);
        buPrescriptionItemTask.setWeekDay(null);
        buPrescriptionItemTask.setUpdateTime(new Date());
        del = buPrescriptionItemTaskMapper.updateById(buPrescriptionItemTask);
        if (del > 0) {
            //对应的适宜技术小项目中的预约数据要-1，剩余预约次数要+1
            BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectById(buPrescriptionItemTask.getPrescriptionItemAppointmentId());
            Preconditions.checkArgument(Objects.nonNull(buPrescriptionItemAppointment), "处方明细适宜技术预约汇总数据错误!");

            buPrescriptionItemAppointment.setAppointmentQuantity(buPrescriptionItemAppointment.getAppointmentQuantity() - 1);
            buPrescriptionItemAppointment.setSurplusQuantity(buPrescriptionItemAppointment.getSurplusQuantity() + 1);
            if (buPrescriptionItemAppointment.getSurplusQuantity() == buPrescriptionItemAppointment.getQuantity()) {
                //剩余预约次数和开单次数相等，改为未预约
                buPrescriptionItemAppointment.setState(1);
            } else {
                //预约状态改成预约中
                buPrescriptionItemAppointment.setState(2);
            }
            buPrescriptionItemAppointment.setUpdateTime(new Date());
            update = buPrescriptionItemAppointmentMapper.updateById(buPrescriptionItemAppointment);
            if (update > 0) {
                //更改所有的门诊下的预约数据的预约状态
                //查询该诊断下的适宜技术的预约数据是否还有不是未预约的数据
                LambdaQueryWrapper<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentQueryWrapper = new LambdaQueryWrapper<>();
                buPrescriptionItemAppointmentQueryWrapper.eq(BuPrescriptionItemAppointment::getIsDel, 0)
                        .eq(BuPrescriptionItemAppointment::getOutpatientAppointmentId, buPrescriptionItemAppointment.getOutpatientAppointmentId())
                        .eq(BuPrescriptionItemAppointment::getCustomerId, buPrescriptionItemAppointment.getCustomerId())
                        .eq(BuPrescriptionItemAppointment::getPatientId, buPrescriptionItemAppointment.getPatientId())
                        .eq(BuPrescriptionItemAppointment::getOutpatientId, buPrescriptionItemAppointment.getOutpatientId())
                        .ne(BuPrescriptionItemAppointment::getState, 1);//不是未预约的数据，就是处于预约中和预约已完成的数据

                List<BuPrescriptionItemAppointment> list = buPrescriptionItemAppointmentMapper.selectList(buPrescriptionItemAppointmentQueryWrapper);

                LambdaUpdateWrapper<BuOutpatientAppointment> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(BuOutpatientAppointment::getIsDel, 0)
                        .eq(BuOutpatientAppointment::getId, buPrescriptionItemAppointment.getOutpatientAppointmentId());
                if (CollectionUtils.isEmpty(list)) {
                    //没有查到不是未预约状态的子项(所以该门诊下的所有技术项目都未预约)
                    updateWrapper.set(BuOutpatientAppointment::getState,1);
                } else {
                    //删除了一个预约数据，所以总的门诊下的预约数据一定有未完成的，所以总的不可能是预约完成，现在只能是预约中了
                    updateWrapper.set(BuOutpatientAppointment::getState,2);
                }
                result = buOutpatientAppointmentMapper.update(null,updateWrapper);
            }
        }
        return del > 0 && update > 0 && result > 0;
    }

    @Override
    public void updateNeedCancelTaskByItemAppointmentIdList(List<Long> prescriptionItemAppointmentIdList) {
        //将不是治疗中和已治疗中的数据设置成作废
        LambdaUpdateWrapper<BuPrescriptionItemTask> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.ne(BuPrescriptionItemTask::getCureStatus, 1)
                .ne(BuPrescriptionItemTask::getCureStatus, 2)
                .in(BuPrescriptionItemTask::getPrescriptionItemAppointmentId, prescriptionItemAppointmentIdList)
                .set(BuPrescriptionItemTask::getIsCancel, 1);

        buPrescriptionItemTaskMapper.update(null, updateWrapper);
    }

    @Override
    public Boolean appointment(AppointmentRo appointmentRo) {
        //1:取出现有要保存或编辑的预约数据的id
        List<Long> idList = appointmentRo.getBuPrescriptionItemTaskRoList().stream().filter(item -> Objects.nonNull(item.getId())).map(BuPrescriptionItemTaskRo::getId).collect(Collectors.toList());

        //2:查询出之前在，这次没有的id，表明这些预约数据要删除
        if (!CollectionUtils.isEmpty(appointmentRo.getPreTaskIdList())) {
            List<Long> deleteIdList = appointmentRo.getPreTaskIdList().stream().filter(item -> !idList.contains(item)).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(deleteIdList)) {
                deleteIdList.forEach(item -> {
                    //3:删除对应的预约数据
                    log.info("/*************删除对应的预约数据*************/");
                    deleteAppointment(item);
                });
            }
        }
        //4：查询出id为null的数据出来这表示是新预约的数据
        for (BuPrescriptionItemTaskRo buPrescriptionItemTaskRo : appointmentRo.getBuPrescriptionItemTaskRoList()) {
            if(Objects.nonNull(buPrescriptionItemTaskRo.getId()))
                continue;

            LambdaQueryWrapper<BuPrescriptionItemTask> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BuPrescriptionItemTask::getPatientId, buPrescriptionItemTaskRo.getPatientId());
            queryWrapper.eq(BuPrescriptionItemTask::getOutpatientId, buPrescriptionItemTaskRo.getOutpatientId());
            queryWrapper.eq(BuPrescriptionItemTask::getOutpatientAppointmentId, buPrescriptionItemTaskRo.getOutpatientAppointmentId());
            queryWrapper.eq(BuPrescriptionItemTask::getPrescriptionItemAppointmentId, buPrescriptionItemTaskRo.getPrescriptionItemAppointmentId());
            queryWrapper.eq(BuPrescriptionItemTask::getEntityId, buPrescriptionItemTaskRo.getEntityId());
            queryWrapper.eq(BuPrescriptionItemTask::getCustomerId, appointmentRo.getCustomerId());
            queryWrapper.eq(BuPrescriptionItemTask::getAppointmentStatus, 0);
            queryWrapper.isNull(BuPrescriptionItemTask::getAppointmentDate);
            queryWrapper.isNull(BuPrescriptionItemTask::getTimeSlot);
            queryWrapper.isNull(BuPrescriptionItemTask::getWeekDay);
            queryWrapper.orderByAsc(BuPrescriptionItemTask::getPrescriptionItemId);
            queryWrapper.last("limit 1");
            BuPrescriptionItemTask buPrescriptionItemTask = buPrescriptionItemTaskMapper.selectOne(queryWrapper);

            if (Objects.nonNull(buPrescriptionItemTask)) {
                buPrescriptionItemTask.setAppointmentDate(buPrescriptionItemTaskRo.getAppointmentDate());
                buPrescriptionItemTask.setAppointmentStatus(1);
                buPrescriptionItemTask.setTimeSlot(buPrescriptionItemTaskRo.getTimeSlot());
                buPrescriptionItemTask.setWeekDay(buPrescriptionItemTaskRo.getWeekday());
                buPrescriptionItemTask.setUpdateTime(new Date());
                //5:添加预约时间
                log.info("/*************保存对应的预约数据*************/");
                int res = buPrescriptionItemTaskMapper.updateById(buPrescriptionItemTask);
                if (res > 0) {
                    //8：修改对应的处置小项目的预约数据汇总,和预约完成状态
                    LambdaQueryWrapper<BuPrescriptionItemAppointment> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(BuPrescriptionItemAppointment::getPrescriptionItemId, buPrescriptionItemTask.getPrescriptionItemId())
                            .eq(BuPrescriptionItemAppointment::getIsDel, 0)
                            .last("limit 1");

                    BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectOne(wrapper);
                    if (Objects.nonNull(buPrescriptionItemAppointment)) {

                        buPrescriptionItemAppointment.setAppointmentQuantity(buPrescriptionItemAppointment.getAppointmentQuantity() + 1);
                        buPrescriptionItemAppointment.setSurplusQuantity(buPrescriptionItemAppointment.getSurplusQuantity() - 1);
                        if (buPrescriptionItemAppointment.getSurplusQuantity() == 0) {
                            buPrescriptionItemAppointment.setState(3);
                        } else {
                            buPrescriptionItemAppointment.setState(2);
                        }
                        buPrescriptionItemAppointment.setUpdateTime(new Date());
                        buPrescriptionItemAppointmentMapper.updateById(buPrescriptionItemAppointment);

                        //9：修改此次门诊对应的总的预约小项目
                        LambdaQueryWrapper<BuPrescriptionItemAppointment> itemAppointmentQueryWrapper = new LambdaQueryWrapper<>();
                        itemAppointmentQueryWrapper.eq(BuPrescriptionItemAppointment::getOutpatientAppointmentId, buPrescriptionItemAppointment.getOutpatientAppointmentId())
                                .eq(BuPrescriptionItemAppointment::getIsDel, 0)
                                .ne(BuPrescriptionItemAppointment::getState, 3);

                        List<BuPrescriptionItemAppointment> prescriptionItemAppointments = buPrescriptionItemAppointmentMapper.selectList(itemAppointmentQueryWrapper);

                        LambdaUpdateWrapper<BuOutpatientAppointment> query = new LambdaUpdateWrapper<>();
                        query.eq(BuOutpatientAppointment::getId, buPrescriptionItemAppointment.getOutpatientAppointmentId());
                        if (CollectionUtils.isEmpty(prescriptionItemAppointments)) {
                            query.set(BuOutpatientAppointment::getState, 3);
                        } else {
                            query.set(BuOutpatientAppointment::getState, 2);
                        }
                        query.set(BuOutpatientAppointment::getUpdateTime, new Date());
                        buOutpatientAppointmentMapper.update(null, query);
                    }
                }
            }
        }
        return true;
    }
}

