package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.TeFault;
import com.maizhiyu.yzt.entity.TeTrouble;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITeFaultService;
import com.maizhiyu.yzt.service.ITeTroubleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "故障接口")
@RestController
@RequestMapping("/fault")
public class TeFaultController {

    @Autowired
    private ITeFaultService faultService;

    @Autowired
    private ITeTroubleService troubleService;


    @ApiOperation(value = "上报故障", notes = "上报故障")
    @PostMapping("/addFault")
    public Result addFault(@RequestBody TeFault fault) {
        fault.setStatus(1);
        fault.setReportTime(new Date());
        Integer res = faultService.addFault(fault);
        return Result.success(fault);
    }


    @ApiOperation(value = "设置故障评价", notes = "设置故障评价")
    @PostMapping("/setFaultEvaluation")
    public Result setFaultEvaluation(@RequestBody TeFault fault) {
        fault.setStatus(4);
        fault.setEvaluationTime(new Date());
        Integer res = faultService.setFault(fault);
        return Result.success(fault);
    }


    @ApiOperation(value = "获取故障信息", notes = "获取故障信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "故障id", required = true)
    })
    @GetMapping("/getFault")
    public Result getFault(Long id) {
        TeFault fault = faultService.getFault(id);
        return Result.success(fault);
    }


    @ApiOperation(value = "获取故障列表", notes = "获取故障列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "用户ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getFaultList")
    public Result getFaultList(Long customerId, Integer status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = faultService.getFaultList(customerId, status);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取问题详情", notes = "获取问题详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题id", required = true)
    })
    @GetMapping("/getTrouble")
    public Result getTrouble(Long id) {
        TeTrouble trouble = troubleService.getTrouble(id);
        return Result.success(trouble);
    }


    @ApiOperation(value = "获取常见问题列表", notes = "获取常见问题列表(不带问题详情)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "etype", value = "设备类型", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
    })
    @GetMapping("/getTroubleList")
    public Result getTroubleList(Integer status, Integer etype) {
        List<Map<String, Object>> list = troubleService.getTroubleList(etype, status);
        return Result.success(list);
    }

}
