package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;
import com.maizhiyu.yzt.service.IBuOutpatientAppointmentService;
import com.maizhiyu.yzt.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;


@Api(tags = "门诊预约接口")
@RestController
@RequestMapping("/outpatientAppointment")
public class BuOutpatientAppointmentController {

    @Resource
    private IBuOutpatientAppointmentService buOutpatientAppointmentService;

    @ApiOperation(value = "查询门诊预约列表")
    @GetMapping("/list")
    public Result outpatientAppointmentList(HttpServletRequest request, @RequestBody OutpatientAppointmentRo outpatientAppointmentRo) {
        // 获取token字段
        Long customerId = Long.parseLong(JWTUtil.getClaims(request).get("id").toString());
        if (Objects.isNull(customerId)) {
            return Result.failure(10001, "token错误");
        }
        outpatientAppointmentRo.setCustomerId(customerId);
        List<BuOutpatientAppointment> buOutpatientAppointmentList = buOutpatientAppointmentService.list(outpatientAppointmentRo);
        return Result.success(buOutpatientAppointmentList);
    }
}
