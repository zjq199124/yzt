package com.maizhiyu.yzt.controller;

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
public class BuPrescriptionController {

    @Autowired
    private IBuPrescriptionService service;


    @ApiOperation(value = "获取处方信息", notes = "获取处方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "处方单ID", required = true),
    })
    @GetMapping("/getPrescription")
    public Result getPrescription(Long id) {
        BuPrescription prescription = service.getPrescription(id);
        return Result.success(prescription);
    }


    @ApiOperation(value = "获取处方单预约计数", notes = "获取处方单预约计数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prescriptionId", value = "处方单ID", required = true),
    })
    @GetMapping("/getPrescriptionTreatmentSummary")
    public Result getPrescriptionTreatmentSummary(Long prescriptionId) {
        List<Map<String, Object>> list = service.getPrescriptionItemSummary(prescriptionId);
        return Result.success(list);
    }


    @ApiOperation(value = "获取患者处方单预约计数", notes = "获取患者处方单预约计数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientId", value = "患者ID", required = true),
    })
    @GetMapping("/getPatientTreatmentSummary")
    public Result getPatientTreatmentSummary(Long patientId) {
        List<Map<String, Object>> list = service.getPatientPrescriptionItemSummary(patientId);
        return Result.success(list);
    }

}
