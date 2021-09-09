package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.BuTreatment;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuTreatmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "治疗预约接口")
@RestController
@RequestMapping("/treatment")
public class BuTreatmentController {

    @Autowired
    private IBuTreatmentService service;


    @ApiOperation(value = "新增治疗预约", notes = "新增治疗预约")
    @PostMapping("/addTreatment")
    public Result addTreatment(@RequestBody BuTreatment treatment) {
        treatment.setStatus(1);
        treatment.setCreateTime(new Date());
        treatment.setUpdateTime(treatment.getCreateTime());
        Integer res = service.addTreatment(treatment);
        return Result.success(treatment);
    }


    @ApiOperation(value = "修改治疗预约状态", notes = "修改治疗预约状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约单id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setTreatmentStatus")
    public Result setTreatmentStatus(Long id, Integer status) {
        BuTreatment treatment = new BuTreatment();
        treatment.setId(id);
        treatment.setStatus(status);
        treatment.setUpdateTime(new Date());
        service.setTreatment(treatment);
        return Result.success(treatment);
    }


    @ApiOperation(value = "修改治疗评价", notes = "修改治疗评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约单id", required = true),
            @ApiImplicitParam(name = "evaluation", value = "评价内容", required = true)
    })
    @GetMapping("/setTreatmentEvaluation")
    public Result setTreatmentStatus(Long id, String evaluation) {
        BuTreatment treatment = new BuTreatment();
        treatment.setId(id);
        treatment.setStatus(1);
        treatment.setEvaluation(evaluation);
        treatment.setUpdateTime(new Date());
        service.setTreatment(treatment);
        return Result.success(treatment);
    }


    @ApiOperation(value = "获取治疗预约信息", notes = "获取治疗预约信息(包括评价)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约单id", required = true),
    })
    @GetMapping("/getTreatment")
    public Result getTreatment(Long id) {
        BuTreatment treatment = service.getTreatment(id);
        return Result.success(treatment);
    }


    @ApiOperation(value = "获取用户治疗预约列表", notes = "获取用户治疗预约列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "patientId", value = "患者ID", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getUserTreatmentList")
    public Result getUserTreatmentList(Long userId, Long patientId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getPsUserTreatmentList(userId, patientId, null, null);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
