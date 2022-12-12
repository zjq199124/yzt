package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuPrescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "处方接口")
@RestController
@RequestMapping("/prescription")
public class BuPrescriptionController extends BaseController {

    @Autowired
    private IBuPrescriptionService service;


    @ApiOperation(value = "新增处方", notes = "新增处方")
    @PostMapping("/addPrescription")
    public Result<Boolean> addPrescription(@RequestBody BuPrescription prescription) {
        prescription.setStatus(1);
        prescription.setCreateTime(new Date());
        prescription.setUpdateTime(prescription.getCreateTime());
        Boolean res = service.addPrescription(prescription);
        return Result.success(res);
    }


    @ApiOperation(value = "删除处方", notes = "删除处方")
    @GetMapping("/delPrescription")
    public Result delPrescription(Long id) {
        // TODO: 需要增加状态判断，很多状态是不能删除的
        Integer res = service.delPrescription(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改处方信息", notes = "修改处方信息")
    @PostMapping("/setPrescription")
    public Result setPrescription(@RequestBody BuPrescription prescription) {
        prescription.setUpdateTime(new Date());
        Integer res = service.setPrescription(prescription);
        return Result.success(prescription);
    }


    @ApiOperation(value = "修改处方状态", notes = "修改处方状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "处方单id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setPrescriptionStatus")
    public Result setPrescriptionStatus(Long id, Integer status) {
        BuPrescription prescription = new BuPrescription();
        prescription.setId(id);
        prescription.setStatus(status);
        prescription.setUpdateTime(new Date());
        service.setPrescriptionStatus(prescription);
        return Result.success(prescription);
    }

    @ApiOperation(value = "结算处方", notes = "结算处方")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "处方单id", required = true),
            @ApiImplicitParam(value = "鉴权token", name = "token", paramType = "header", dataType = "String", required = true)
    })
    @GetMapping("/setPaymentStatus")
    public Result setPaymentStatus(Long id) {
        Long userId = ((Number) getClaims().get("id")).longValue();
        service.setPaymentStatus(id, userId);
        return Result.success();
    }


    @ApiOperation(value = "获取处方信息", notes = "获取处方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "处方单ID", required = true, dataTypeClass = Long.class),
    })
    @GetMapping("/getPrescription")
    public Result getPrescription(Long id) {
        BuPrescription prescription = service.getPrescription(id);
        return Result.success(prescription);
    }


    @ApiOperation(value = "获取处方单列表", notes = "获取处方单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outpatientId", value = "挂号ID", required = false, dataTypeClass = Long.class),
    })
    @GetMapping("/getPrescriptionList")
    public Result getPrescriptionList(Long outpatientId) {
        List<Map<String, Object>> list = service.getPrescriptionList(outpatientId);
        return Result.success(list);
    }


    // 确定下这个接口滨郝有没有在使用
    // 没有的话转移到treatmentController中

    @ApiOperation(value = "获取处方单预约计数", notes = "获取处方单预约计数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prescriptionId", value = "处方单ID", required = true, dataTypeClass = Long.class),
    })
    @GetMapping("/getPrescriptionTreatmentSummary")
    public Result getPrescriptionTreatmentSummary(Long prescriptionId) {
        List<Map<String, Object>> list = service.getPrescriptionItemSummary(prescriptionId);
        return Result.success(list);
    }


    @ApiOperation(value = "获取患者处方单预约计数", notes = "获取患者处方单预约计数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientId", value = "患者ID", required = true, dataTypeClass = Long.class),
    })
    @GetMapping("/getPatientTreatmentSummary")
    public Result getPatientTreatmentSummary(Long patientId) {
        List<Map<String, Object>> list = service.getPatientPrescriptionItemSummary(patientId);
        return Result.success(list);
    }

}
