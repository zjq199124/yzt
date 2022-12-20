package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.HsAppointmentSystem;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.IHsAppointmentSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    @PostMapping("/getNowTimeSlot")
    public Result getNowTimeSlot() {
        HsUserDetails hsUserDetails = getHsUserDetails();
        HsAppointmentSystem hsAppointmentSystem = hsAppointmentSystemService.getNowTimeSlot(hsUserDetails.getCustomerId());
        return Result.success(hsAppointmentSystem);
    }
}
