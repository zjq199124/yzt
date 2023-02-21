package com.maizhiyu.yzt.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.BuPrescriptionItemTaskRo;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.IBuOutpatientAppointmentService;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentItemService;
import com.maizhiyu.yzt.service.IHsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Api(tags = "2.1门诊预约接口")
@RestController
@RequestMapping("/outpatientAppointment")
public class BuOutpatientAppointmentController extends BaseController {

    @Resource
    private IBuOutpatientAppointmentService buOutpatientAppointmentService;

    @Resource
    private IBuPrescriptionItemAppointmentItemService buPrescriptionItemAppointmentItemService;

    @Resource
    private IHsUserService userService;

    @ApiOperation(value = "查询门诊预约列表")
    @PostMapping("/list")
    public Result<List<BuOutpatientAppointment>> outpatientAppointmentList(@RequestBody OutpatientAppointmentRo outpatientAppointmentRo) throws Exception {
        log.info("/*************查询待预约列表*************/");
        //Long customerId = ((Number) getClaims().get("customerId")).longValue();
        HsUserDetails hsUserDetails = getHsUserDetails();
        Long customerId = getCustomerId(hsUserDetails);
        outpatientAppointmentRo.setCustomerId(customerId);
        Page<BuOutpatientAppointment> page = buOutpatientAppointmentService.list(outpatientAppointmentRo);
        return Result.success(page);
    }

    private Long getCustomerId(HsUserDetails hsUserDetails) {
        if (Objects.nonNull(hsUserDetails)) {
            Map<String, Object> userMap = userService.getUser(hsUserDetails.getId());
            if (Objects.nonNull(userMap)) {
                return Long.parseLong(userMap.get("customerId").toString());
            }
        }
        return null;
    }

    @ApiOperation(value = "预约状况接口")
    @GetMapping("/appointmentDetail")
    @ApiImplicitParam(name = "outpatientAppointmentId", value = "当前处治下的预约数据id", required = true)
    public Result<BuOutpatientAppointment> appointment(Long outpatientAppointmentId) {
        BuOutpatientAppointment buOutpatientAppointment = buOutpatientAppointmentService.appointmentDetail(outpatientAppointmentId);
        return Result.success(buOutpatientAppointment);
    }


    @ApiOperation(value = "预约")
    @PostMapping("/makeAppointment")
    public Result<Boolean> makeAppointment(@RequestBody BuPrescriptionItemTaskRo buPrescriptionItemTaskRo) throws Exception {
        HsUserDetails hsUserDetails = getHsUserDetails();
        Long customerId = getCustomerId(hsUserDetails);
        BuPrescriptionItemAppointmentItem buPrescriptionItemAppointmentItem = new BuPrescriptionItemAppointmentItem();
        BeanUtil.copyProperties(buPrescriptionItemTaskRo, buPrescriptionItemAppointmentItem);
        buPrescriptionItemAppointmentItem.setCustomerId(customerId);
        Boolean result = buPrescriptionItemAppointmentItemService.makeAppointment(buPrescriptionItemAppointmentItem);
        return Result.success(result);
    }
}



























