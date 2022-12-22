package com.maizhiyu.yzt.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.HsAppointmentSystem;
import com.maizhiyu.yzt.vo.TimeSlotDetailVo;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.IHsAppointmentSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "系统预约时段设置")
@RestController
@RequestMapping("/appointmentSystem")
public class HsAppointmentSystemController extends BaseController {

    @Resource
    private IHsAppointmentSystemService hsAppointmentSystemService;

    @ApiOperation("增加预约时段配置信息")
    @PostMapping("/add")
    public Result addAppointmentTimeSlot(@RequestBody HsAppointmentSystem hsAppointmentSystem) {
        HsUserDetails hsUserDetails = getHsUserDetails();
        hsAppointmentSystem.setCustomerId(hsUserDetails.getCustomerId());
        Boolean result = hsAppointmentSystemService.add(hsAppointmentSystem);
        return Result.success(result);
    }

    @ApiOperation("查询当前正在使用的预约时段信息")
    @GetMapping("/getNowTimeSlot")
    public Result getNowTimeSlot() {
        HsUserDetails hsUserDetails = getHsUserDetails();
        HsAppointmentSystem hsAppointmentSystem = hsAppointmentSystemService.getNowTimeSlot(hsUserDetails.getCustomerId());
        return Result.success(hsAppointmentSystem);
    }

    @ApiOperation("查询最后的一条预约时段信息")
    @GetMapping("/getLastNowTimeSlot")
    public Result getLastNowTimeSlot() {
        HsUserDetails hsUserDetails = getHsUserDetails();
        HsAppointmentSystem hsAppointmentSystem = hsAppointmentSystemService.getLastNowTimeSlot(hsUserDetails.getCustomerId());
        return Result.success(hsAppointmentSystem);
    }

    @ApiOperation("展示预约时段")
    @GetMapping("/getTimeSlotByDate")
    public Result getTimeSlotByDate(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date, Long outpatientAppointmentId) {
        HsUserDetails hsUserDetails = getHsUserDetails();
        List<TimeSlotDetailVo> resultList = hsAppointmentSystemService.getTimeSlotByDate(hsUserDetails.getCustomerId(), outpatientAppointmentId, date);
        return Result.success(resultList);
    }
}
