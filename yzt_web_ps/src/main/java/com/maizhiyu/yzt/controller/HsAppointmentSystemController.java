package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.TimeSlotVo;
import com.maizhiyu.yzt.service.IHsAppointmentSystemService;
import com.maizhiyu.yzt.vo.TimeSlotDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "患者端系统预约时段设置")
@RestController
@RequestMapping("/appointmentSystem")
public class HsAppointmentSystemController {

    @Resource
    private IHsAppointmentSystemService hsAppointmentSystemService;

    @ApiOperation("展示预约时段")
    @PostMapping("/getTimeSlotByDate")
    public Result getTimeSlotByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date, Long outpatientAppointmentId,Long customerId) {
        List<TimeSlotDetailVo> resultList = hsAppointmentSystemService.getTimeSlotByDate(customerId, outpatientAppointmentId, date);
        return Result.success(resultList);
    }
}
