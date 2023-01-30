package com.maizhiyu.yzt.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.entity.PsUserPatient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.AppointmentRo;
import com.maizhiyu.yzt.ro.BuPrescriptionItemAppointmentItemRo;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;
import com.maizhiyu.yzt.service.IBuOutpatientAppointmentService;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentItemService;
import com.maizhiyu.yzt.service.IPsUserPatientService;
import com.maizhiyu.yzt.serviceimpl.PsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "患者端门诊预约接口")
@RestController
@RequestMapping("/outpatientAppointment")
public class BuOutpatientAppointmentController {

    @Resource
    private IBuOutpatientAppointmentService buOutpatientAppointmentService;

    @Resource
    private IBuPrescriptionItemAppointmentItemService buPrescriptionItemAppointmentItemService;

    @Resource
    private IPsUserPatientService psUserPatientService;

    @ApiOperation(value = "查询门诊预约列表")
    @PostMapping("/list")
    public Result<List<BuOutpatientAppointment>> outpatientAppointmentList(@RequestBody OutpatientAppointmentRo outpatientAppointmentRo) {
        PsUserPatient psUserPatient = psUserPatientService.selectByUserId(outpatientAppointmentRo.getPsUserId());
        outpatientAppointmentRo.setPatientId(psUserPatient.getPatientId());
        Page<BuOutpatientAppointment> page = buOutpatientAppointmentService.list(outpatientAppointmentRo);
        return Result.success(page);
    }

    @ApiOperation(value = "预约状况接口")
    @GetMapping("/appointment")
    @ApiImplicitParam(name = "outpatientAppointmentId", value = "当前诊断下的预约数据id", required = true)
    public Result<BuOutpatientAppointment> appointment(Long outpatientAppointmentId) {
        BuOutpatientAppointment buOutpatientAppointment = buOutpatientAppointmentService.appointmentDetail(outpatientAppointmentId);
        return Result.success(buOutpatientAppointment);
    }


    @ApiOperation(value = "预约")
    @PostMapping("/makeAppointment")
    public Result<Boolean> makeAppointment(@RequestBody BuPrescriptionItemAppointmentItemRo buPrescriptionItemAppointmentItemRo) {
        BuPrescriptionItemAppointmentItem buPrescriptionItemAppointmentItem = new BuPrescriptionItemAppointmentItem();
        BeanUtil.copyProperties(buPrescriptionItemAppointmentItemRo, buPrescriptionItemAppointmentItem);
        Boolean result = buPrescriptionItemAppointmentItemService.makeAppointment(buPrescriptionItemAppointmentItem);
        return Result.success(result);
    }

   /* @ApiOperation(value = "删除预约")
    @GetMapping("/deleteAppointment")
    @ApiImplicitParam(name = "buPrescriptionItemAppointmentItemId", value = "适宜技术小项目预约详情数据主键id", required = true)
    public Result<Boolean> deleteAppointment(Long buPrescriptionItemAppointmentItemId) {
        Boolean result = buPrescriptionItemAppointmentItemService.deleteAppointment(buPrescriptionItemAppointmentItemId);
        return Result.success(result);
    }

    @ApiOperation(value = "批量预约操作")
    @PostMapping("/appointment")
    public Result<Boolean> appointment(@RequestBody AppointmentRo appointmentRo) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        appointmentRo.setCustomerId(customerId);
        Boolean result = buPrescriptionItemAppointmentItemService.appointment(appointmentRo);
        return Result.success(result);
    }*/
}



























