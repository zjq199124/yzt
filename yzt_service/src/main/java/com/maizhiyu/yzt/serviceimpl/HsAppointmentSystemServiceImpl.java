package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.sql.visitor.functions.Now;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.HsAppointmentSystem;
import com.maizhiyu.yzt.mapper.HsAppointmentSystemMapper;
import com.maizhiyu.yzt.service.IHsAppointmentSystemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.swing.text.EditorKit;
import java.util.Date;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class HsAppointmentSystemServiceImpl extends ServiceImpl<HsAppointmentSystemMapper, HsAppointmentSystem> implements IHsAppointmentSystemService {

    @Resource
    private HsAppointmentSystemMapper hsAppointmentSystemMapper;

    @Override
    public Boolean add(HsAppointmentSystem hsAppointmentSystem) {
        int result = 0;
        hsAppointmentSystem.setCreateTime(new Date());
        hsAppointmentSystem.setUpdateTime(hsAppointmentSystem.getCreateTime());
        //1:查询最近正在使用的预约时段数据
        HsAppointmentSystem nowHsAppointSystem = hsAppointmentSystemMapper.selectNow(hsAppointmentSystem.getCustomerId(),new Date());

        if (Objects.isNull(nowHsAppointSystem)) {
            //2:之前没有设置系统预约时段数据，当前是第一条，生效时间从今天开始算起
            hsAppointmentSystem.setEffectTime(DateUtil.beginOfDay(new Date()));
            result = hsAppointmentSystemMapper.insert(hsAppointmentSystem);
        } else {

            //设置生效时间
            DateTime effectiveTime = DateUtil.beginOfDay(DateUtil.offset(new Date(), DateField.DAY_OF_MONTH, 7));

            //3:之前已经配置了预约数据，现在新设置的开始时间是7天后
            //3.1:查询是否已经有设置了的但是还未生效的预约数据
            HsAppointmentSystem tobeEffectiveHsAppointSystem = hsAppointmentSystemMapper.selectTobeEffective(hsAppointmentSystem.getCustomerId(),new Date());

            if (Objects.nonNull(tobeEffectiveHsAppointSystem)) {
                tobeEffectiveHsAppointSystem.setHasHolidays(hsAppointmentSystem.getHasHolidays());
                tobeEffectiveHsAppointSystem.setTimeSlotInfo(hsAppointmentSystem.getTimeSlotInfo());
                tobeEffectiveHsAppointSystem.setEffectTime(effectiveTime);
                tobeEffectiveHsAppointSystem.setUpdateTime(new Date());
                result = hsAppointmentSystemMapper.updateById(tobeEffectiveHsAppointSystem);
            } else {
                //3.2之前没有设置还未生效的时段，新增一条数据
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
        HsAppointmentSystem hsAppointmentSystem = hsAppointmentSystemMapper.selectNow(customerId, new Date());
        return hsAppointmentSystem;
    }
}
