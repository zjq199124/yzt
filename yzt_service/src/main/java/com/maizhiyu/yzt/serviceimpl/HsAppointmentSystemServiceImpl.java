package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.entity.HsAppointmentSystem;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemTaskMapper;
import com.maizhiyu.yzt.vo.TimeSlotDetailVo;
import com.maizhiyu.yzt.vo.TimeSlotInfoVo;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemAppointmentItemMapper;
import com.maizhiyu.yzt.mapper.HsAppointmentSystemMapper;
import com.maizhiyu.yzt.service.IHsAppointmentSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class HsAppointmentSystemServiceImpl extends ServiceImpl<HsAppointmentSystemMapper, HsAppointmentSystem> implements IHsAppointmentSystemService {

    @Resource
    private HsAppointmentSystemMapper hsAppointmentSystemMapper;

    @Resource
    private BuPrescriptionItemAppointmentItemMapper buPrescriptionItemAppointmentItemMapper;

    @Resource
    private BuPrescriptionItemTaskMapper buPrescriptionItemTaskMapper;

    @Override
    public Boolean add(HsAppointmentSystem hsAppointmentSystem) {
        int result = 0;
        hsAppointmentSystem.setCreateTime(new Date());
        hsAppointmentSystem.setUpdateTime(hsAppointmentSystem.getCreateTime());
        //1:查询最近正在使用的预约时段数据（只能预约明天开始的数据，所以查询时间要用明天的时间）
        HsAppointmentSystem nowHsAppointSystem = hsAppointmentSystemMapper.selectNow(hsAppointmentSystem.getCustomerId(),DateUtil.beginOfDay(DateUtil.offset(new Date(),DateField.DAY_OF_MONTH,1)));

        if (Objects.isNull(nowHsAppointSystem)) {
            //2:之前没有设置系统预约时段数据，当前是第一条，生效时间从明天开始算起
            hsAppointmentSystem.setEffectTime(DateUtil.beginOfDay(DateUtil.offset(new Date(),DateField.DAY_OF_MONTH,1)));
            result = hsAppointmentSystemMapper.insert(hsAppointmentSystem);
        } else {

            //设置生效时间,生效时间是明天之后的第7天
            DateTime effectiveTime = DateUtil.beginOfDay(DateUtil.offset(DateUtil.offset(new Date(),DateField.DAY_OF_MONTH,1), DateField.DAY_OF_MONTH, 7));

            //3:之前已经配置了预约数据，现在新设置的开始时间是明天算起的7天后
            //3.1:查询新的开始时间这个时刻是否已经有数据了
            HsAppointmentSystem tobeEffectiveHsAppointSystem = hsAppointmentSystemMapper.selectTobeEffective(hsAppointmentSystem.getCustomerId(),effectiveTime);

            if (Objects.nonNull(tobeEffectiveHsAppointSystem)) {
                tobeEffectiveHsAppointSystem.setHasHolidays(hsAppointmentSystem.getHasHolidays());
                tobeEffectiveHsAppointSystem.setTimeSlotInfo(hsAppointmentSystem.getTimeSlotInfo());
                tobeEffectiveHsAppointSystem.setEffectTime(effectiveTime);
                tobeEffectiveHsAppointSystem.setUpdateTime(new Date());
                result = hsAppointmentSystemMapper.updateById(tobeEffectiveHsAppointSystem);
            } else {
                //3.2这个生效时间之前还没有进行设置，新增一条数据
                hsAppointmentSystem.setEffectTime(effectiveTime);
                hsAppointmentSystem.setCreateTime(new Date());
                hsAppointmentSystem.setUpdateTime(hsAppointmentSystem.getCreateTime());
                result = hsAppointmentSystemMapper.insert(hsAppointmentSystem);
            }

            //4：更新正在使用的预约时段配置
            //4.1：设置当前使用的时段设置的结束时间
            DateTime endTime = DateUtil.offset(effectiveTime, DateField.SECOND, -1);

            nowHsAppointSystem.setEndTime(endTime);
            nowHsAppointSystem.setUpdateTime(new Date());
            result = hsAppointmentSystemMapper.updateById(nowHsAppointSystem);
        }
        return result > 0;
    }

    @Override
    public HsAppointmentSystem getNowTimeSlot(Long customerId) {
        //查询最近正在使用的预约时段数据（只能预约明天开始的数据，所以查询时间要用明天的时间）
        HsAppointmentSystem hsAppointmentSystem = hsAppointmentSystemMapper.selectNow(customerId, DateUtil.beginOfDay(DateUtil.offset(new Date(),DateField.DAY_OF_MONTH,1)));
        return hsAppointmentSystem;
    }

    @Override
    public HsAppointmentSystem getLastNowTimeSlot(Long customerId) {
        LambdaQueryWrapper<HsAppointmentSystem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HsAppointmentSystem::getIsDel, 0)
                .eq(HsAppointmentSystem::getCustomerId, customerId)
                .orderByDesc(HsAppointmentSystem::getEffectTime)
                .last("limit 1");
        return hsAppointmentSystemMapper.selectOne(queryWrapper);
    }

    @Override
    public List<TimeSlotDetailVo> getTimeSlotByDate(Long customerId, Long outpatientAppointmentId, Date date) {
        List<TimeSlotDetailVo> morningTimeSlotDetailVoList = Collections.emptyList();
        List<TimeSlotDetailVo> afternoonTimeSlotDetailVoList = Collections.emptyList();
        //根据日期和客户id查询当前的时段设置数据
        HsAppointmentSystem hsAppointmentSystem = hsAppointmentSystemMapper.selectNow(customerId, date);
        if(Objects.isNull(hsAppointmentSystem))
            return null;

        try {
            TimeSlotInfoVo timeSlotInfoVo = JSONObject.parseObject(hsAppointmentSystem.getTimeSlotInfo(), TimeSlotInfoVo.class);
            //查询上午的时段列表中，用户的预约状态和该时段的约满状态
            if (!CollectionUtils.isEmpty(timeSlotInfoVo.getMorningTimeSlotList())) {
                morningTimeSlotDetailVoList = timeSlotInfoVo.getMorningTimeSlotList().stream().map(item -> {
                    TimeSlotDetailVo timeSlotDetailVo = new TimeSlotDetailVo();
                    timeSlotDetailVo.setTimeSlot(item);
                    timeSlotDetailVo.setHasAppointment(0);
                    timeSlotDetailVo.setOverLimit(0);
                    return timeSlotDetailVo;
                }).collect(Collectors.toList());
                fillInfo(customerId, outpatientAppointmentId, date, morningTimeSlotDetailVoList, timeSlotInfoVo.getMorningTimeSlotList(), timeSlotInfoVo.getMorningTimeSlotQuantity());
            }

            //查询下午的时段列表中，用户的预约状态和该时段的约满状态
            if (!CollectionUtils.isEmpty(timeSlotInfoVo.getAfternoonTimeSlotList())) {
                afternoonTimeSlotDetailVoList = timeSlotInfoVo.getAfternoonTimeSlotList().stream().map(item -> {
                    TimeSlotDetailVo timeSlotDetailVo = new TimeSlotDetailVo();
                    timeSlotDetailVo.setTimeSlot(item);
                    timeSlotDetailVo.setHasAppointment(0);
                    timeSlotDetailVo.setOverLimit(0);
                    return timeSlotDetailVo;
                }).collect(Collectors.toList());
                fillInfo(customerId, outpatientAppointmentId, date, afternoonTimeSlotDetailVoList, timeSlotInfoVo.getAfternoonTimeSlotList(), timeSlotInfoVo.getAfternoonTimeSlotQuantity());
            }

        } catch (Exception e) {
            log.warn("获取预约时段设置信息失败：" + e.getMessage());
        }

        morningTimeSlotDetailVoList.addAll(afternoonTimeSlotDetailVoList);
        List<TimeSlotDetailVo> resultList = morningTimeSlotDetailVoList.stream().sorted(Comparator.comparing(TimeSlotDetailVo::getTimeSlot)).collect(Collectors.toList());

        return resultList;
    }

    private void fillInfo(Long customerId, Long outpatientAppointmentId, Date date, List<TimeSlotDetailVo> timeSlotDetailVoList, List<String> timeSlotStringList, Integer timeSlotQuantity) {
        LambdaQueryWrapper<BuPrescriptionItemTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuPrescriptionItemTask::getCustomerId, customerId)
                .eq(BuPrescriptionItemTask::getOutpatientAppointmentId, outpatientAppointmentId)
                .eq(BuPrescriptionItemTask::getAppointmentDate, DateUtil.beginOfDay(date))
                .eq(BuPrescriptionItemTask::getIsDel, 0)
                .in(BuPrescriptionItemTask::getTimeSlot, timeSlotStringList);

        List<BuPrescriptionItemTask> buPrescriptionItemTaskList = buPrescriptionItemTaskMapper.selectList(queryWrapper);

        //根据时段标记出当前患者是否在这个时段有预约
        if (!CollectionUtils.isEmpty(buPrescriptionItemTaskList)) {
            Map<String, BuPrescriptionItemTask> timeSlotMap = buPrescriptionItemTaskList.stream().collect(Collectors.toMap(BuPrescriptionItemTask::getTimeSlot, Function.identity(), (k1, k2) -> k1));
            timeSlotDetailVoList.forEach(item -> {
                BuPrescriptionItemTask buPrescriptionItemTask = timeSlotMap.get(item.getTimeSlot());
                int hasAppointment = Objects.nonNull(buPrescriptionItemTask) ? 1 : 0;
                item.setHasAppointment(hasAppointment);
                item.setBuPrescriptionTaskId(buPrescriptionItemTask.getId());
            });
        }

        //根据时段标记出当前时段预约的数量是否达到上限
        LambdaQueryWrapper<BuPrescriptionItemTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BuPrescriptionItemTask::getCustomerId, customerId)
                .eq(BuPrescriptionItemTask::getAppointmentDate, DateUtil.beginOfDay(date))
                .eq(BuPrescriptionItemTask::getIsDel, 0)
                .in(BuPrescriptionItemTask::getTimeSlot, timeSlotStringList);

        List<BuPrescriptionItemTask> customerBuPrescriptionItemTaskList = buPrescriptionItemTaskMapper.selectList(queryWrapper);

        if (!CollectionUtils.isEmpty(customerBuPrescriptionItemTaskList)) {
            Map<String, List<BuPrescriptionItemTask>> customerTimeSlotMap = customerBuPrescriptionItemTaskList.stream().collect(Collectors.groupingBy(BuPrescriptionItemTask::getTimeSlot));

            timeSlotDetailVoList.forEach(item -> {
                List<BuPrescriptionItemTask> list = customerTimeSlotMap.get(item.getTimeSlot());
                if (CollectionUtils.isEmpty(list)) {
                    item.setOverLimit(0);
                } else {
                    int overLimit = list.size() - timeSlotQuantity >= 0 ? 1 : 0;
                    item.setOverLimit(overLimit);
                }
            });
        }
    }
}
