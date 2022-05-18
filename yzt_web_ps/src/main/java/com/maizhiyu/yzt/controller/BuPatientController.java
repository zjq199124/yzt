package com.maizhiyu.yzt.controller;


import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuPatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "患者接口")
@RestController
@RequestMapping("/patient")
public class BuPatientController {

    @Autowired
    private IBuPatientService service;


    @ApiOperation(value = "增加患者", notes = "增加患者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @PostMapping("/addPatient")
    public Result addPatient (@RequestParam Long userId, @RequestBody BuPatient patient) {
        patient.setStatus(1);
        patient.setCustomerId(0L);   // 从公众号增加的患者，customerId默认都是0，所有客户都可以查到这个用户
        patient.setCreateTime(new Date());
        patient.setUpdateTime(patient.getCreateTime());
        Integer res = service.addPatientByPsUser(userId, patient);
        return Result.success(patient);
    }


    @ApiOperation(value = "删除患者", notes = "删除患者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "患者id", required = true)
    })
    @GetMapping("/delPatient")
    public Result delPatient(Long id) {
        Integer res = service.delPatient(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改患者信息", notes = "修改患者信息")
    @PostMapping("/setPatient")
    public Result setPatient (@RequestBody BuPatient patient) {
        patient.setUpdateTime(new Date());
        Integer res = service.setPatient(patient);
        return Result.success(patient);
    }


    @ApiOperation(value = "获取患者信息", notes = "获取患者信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "患者ID", required = true),
    })
    @GetMapping("/getPatient")
    public Result getPatient(Long id) {
        BuPatient patient = service.getPatient(id);
        return Result.success(patient);
    }


    @ApiOperation(value = "获取患者列表", notes = "获取患者列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
    })
    @GetMapping("/getPatientList")
    public Result getPatientList(Long userId) {
        List<BuPatient> list = service.getPatientListByPsuser(userId);
        return Result.success(list);
    }


    @ApiOperation(value = "获取患者处方单列表", notes = "获取患者处方单列表，已经包含处方单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientId", value = "患者ID", required = true),
            @ApiImplicitParam(name = "type", value = "处方类型", required = false),
    })
    @GetMapping("/getPatientPrescriptionList")
    public Result getPatientPrescriptionList(Long patientId, Integer type) {
        List<Map<String, Object>> list = service.getPatientPrescriptionList(patientId, type);
        return Result.success(list);
    }

}
