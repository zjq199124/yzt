package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.IBuOutpatientAppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


@Api(tags = "门诊预约接口")
@RestController
@RequestMapping("/outpatientAppointment")
public class BuOutpatientAppointmentController extends BaseController {

    @Resource
    private IBuOutpatientAppointmentService buOutpatientAppointmentService;

    @ApiOperation(value = "查询门诊预约列表")
    @PostMapping("/list")
    public Result<List<BuOutpatientAppointment>> outpatientAppointmentList(@RequestBody OutpatientAppointmentRo outpatientAppointmentRo) {
        HsUserDetails hsUserDetails = getHsUserDetails();
        outpatientAppointmentRo.setCustomerId(hsUserDetails.getCustomerId());
        Page<BuOutpatientAppointment> page = buOutpatientAppointmentService.list(outpatientAppointmentRo);
        return Result.success(page);
    }
}
