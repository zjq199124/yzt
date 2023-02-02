package com.maizhiyu.yzt.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.BuPrescriptionItemTaskRo;
import com.maizhiyu.yzt.ro.BuPrescriptionItemAppointmentRo;
import com.maizhiyu.yzt.service.IBuOutpatientAppointmentService;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentItemService;
import com.maizhiyu.yzt.service.IPsUserPatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "患者端门诊预约接口")
@RestController
@RequestMapping("/prescriptionItemAppointment")
public class BuOutpatientAppointmentController {

    @Resource
    private IBuOutpatientAppointmentService buOutpatientAppointmentService;

    @Resource
    private IBuPrescriptionItemAppointmentItemService buPrescriptionItemAppointmentItemService;

    @Resource
    private IPsUserPatientService psUserPatientService;

    @ApiOperation(value = "查询门诊预约列表")
    @PostMapping("/list")
    public Result<List<BuPrescriptionItemAppointment>> prescriptionItemAppointment(@RequestBody BuPrescriptionItemAppointmentRo buPrescriptionItemAppointmentRo) {
        List<PsUserPatient> psUserPatientList = psUserPatientService.selectByUserId(buPrescriptionItemAppointmentRo.getPsUserId());
        if(CollectionUtils.isEmpty(psUserPatientList))
            return Result.success();

        List<Long> patientIdList = psUserPatientList.stream().map(PsUserPatient::getPatientId).collect(Collectors.toList());
        buPrescriptionItemAppointmentRo.setPatientIdList(patientIdList);
        Page<BuPrescriptionItemAppointment> page = buPrescriptionItemAppointmentItemService.listPrescriptionItemAppointment(buPrescriptionItemAppointmentRo);
        return Result.success(page);
    }

    @ApiOperation(value = "预约状况接口")
    @GetMapping("/appointment")
    @ApiImplicitParam(name = "outpatientAppointmentId", value = "当前诊断下的预约数据id", required = true)
    public Result<BuOutpatientAppointment> appointment(Long outpatientAppointmentId) {
        BuOutpatientAppointment buOutpatientAppointment = buOutpatientAppointmentService.appointmentDetail(outpatientAppointmentId);
        return Result.success(buOutpatientAppointment);
    }


    @ApiOperation(value = "编辑或保存预约")
    @PostMapping("/saveOrUpdateAppointment")
    public Result<Boolean> makeAppointment(@RequestBody BuPrescriptionItemTaskRo buPrescriptionItemTaskRo) {
        BuPrescriptionItemAppointmentItem buPrescriptionItemAppointmentItem = new BuPrescriptionItemAppointmentItem();
        BeanUtil.copyProperties(buPrescriptionItemTaskRo, buPrescriptionItemAppointmentItem);
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



























