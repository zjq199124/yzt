package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    @ApiImplicitParams({})
    @PostMapping("/addPatient")
    public Result addPatient (@RequestBody BuPatient patient) {
        patient.setStatus(1);
        patient.setCreateTime(new Date());
        patient.setUpdateTime(patient.getCreateTime());
        Integer res = service.addPatient(patient);
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


    @ApiOperation(value = "获取患者列表", notes = "获取患者列表(搜索使用)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "term", value = "搜索词", required = true),
    })
    @GetMapping("/getPatientList")
    public Result getPatientList(String term) {
        List<BuPatient> list = service.getPatientList(term);
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


    @ApiOperation(value = "获取医生诊断过的患者列表", notes = "获取医生诊断过的患者列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "doctorId", value = "医生ID", required = true),
            @ApiImplicitParam(name = "term", value = "搜索词", required = true),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getPatientListByDoctor")
    public Result getPatientListByDoctor(Long doctorId, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getPatientListByDoctor(doctorId, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取治疗师治疗过的患者列表", notes = "获取治疗师治疗过的患者列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "therapistId", value = "治疗师ID", required = true),
            @ApiImplicitParam(name = "type", value = "处方类型", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = true),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getPatientListByTherapist")
    public Result getPatientListByTherapist(Long therapistId, Integer type, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getPatientListByTherapist(therapistId, type, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }
}
