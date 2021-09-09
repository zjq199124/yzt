package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.BuSchedule;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "排班接口")
@RestController
@RequestMapping("/schedule")
public class BuScheduleController {

    @Autowired
    private IBuScheduleService service;


    @ApiOperation(value = "增加排班", notes = "增加排班")
    @ApiImplicitParams({})
    @PostMapping("/addSchedule")
    public Result addSchedule (@RequestBody BuSchedule schedule) {
        schedule.setStatus(1);
        schedule.setCreateTime(new Date());
        schedule.setUpdateTime(schedule.getCreateTime());
        Integer res = service.addSchedule(schedule);
        return Result.success(schedule);
    }


    @ApiOperation(value = "删除排班", notes = "删除排班")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "排班id", required = true)
    })
    @GetMapping("/delSchedule")
    public Result delSchedule(Long id) {
        Integer res = service.delSchedule(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改排班信息", notes = "修改排班信息(不存在则新增)")
    @PostMapping("/setSchedule")
    public Result setSchedule (@RequestBody BuSchedule schedule) {
        schedule.setUpdateTime(new Date());
        Integer res = service.setSchedule(schedule);
        return Result.success(schedule);
    }


    @ApiOperation(value = "获取排班信息", notes = "获取排班信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "排班ID", required = true),
    })
    @GetMapping("/getSchedule")
    public Result getSchedule(Long id) {
        Map<String, Object> schedule = service.getSchedule(id);
        return Result.success(schedule);
    }


    @ApiOperation(value = "获取排班列表", notes = "获取排班列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = true),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true),
            @ApiImplicitParam(name = "doctorId", value = "医生ID", required = false),
    })
    @GetMapping("/getScheduleList")
    public Result getScheduleList(String startDate, String endDate, Long doctorId) {
        List<Map<String, Object>> list = service.getScheduleList(startDate, endDate, doctorId);
        return Result.success(list);
    }


    @ApiOperation(value = "获取排班及预约列表", notes = "获取排班及预约列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = true),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true),
            @ApiImplicitParam(name = "customerId", value = "医院ID", required = false),
            @ApiImplicitParam(name = "departmentId", value = "科室ID", required = false),
            @ApiImplicitParam(name = "doctorId", value = "医生ID", required = false),
    })
    @GetMapping("/getScheduleAppointmentList")
    public Result getScheduleAppointmentList(String startDate, String endDate, Long customerId, Long departmentId, Long doctorId) throws ParseException {
        List<Map<String, Object>> list = service.getScheduleAppointmentList(startDate, endDate, customerId, departmentId, doctorId);
        return Result.success(list);
    }

}
