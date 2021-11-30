package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.BuTreatment;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuTreatmentService;
import com.maizhiyu.yzt.service.IMsCustomerService;
import com.maizhiyu.yzt.utils.MyDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "治疗预约接口")
@RestController
@RequestMapping("/treatment")
public class BuTreatmentController {

    @Autowired
    private IBuTreatmentService service;

    @Autowired
    private IMsCustomerService customerService;


    @ApiOperation(value = "获取医院预约时段列表", notes = "获取医院预约时段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "医院ID", required = true),
    })
    @GetMapping("/getCustomerTimeslotList")
    public Result getCustomerTimeslotList(Long customerId) {
        List<String> list = customerService.getCustomerTimeslotList(customerId);
        return Result.success(list);
    }


    @ApiOperation(value = "新增治疗预约", notes = "新增治疗预约")
    @PostMapping("/addTreatment")
    public Result addTreatment(@RequestBody BuTreatment treatment) {
        treatment.setStatus(1);
        treatment.setCreateTime(new Date());
        treatment.setUpdateTime(treatment.getCreateTime());
        Integer res = service.addTreatment(treatment);
        return Result.success(treatment);
    }


    @ApiOperation(value = "修改治疗预约", notes = "修改治疗预约：异常，反馈，评价等都用此接口")
    @ApiImplicitParams({})
    @PostMapping("/setTreatment")
    public Result setTreatment(@RequestBody BuTreatment treatment) {
        treatment.setUpdateTime(new Date());
        Integer res = service.setTreatment(treatment);
        return Result.success(res);
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
        Integer res = service.setTreatment(treatment);
        return Result.success(treatment);
    }


//    @ApiOperation(value = "修改治疗评价", notes = "修改治疗评价，用上面的setTreatment接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "预约单id", required = true),
//            @ApiImplicitParam(name = "evaluation", value = "评价内容", required = true)
//    })
//    @GetMapping("/setTreatmentEvaluation")
//    public Result setTreatmentStatus(Long id, String evaluation) {
//        BuTreatment treatment = new BuTreatment();
//        treatment.setId(id);
//        treatment.setStatus(1);
//        treatment.setEvaluation(evaluation);
//        treatment.setUpdateTime(new Date());
//        service.setTreatment(treatment);
//        return Result.success(treatment);
//    }


    @ApiOperation(value = "获取治疗预约信息", notes = "获取治疗预约信息(包括评价)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "预约id", required = true),
    })
    @GetMapping("/getTreatment")
    public Result getTreatment(Long id) {
        BuTreatment treatment = service.getTreatment(id);
        return Result.success(treatment);
    }


    @ApiOperation(value = "获取治疗预约列表", notes = "获取治疗预约列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = true),
            @ApiImplicitParam(name = "endDate", value = "开始日期", required = false),
            @ApiImplicitParam(name = "customerId", value = "医院ID", required = true),
            @ApiImplicitParam(name = "departmentId", value = "科室ID", required = false),
            @ApiImplicitParam(name = "therapistId", value = "治疗师ID", required = false),
            @ApiImplicitParam(name = "patientId", value = "患者ID", required = false),
            @ApiImplicitParam(name = "prescriptionId", value = "处方ID", required = false),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = false),
            @ApiImplicitParam(name = "type", value = "预约类型", required = false),
            @ApiImplicitParam(name = "status", value = "预约状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getTreatmentList")
    public Result getTreatmentList(String startDate, String endDate, String date,
            Long customerId, Long departmentId, Long therapistId, Long patientId, Long prescriptionId, Long projectId,
            Integer type, Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) throws ParseException {
        if (startDate == null && endDate == null && date != null) {
            startDate = date;
            endDate = MyDate.getNextDay(date);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getTreatmentList(startDate, endDate,
                customerId, departmentId, therapistId, patientId, prescriptionId, projectId, type, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取治疗等待列表", notes = "获取治疗等待列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = true),
            @ApiImplicitParam(name = "endDate", value = "开始日期", required = false),
            @ApiImplicitParam(name = "date", value = "日期", required = false),
            @ApiImplicitParam(name = "customerId", value = "医院ID", required = true),
            @ApiImplicitParam(name = "departmentId", value = "科室ID", required = false),
            @ApiImplicitParam(name = "therapistId", value = "治疗师ID", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getTreatmentWaitingList")
    public Result getTreatmentWaitingList(String startDate, String endDate, String date,
                                   Long customerId, Long departmentId, Long therapistId,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize) throws ParseException {
        if (startDate == null && endDate == null && date != null) {
            startDate = date;
            endDate = MyDate.getNextDay(date);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getTreatmentWaitingList(startDate, endDate, customerId, departmentId, therapistId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取治疗师治疗统计数据", notes = "获取治疗师治疗统计数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = true),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true),
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "departmentId", value = "科室ID", required = false),
            @ApiImplicitParam(name = "therapistId", value = "治疗师ID", required = false),
            @ApiImplicitParam(name = "projects", value = "项目ID列表", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getTreatmentStatisticsOfTherapist")
    public Result getTreatmentStatisticsOfTherapist(String startDate, String endDate, Long customerId, Long departmentId, Long therapistId, String projects,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "100") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Long> projectList = new ArrayList<>();
        if (projects != null) {
            for (String pid : projects.split(",")) {
                projectList.add(Long.parseLong(pid));
            }
        } else {
            throw new RuntimeException("请选择项目");
        }
        List<Map<String, Object>> list = service.getTreatmentStatistics(startDate, endDate, customerId, departmentId, therapistId, projectList);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
