package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.enums.SmsTemplateEnum;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.AppointmentRo;
import com.maizhiyu.yzt.ro.ItemTaskRo;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.ro.WaitTreatmentRo;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.*;
import com.maizhiyu.yzt.serviceimpl.MsCustomerService;
import com.maizhiyu.yzt.vo.BuPrescriptionItemTaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Api(tags = "治疗签到接口-新")
@RestController
@RequestMapping("/prescriptionItemTask")
public class BuPrescriptionItemTaskController extends BaseController {

    @Value("${sms.signName}")
    private String signName;

    @Resource
    private IBuPrescriptionItemService buPrescriptionItemService;

    @Resource
    private BuSignatureService buSignatureService;

    @Resource
    private MsCustomerService msCustomerService;

    @Resource
    private BuPrescriptionItemTaskService buPrescriptionItemTaskService;

    @Resource
    private ISmsService smsService;

    @Resource
    private IBuOutpatientAppointmentService buOutpatientAppointmentService;

    @Resource
    private IBuPrescriptionItemAppointmentItemService buPrescriptionItemAppointmentItemService;

    @Resource
    private IHsUserService userService;

    @ApiOperation(value = "删除预约")
    @GetMapping("/deleteAppointment")
    @ApiImplicitParam(name = "buPrescriptionItemTaskId", value = "处治小项目任务的Id", required = true)
    public Result<Boolean> deleteAppointment(Long buPrescriptionItemTaskId) {
        Boolean result = buPrescriptionItemTaskService.deleteAppointment(buPrescriptionItemTaskId);
        return Result.success(result);
    }

    @ApiOperation(value = "批量预约操作")
    @PostMapping("/appointment")
    public Result<Boolean> appointment(@RequestBody AppointmentRo appointmentRo) throws Exception {
        log.info("/*************批量预约*************/");
        Long customerId = getCustomerId();
        appointmentRo.setCustomerId(customerId);
        Boolean result = buPrescriptionItemTaskService.appointment(appointmentRo);
        return Result.success(result);
    }



    @ApiOperation("待签到列表")
    @PostMapping("/waitSignatureList")
    public Result<Page<BuPrescriptionItemTaskVo>> waitSignatureList(@RequestBody WaitSignatureRo waitSignatureRo) throws Exception {
        Long customerId = getCustomerId();
        waitSignatureRo.setCustomerId(customerId);
        Page<BuPrescriptionItemTaskVo> page = buPrescriptionItemTaskService.selectWaitSignatureList(waitSignatureRo);
        return Result.success(page);
    }


    @ApiOperation("签到")
    @PostMapping("/signature")
    public Result<Boolean> signature(@RequestBody ItemTaskRo itemTaskRo) {
        Preconditions.checkArgument(Objects.nonNull(itemTaskRo.getPrescriptionItemTaskId()) || Objects.nonNull(itemTaskRo.getPrescriptionItemId()),
                "处治小项目id和任务id不能同时为空!");
        Boolean result = buPrescriptionItemTaskService.signature(itemTaskRo);
        return Result.success(result);
    }

    @ApiOperation("待治疗列表")
    @PostMapping("/waitTreatmentList")
    public Result<Page<BuPrescriptionItemTaskVo>> waitTreatmentList(@RequestBody WaitTreatmentRo waitTreatmentRo) throws Exception {
        Long customerId = getCustomerId();
        waitTreatmentRo.setCustomerId(customerId);
        Page<BuPrescriptionItemTaskVo> page = buPrescriptionItemTaskService.waitTreatmentList(waitTreatmentRo);
        return Result.success(page);
    }


    @ApiOperation("开始治疗接口")
    @PostMapping("/startTreatment")
    public Result<Boolean> startTreatment(@RequestBody ItemTaskRo itemTaskRo) {
        Assert.notNull(itemTaskRo.getPrescriptionItemTaskId(), "任务id不能为空!");
        boolean res = buPrescriptionItemTaskService.startTreatment(itemTaskRo);
        return Result.success(res);
    }

    @ApiOperation("结束治疗")
    @GetMapping("/endTreatment")
    public Result<Boolean> endTreatment(Long buPrescriptionItemTaskId) {
        Assert.notNull(buPrescriptionItemTaskId, "任务id不能为空!");
        boolean res = buPrescriptionItemTaskService.endTreatment(buPrescriptionItemTaskId);
        return Result.success(res);
    }


    @ApiOperation("已治疗列表接口")
    @PostMapping("/treatmentList")
    public Result<Page<BuPrescriptionItemTaskVo>> treatmentList(@RequestBody ItemTaskRo itemTaskRo) throws Exception {
        Long customerId = getCustomerId();
        itemTaskRo.setCustomerId(customerId);
        Page<BuPrescriptionItemTaskVo> resultPage = buPrescriptionItemTaskService.treatmentList(itemTaskRo);
        return Result.success(resultPage);
    }

    @ApiOperation("治疗记录详情")
    @GetMapping("/treatmentRecordDetail")
    @ApiImplicitParam(name = "prescriptionItemTaskId", value = "处方小项目任务id")
    public Result<BuPrescriptionItemTaskVo> selectCureDetailBySignatureId(Long prescriptionItemTaskId) {
        BuPrescriptionItemTaskVo buPrescriptionItemTaskVo = buPrescriptionItemTaskService.treatmentRecordDetail(prescriptionItemTaskId);
        return Result.success(buPrescriptionItemTaskVo);
    }


    @ApiOperation("短信提醒")
    @PostMapping("/smsNotify")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "患者姓名", required = true),
            @ApiImplicitParam(name = "phone",value = "手机号码", required = true),
            @ApiImplicitParam(name = "tsName",value = "适宜技术名称", required = true)
    })
    public Result<Boolean> smsNotify(String name,String phone,String tsName) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        Map<String, Object> customer = msCustomerService.getCustomer(customerId);
        Map<String, String> map = new HashMap<>();
        map.put("code", "1234");
        try {
            Boolean res = smsService.sendSms(signName,SmsTemplateEnum.VERIFICATION_CODE.getCode(), phone, map);
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送短信提醒失败 "  + e.getMessage());
        }
        return Result.success(false);
    }
}























