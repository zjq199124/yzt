package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.BuTreatment;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuOutpatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "门诊预约接口")
@RestController
@RequestMapping("/outpatient")
public class BuOutpatientController {

    @Autowired
    private IBuOutpatientService service;


    @ApiOperation(value = "新增门诊预约", notes = "新增门诊预约")
    @PostMapping("/addOutpatient")
    public Result addOutpatient(@RequestBody BuOutpatient outpatient) {
        outpatient.setStatus(1);
        outpatient.setCreateTime(new Date());
        outpatient.setUpdateTime(outpatient.getCreateTime());
        Integer res = service.addOutpatient(outpatient);
        return Result.success(outpatient);
    }


    @ApiOperation(value = "修改门诊预约状态", notes = "修改门诊预约状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约单id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setOutpatientStatus")
    public Result setOutpatientStatus(Long id, Integer status) {
        BuOutpatient outpatient = new BuOutpatient();
        outpatient.setId(id);
        outpatient.setStatus(status);
        outpatient.setUpdateTime(new Date());
        service.setOutpatient(outpatient);
        return Result.success(outpatient);
    }


    @ApiOperation(value = "获取门诊预约信息", notes = "获取门诊预约信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约id", required = true),
    })
    @GetMapping("/getOutpatient")
    public Result getOutpatient(Long id) {
        BuOutpatient outpatient = service.getOutpatient(id);
        return Result.success(outpatient);
    }


    @ApiOperation(value = "获取门诊预约列表", notes = "获取门诊预约列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = true),
            @ApiImplicitParam(name = "endDate", value = "开始日期", required = false),
            @ApiImplicitParam(name = "customerId", value = "医院ID", required = true),
            @ApiImplicitParam(name = "departmentId", value = "科室ID", required = false),
            @ApiImplicitParam(name = "doctorId", value = "医生ID", required = false),
            @ApiImplicitParam(name = "patientId", value = "患者ID", required = false),
            @ApiImplicitParam(name = "type", value = "预约类型", required = false),
            @ApiImplicitParam(name = "status", value = "预约状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getOutpatientList")
    public Result getOutpatientList(String startDate, String endDate,
            Long customerId, Long departmentId, Long doctorId, Long patientId,
            Integer type, Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getOutpatientList(
                startDate, endDate, customerId, departmentId, doctorId, patientId, type, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
