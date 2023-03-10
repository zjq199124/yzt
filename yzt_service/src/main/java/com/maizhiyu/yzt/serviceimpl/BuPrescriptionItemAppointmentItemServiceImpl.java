package com.maizhiyu.yzt.serviceimpl;

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
import com.maizhiyu.yzt.ro.BuPrescriptionItemAppointmentRo;
import com.maizhiyu.yzt.ro.BuPrescriptionItemTaskRo;
import com.maizhiyu.yzt.service.BuPrescriptionItemTaskService;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
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
        //??????????????????????????????????????????
        LambdaQueryWrapper<BuPrescriptionItemAppointmentItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuPrescriptionItemAppointmentItem::getIsDel, 0)
                .eq(BuPrescriptionItemAppointmentItem::getAppointmentDate, buPrescriptionItemAppointmentItem.getAppointmentDate())
                .eq(BuPrescriptionItemAppointmentItem::getTimeSlot, buPrescriptionItemAppointmentItem.getTimeSlot())
                .eq(BuPrescriptionItemAppointmentItem::getCustomerId, buPrescriptionItemAppointmentItem.getCustomerId())
                .eq(BuPrescriptionItemAppointmentItem::getPatientId, buPrescriptionItemAppointmentItem.getPatientId())
                .eq(BuPrescriptionItemAppointmentItem::getOutpatientId, buPrescriptionItemAppointmentItem.getOutpatientId())
                .last("limit 1");
        BuPrescriptionItemAppointmentItem select = buPrescriptionItemAppointmentItemMapper.selectOne(queryWrapper);

        Preconditions.checkArgument(Objects.isNull(select), "??????????????????????????????????????????????????????!");

        buPrescriptionItemAppointmentItem.setState(1);
        buPrescriptionItemAppointmentItem.setCreateTime(new Date());
        buPrescriptionItemAppointmentItem.setUpdateTime(buPrescriptionItemAppointmentItem.getCreateTime());
        if (Objects.nonNull(buPrescriptionItemAppointmentItem.getId())) {
            insert = buPrescriptionItemAppointmentItemMapper.insert(buPrescriptionItemAppointmentItem);

            if (insert > 0) {
                //???????????????????????????????????????????????????????????????????????????????????????
                LambdaQueryWrapper<BuPrescriptionItemAppointment> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(BuPrescriptionItemAppointment::getIsDel, 0)
                        .eq(BuPrescriptionItemAppointment::getId, buPrescriptionItemAppointmentItem.getPrescriptionItemAppointmentId())
                        .orderByDesc(BuPrescriptionItemAppointment::getCreateTime)
                        .last("limit 1");
                BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectOne(wrapper);
                Preconditions.checkArgument(Objects.nonNull(buPrescriptionItemAppointment), "?????????????????????????????????????????????!");

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
                    //???????????????????????????????????????
                    LambdaQueryWrapper<BuOutpatientAppointment> query = new LambdaQueryWrapper<>();
                    query.eq(BuOutpatientAppointment::getIsDel, 0)
                            .eq(BuOutpatientAppointment::getCustomerId, buPrescriptionItemAppointment.getCustomerId())
                            .eq(BuOutpatientAppointment::getPatientId, buPrescriptionItemAppointment.getPatientId())
                            .eq(BuOutpatientAppointment::getId, buPrescriptionItemAppointment.getOutpatientAppointmentId())
                            .orderByDesc(BuOutpatientAppointment::getCreateTime)
                            .last("limit 1");
                    BuOutpatientAppointment buOutpatientAppointment = buOutpatientAppointmentMapper.selectOne(query);
                    Preconditions.checkArgument(Objects.nonNull(buOutpatientAppointment), "????????????????????????????????????!");

                    if (buOutpatientAppointment.getState() == 1) {//?????????
                        //?????????
                        buOutpatientAppointment.setState(2);
                    } else if (buOutpatientAppointment.getState() == 2) {//?????????
                        //???????????????????????????????????????????????????????????????????????????????????????
                        LambdaQueryWrapper<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentQueryWrapper = new LambdaQueryWrapper<>();
                        buPrescriptionItemAppointmentQueryWrapper.eq(BuPrescriptionItemAppointment::getIsDel, 0)
                                .eq(BuPrescriptionItemAppointment::getOutpatientAppointmentId, buOutpatientAppointment.getId())
                                .eq(BuPrescriptionItemAppointment::getCustomerId, buPrescriptionItemAppointment.getCustomerId())
                                .eq(BuPrescriptionItemAppointment::getPatientId, buPrescriptionItemAppointment.getPatientId())
                                .eq(BuPrescriptionItemAppointment::getOutpatientId, buPrescriptionItemAppointment.getOutpatientId())
                                .ne(BuPrescriptionItemAppointment::getState, 3);

                        List<BuPrescriptionItemAppointment> list = buPrescriptionItemAppointmentMapper.selectList(buPrescriptionItemAppointmentQueryWrapper);
                        if (CollectionUtils.isEmpty(list)) {
                            //?????????????????????????????????????????????
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
        Preconditions.checkArgument(Objects.nonNull(buPrescriptionItemTask), "????????????????????????????????????id??????!");

        //???????????????????????????
        buPrescriptionItemTask.setAppointmentStatus(0);
        buPrescriptionItemTask.setAppointmentDate(null);
        buPrescriptionItemTask.setTimeSlot(null);
        buPrescriptionItemTask.setWeekDay(null);
        buPrescriptionItemTask.setUpdateTime(new Date());
        del = buPrescriptionItemTaskMapper.updateById(buPrescriptionItemTask);
        if (del > 0) {
            //???????????????????????????????????????????????????-1????????????????????????+1
            BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectById(buPrescriptionItemTask.getPrescriptionItemAppointmentId());
            Preconditions.checkArgument(Objects.nonNull(buPrescriptionItemAppointment), "????????????????????????????????????????????????!");

            buPrescriptionItemAppointment.setAppointmentQuantity(buPrescriptionItemAppointment.getAppointmentQuantity() - 1);
            buPrescriptionItemAppointment.setSurplusQuantity(buPrescriptionItemAppointment.getSurplusQuantity() + 1);
            if (buPrescriptionItemAppointment.getSurplusQuantity() == buPrescriptionItemAppointment.getQuantity()) {
                //?????????????????????????????????????????????????????????
                buPrescriptionItemAppointment.setState(1);
            } else {
                //???????????????????????????
                buPrescriptionItemAppointment.setState(2);
            }
            buPrescriptionItemAppointment.setUpdateTime(new Date());
            update = buPrescriptionItemAppointmentMapper.updateById(buPrescriptionItemAppointment);
            if (update > 0) {
                //??????????????????????????????????????????????????????
                //????????????????????????????????????????????????????????????????????????????????????
                LambdaQueryWrapper<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentQueryWrapper = new LambdaQueryWrapper<>();
                buPrescriptionItemAppointmentQueryWrapper.eq(BuPrescriptionItemAppointment::getIsDel, 0)
                        .eq(BuPrescriptionItemAppointment::getOutpatientAppointmentId, buPrescriptionItemAppointment.getOutpatientAppointmentId())
                        .eq(BuPrescriptionItemAppointment::getCustomerId, buPrescriptionItemAppointment.getCustomerId())
                        .eq(BuPrescriptionItemAppointment::getPatientId, buPrescriptionItemAppointment.getPatientId())
                        .eq(BuPrescriptionItemAppointment::getOutpatientId, buPrescriptionItemAppointment.getOutpatientId())
                        .ne(BuPrescriptionItemAppointment::getState, 1);//???????????????????????????????????????????????????????????????????????????

                List<BuPrescriptionItemAppointment> list = buPrescriptionItemAppointmentMapper.selectList(buPrescriptionItemAppointmentQueryWrapper);

                LambdaUpdateWrapper<BuOutpatientAppointment> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(BuOutpatientAppointment::getIsDel, 0)
                        .eq(BuOutpatientAppointment::getId, buPrescriptionItemAppointment.getOutpatientAppointmentId());
                if (CollectionUtils.isEmpty(list)) {
                    //??????????????????????????????????????????(???????????????????????????????????????????????????)
                    updateWrapper.set(BuOutpatientAppointment::getState,1);
                } else {
                    //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    updateWrapper.set(BuOutpatientAppointment::getState,2);
                }
                result = buOutpatientAppointmentMapper.update(null,updateWrapper);
            }
        }
        return del > 0 && update > 0 && result > 0;
    }

    @Override
    public Boolean appointment(AppointmentRo appointmentRo) {
        //1:????????????????????????????????????????????????id
        List<Long> idList = appointmentRo.getBuPrescriptionItemTaskRoList().stream().filter(item -> Objects.nonNull(item.getId())).map(BuPrescriptionItemTaskRo::getId).collect(Collectors.toList());

        //2:????????????????????????????????????id????????????????????????????????????
        if (!CollectionUtils.isEmpty(appointmentRo.getPreTaskIdList())) {
            List<Long> deleteIdList = appointmentRo.getPreTaskIdList().stream().filter(item -> !idList.contains(item)).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(deleteIdList)) {
                deleteIdList.forEach(item -> {
                    //3:???????????????????????????
                    deleteAppointment(item);
                });
            }
        }
        //4????????????id???null?????????????????????????????????????????????
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
            //5????????????????????????id???????????????????????????????????????????????????????????????
            Map<Long, List<BuPrescriptionItemTask>> prescriptionItemIdListMap = buPrescriptionItemTaskList.stream().collect(Collectors.groupingBy(BuPrescriptionItemTask::getPrescriptionItemId));
            //6???????????????????????????????????????????????????????????????????????????????????????
            prescriptionItemIdListMap.keySet().forEach(item -> {
                List<BuPrescriptionItemTask> list = prescriptionItemIdListMap.get(item);
                //7:??????????????????
                if (!CollectionUtils.isEmpty(list)) {
                    boolean res = buPrescriptionItemTaskService.saveBatch(buPrescriptionItemTaskList);
                    if (res) {
                        //8??????????????????????????????????????????????????????,?????????????????????
                        LambdaQueryWrapper<BuPrescriptionItemAppointment> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(BuPrescriptionItemAppointment::getPrescriptionItemId, item)
                                .eq(BuPrescriptionItemAppointment::getIsDel, 0)
                                .last("limit 1");

                        BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectOne(queryWrapper);
                        if (Objects.nonNull(buPrescriptionItemAppointment)) {

                            buPrescriptionItemAppointment.setAppointmentQuantity(buPrescriptionItemAppointment.getAppointmentQuantity() + list.size());
                            buPrescriptionItemAppointment.setSurplusQuantity(buPrescriptionItemAppointment.getSurplusQuantity() - list.size());
                            if (buPrescriptionItemAppointment.getSurplusQuantity() == 0) {
                                buPrescriptionItemAppointment.setState(3);
                            } else {
                                buPrescriptionItemAppointment.setState(2);
                            }
                            buPrescriptionItemAppointment.setUpdateTime(new Date());
                            buPrescriptionItemAppointmentMapper.updateById(buPrescriptionItemAppointment);

                            //9???????????????????????????????????????????????????
                            queryWrapper = null;
                            queryWrapper.eq(BuPrescriptionItemAppointment::getOutpatientAppointmentId, buPrescriptionItemAppointment.getOutpatientAppointmentId())
                                    .eq(BuPrescriptionItemAppointment::getIsDel, 0)
                                    .ne(BuPrescriptionItemAppointment::getState, 3);

                            List<BuPrescriptionItemAppointment> prescriptionItemAppointments = buPrescriptionItemAppointmentMapper.selectList(queryWrapper);

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
            });
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
