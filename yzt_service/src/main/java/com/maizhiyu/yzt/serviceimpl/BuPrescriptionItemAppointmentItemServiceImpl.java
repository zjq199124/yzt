package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointment;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.mapper.BuOutpatientAppointmentMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemAppointmentItemMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemAppointmentMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemTaskMapper;
import com.maizhiyu.yzt.ro.AppointmentRo;
import com.maizhiyu.yzt.ro.BuPrescriptionItemTaskRo;
import com.maizhiyu.yzt.ro.BuPrescriptionItemAppointmentRo;
import com.maizhiyu.yzt.service.BuPrescriptionItemTaskService;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class BuPrescriptionItemAppointmentItemServiceImpl extends ServiceImpl<BuPrescriptionItemAppointmentItemMapper, BuPrescriptionItemAppointmentItem> implements IBuPrescriptionItemAppointmentItemService {

    @Resource
    private BuPrescriptionItemAppointmentItemMapper buPrescriptionItemAppointmentItemMapper;

    @Resource
    private BuPrescriptionItemAppointmentMapper buPrescriptionItemAppointmentMapper;

    @Resource
    private BuOutpatientAppointmentMapper buOutpatientAppointmentMapper;

    @Resource
    private BuPrescriptionItemTaskMapper buPrescriptionItemTaskMapper;

    @Resource
    private BuPrescriptionItemTaskService buPrescriptionItemTaskService;

    @Override
    public Boolean makeAppointment(BuPrescriptionItemAppointmentItem buPrescriptionItemAppointmentItem) {
        int insert = 0;
        int update = 0;
        int result = 0;
        //先查询是否在该时段已经预约了
        LambdaQueryWrapper<BuPrescriptionItemAppointmentItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuPrescriptionItemAppointmentItem::getIsDel, 0)
                .eq(BuPrescriptionItemAppointmentItem::getAppointmentDate, buPrescriptionItemAppointmentItem.getAppointmentDate())
                .eq(BuPrescriptionItemAppointmentItem::getTimeSlot, buPrescriptionItemAppointmentItem.getTimeSlot())
                .eq(BuPrescriptionItemAppointmentItem::getCustomerId, buPrescriptionItemAppointmentItem.getCustomerId())
                .eq(BuPrescriptionItemAppointmentItem::getPatientId, buPrescriptionItemAppointmentItem.getPatientId())
                .eq(BuPrescriptionItemAppointmentItem::getOutpatientId, buPrescriptionItemAppointmentItem.getOutpatientId())
                .last("limit 1");
        BuPrescriptionItemAppointmentItem select = buPrescriptionItemAppointmentItemMapper.selectOne(queryWrapper);

        Preconditions.checkArgument(Objects.isNull(select), "该患者在该时段已经有预约不能重复预约!");

        buPrescriptionItemAppointmentItem.setState(1);
        buPrescriptionItemAppointmentItem.setCreateTime(new Date());
        buPrescriptionItemAppointmentItem.setUpdateTime(buPrescriptionItemAppointmentItem.getCreateTime());
        if (Objects.nonNull(buPrescriptionItemAppointmentItem.getId())) {
            insert = buPrescriptionItemAppointmentItemMapper.insert(buPrescriptionItemAppointmentItem);

            if (insert > 0) {
                //预约成功，要增加该适宜技术已预约的次数，扣减剩余预约的次数
                LambdaQueryWrapper<BuPrescriptionItemAppointment> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(BuPrescriptionItemAppointment::getIsDel, 0)
                        .eq(BuPrescriptionItemAppointment::getId, buPrescriptionItemAppointmentItem.getPrescriptionItemAppointmentId())
                        .orderByDesc(BuPrescriptionItemAppointment::getCreateTime)
                        .last("limit 1");
                BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectOne(wrapper);
                Preconditions.checkArgument(Objects.nonNull(buPrescriptionItemAppointment), "当前所开适宜技术的预约信息错误!");

                buPrescriptionItemAppointment.setSurplusQuantity(buPrescriptionItemAppointment.getSurplusQuantity() - 1);
                buPrescriptionItemAppointment.setAppointmentQuantity(buPrescriptionItemAppointment.getAppointmentQuantity() + 1);

                if (buPrescriptionItemAppointment.getSurplusQuantity() == 0) {
                    buPrescriptionItemAppointment.setState(3);
                } else {
                    buPrescriptionItemAppointment.setState(2);
                }
                buPrescriptionItemAppointment.setUpdateTime(new Date());
                update = buPrescriptionItemAppointmentMapper.updateById(buPrescriptionItemAppointment);
                if (update > 0) {
                    //更新整个诊断下的预约的状态
                    LambdaQueryWrapper<BuOutpatientAppointment> query = new LambdaQueryWrapper<>();
                    query.eq(BuOutpatientAppointment::getIsDel, 0)
                            .eq(BuOutpatientAppointment::getCustomerId, buPrescriptionItemAppointment.getCustomerId())
                            .eq(BuOutpatientAppointment::getPatientId, buPrescriptionItemAppointment.getPatientId())
                            .eq(BuOutpatientAppointment::getId, buPrescriptionItemAppointment.getOutpatientAppointmentId())
                            .orderByDesc(BuOutpatientAppointment::getCreateTime)
                            .last("limit 1");
                    BuOutpatientAppointment buOutpatientAppointment = buOutpatientAppointmentMapper.selectOne(query);
                    Preconditions.checkArgument(Objects.nonNull(buOutpatientAppointment), "当前门诊下的预约数据错误!");

                    if (buOutpatientAppointment.getState() == 1) {//未预约
                        //预约中
                        buOutpatientAppointment.setState(2);
                    } else if (buOutpatientAppointment.getState() == 2) {//预约中
                        //查询该诊断下的适宜技术的预约数据是否还有不是预约完成状态的
                        LambdaQueryWrapper<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentQueryWrapper = new LambdaQueryWrapper<>();
                        buPrescriptionItemAppointmentQueryWrapper.eq(BuPrescriptionItemAppointment::getIsDel, 0)
                                .eq(BuPrescriptionItemAppointment::getOutpatientAppointmentId, buOutpatientAppointment.getId())
                                .eq(BuPrescriptionItemAppointment::getCustomerId, buPrescriptionItemAppointment.getCustomerId())
                                .eq(BuPrescriptionItemAppointment::getPatientId, buPrescriptionItemAppointment.getPatientId())
                                .eq(BuPrescriptionItemAppointment::getOutpatientId, buPrescriptionItemAppointment.getOutpatientId())
                                .ne(BuPrescriptionItemAppointment::getState, 3);

                        List<BuPrescriptionItemAppointment> list = buPrescriptionItemAppointmentMapper.selectList(buPrescriptionItemAppointmentQueryWrapper);
                        if (CollectionUtils.isEmpty(list)) {
                            //没有查到不是预约完成状态的子项
                            buOutpatientAppointment.setState(3);
                        } else {
                            buOutpatientAppointment.setState(2);
                        }
                    }
                    result = buOutpatientAppointmentMapper.updateById(buOutpatientAppointment);
                }
            }
            return insert > 0 && update > 0 && result > 0;
        } else {
            buPrescriptionItemAppointmentItem.setUpdateTime(new Date());
            int res = buPrescriptionItemAppointmentItemMapper.updateById(buPrescriptionItemAppointmentItem);
            return res > 0;
        }
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
    public Boolean appointment(AppointmentRo appointmentRo) {
        //取出现有要保存或编辑的预约数据的id
        List<Long> idList = appointmentRo.getBuPrescriptionItemTaskRoList().stream().filter(item -> Objects.nonNull(item.getId())).map(BuPrescriptionItemTaskRo::getId).collect(Collectors.toList());

        //查询出之前在，这次没有的id，表明这些预约数据要删除
        if (!CollectionUtils.isEmpty(appointmentRo.getPreTaskIdList())) {
            List<Long> deleteIdList = appointmentRo.getPreTaskIdList().stream().filter(item -> !idList.contains(item)).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(deleteIdList)) {
                deleteIdList.forEach(item -> {
                    deleteAppointment(item);
                });
            }
        }
        //查询出id为null的数据出来这表示是新预约的数据
        List<BuPrescriptionItemTask> buPrescriptionItemTaskList = new ArrayList<>();
        for (BuPrescriptionItemTaskRo buPrescriptionItemTaskRo : appointmentRo.getBuPrescriptionItemTaskRoList()) {
            if(Objects.nonNull(buPrescriptionItemTaskRo.getId()))
                continue;

            LambdaQueryWrapper<BuPrescriptionItemTask> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BuPrescriptionItemTask::getPatientId, buPrescriptionItemTaskRo.getPatientId());
            queryWrapper.eq(BuPrescriptionItemTask::getOutpatientId, buPrescriptionItemTaskRo.getOutpatientId());
            queryWrapper.eq(BuPrescriptionItemTask::getOutpatientAppointmentId, buPrescriptionItemTaskRo.getOutpatientAppointmentId());
            queryWrapper.eq(BuPrescriptionItemTask::getPrescriptionItemAppointmentId, buPrescriptionItemTaskRo.getPrescriptionItemAppointmentId());
            queryWrapper.eq(BuPrescriptionItemTask::getEntityId, buPrescriptionItemTaskRo.getEntityId());
            queryWrapper.eq(BuPrescriptionItemTask::getCustomerId, buPrescriptionItemTaskRo.getCustomerId());
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
                buPrescriptionItemTask.setWeekDay(buPrescriptionItemTaskRo.getWeekday());
                buPrescriptionItemTask.setUpdateTime(new Date());
                buPrescriptionItemTaskList.add(buPrescriptionItemTask);
            }
        }

        if (!CollectionUtils.isEmpty(buPrescriptionItemTaskList)) {
            //批量预约数据，就是修改设置任务上的预约时间
            boolean res = buPrescriptionItemTaskService.saveBatch(buPrescriptionItemTaskList);
            return res;
        }
        return true;
    }

    @Override
    public List<BuPrescriptionItemAppointmentItem> selectRemindLatestAppointmentList(Date startDate, Date endDate) {
        List<BuPrescriptionItemAppointmentItem> list = buPrescriptionItemAppointmentItemMapper.selectRemindLatestAppointmentList(startDate, endDate);
        return list;
    }

    @Override
    public List<BuPrescriptionItemAppointmentItem> selectTreatmentRemindList(Date startDate, Date endDate) {
        List<BuPrescriptionItemAppointmentItem> list = buPrescriptionItemAppointmentItemMapper.selectTreatmentRemindList(startDate, endDate);
        return list;
    }

    @Override
    public Page<BuPrescriptionItemAppointment> listPrescriptionItemAppointment(BuPrescriptionItemAppointmentRo buPrescriptionItemAppointmentRo) {
        Page page = new Page(buPrescriptionItemAppointmentRo.getCurrentPage(), buPrescriptionItemAppointmentRo.getPageSize());

        LambdaQueryWrapper<BuPrescriptionItemAppointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BuPrescriptionItemAppointment::getPatientId, buPrescriptionItemAppointmentRo.getPatientIdList())
                .eq(BuPrescriptionItemAppointment::getIsDel, 0)
                .ne(BuPrescriptionItemAppointment::getState, 3);
        Page resultPage = buPrescriptionItemAppointmentMapper.selectPage(page, queryWrapper);
        return resultPage;
    }


}
