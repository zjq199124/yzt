package com.maizhiyu.yzt.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.BuSchedule;
import com.maizhiyu.yzt.mapper.BuOutpatientMapper;
import com.maizhiyu.yzt.mapper.BuScheduleMapper;
import com.maizhiyu.yzt.service.IBuScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional(rollbackFor = Exception.class)
public class BuScheduleService implements IBuScheduleService {

    @Autowired
    private BuScheduleMapper mapper;

    @Autowired
    private BuOutpatientMapper outpatientMapper;

    @Override
    public Integer addSchedule(BuSchedule schedule) {
        System.out.println(schedule);
        return mapper.insert(schedule);
    }

    @Override
    public Integer delSchedule(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setSchedule(BuSchedule schedule) {
        return mapper.updateById(schedule);
    }

    @Override
    public Map<String, Object> getSchedule(Long id) {
        // 获取排班信息
        BuSchedule schedule = mapper.selectById(id);
        if (schedule == null) {
            return null;
        }

        Map<String, Object> result = (Map<String, Object>) JSON.toJSON(schedule);
        List<Map<String,Object>> list = new ArrayList<>();
        result.put("timeList", list);

        // 获取预约信息
        QueryWrapper<BuOutpatient> wrapper = new QueryWrapper<>();
        wrapper.eq("schedule_id", id);
        List<BuOutpatient> outpatientList = outpatientMapper.selectList(wrapper);

        // 计算排班时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long thisTime = schedule.getStartTime().getTime();
        for (int i = 0; i < schedule.getNumber(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("time", sdf.format(new Date(thisTime)));
            map.put("status", 0);
            list.add(map);
            long nextTime = thisTime + schedule.getInterval() * 60 * 1000;
            for (BuOutpatient outpatient : outpatientList) {
                long time = outpatient.getTime().getTime();
                if (time >= thisTime && time < nextTime) {
                    map.put("status", 1);
                }
            }
            thisTime = nextTime;
        }

        // 返回数据结果
        return result;
    }

    @Override
    public List<Map<String, Object>> getScheduleList(String startDate, String endDate, Long doctorId) {
        return mapper.selectScheduleList(startDate, endDate, doctorId);
    }

    @Override
    public List<Map<String, Object>> getScheduleAppointmentList(String startDate, String endDate, Long customerId, Long departmentId, Long doctorId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> result = new ArrayList<>();
        // 开始日期
        Date start;
        if (startDate == null) {
            start = sdf.parse(sdf.format(new Date()));
        } else {
            start = sdf.parse(startDate);
        }
        // 结束日期
        Date end;
        if (endDate == null) {
            end = new Date(start.getTime() + 24 * 60 * 60 * 1000);
        } else {
            end = new Date(sdf.parse(endDate).getTime() + 24 * 60 * 60 * 1000);
        }
        // 日期循环
        String s, e;
        while (start.getTime() < end.getTime()) {
            // 整理时间格式
            Calendar dd = Calendar.getInstance();
            dd.setTime(start);
            dd.add(Calendar.DAY_OF_MONTH, 1);
            s = sdf.format(start);
            e = sdf.format(dd.getTime());
            // 查询数据
            List<Map<String, Object>> list = mapper.selectScheduleAppointmentList(s, e, customerId, departmentId, doctorId);
            Map<String, Object> map = new HashMap<>();
            map.put("date", s);
            map.put("list", list);
            result.add(map);
            // 下一天
            start.setTime(start.getTime() + 24 * 60 * 60 * 1000);
        }
        // 返回结果
        return result;
    }
}
