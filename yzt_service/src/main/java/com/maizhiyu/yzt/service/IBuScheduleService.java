package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.BuSchedule;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IBuScheduleService {

    Integer addSchedule(BuSchedule schedule);

    Integer delSchedule(Long id);

    Integer setSchedule(BuSchedule schedule);

    Map<String, Object> getSchedule(Long id);

    List<Map<String, Object>> getScheduleList(String startDate, String endDate, Long doctorId);

    List<Map<String, Object>> getScheduleAppointmentList(String startDate, String endDate, Long customerId, Long departmentId, Long doctorId) throws ParseException;

}
