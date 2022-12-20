package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TeFault;
import com.maizhiyu.yzt.entity.TeFaultSolution;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITeFaultService;
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
    private ITeFaultService service;


    @ApiOperation(value = "上报故障", notes = "上报故障")
    @PostMapping("/addFault")
    public Result addFault(@RequestBody TeFault fault) {
        fault.setStatus(1);
        Integer res = service.addFault(fault);
        return Result.success(fault);
    }


    @ApiOperation(value = "解决故障", notes = "解决故障")
    @PostMapping("/setFaultResolve")
    public Result setFaultResolve(@RequestBody TeFault fault) {
        fault.setStatus(2);
        Integer res = service.setFault(fault);
        return Result.success(fault);
    }


    @ApiOperation(value = "回访故障", notes = "回访故障")
    @PostMapping("/setFaultFollowup")
    public Result setFaultFollowup(@RequestBody TeFault fault) {
        fault.setStatus(3);
        fault.setFollowupTime(new Date());
        Integer res = service.setFault(fault);
        return Result.success(fault);
    }


    @ApiOperation(value = "获取故障信息", notes = "获取故障信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "故障id", required = true)
    })
    @GetMapping("/getFault")
    public Result getFault(Long id) {
        TeFault fault = service.getFault(id);
        return Result.success(fault);
    }


    @ApiOperation(value = "获取故障列表", notes = "获取故障列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "用户ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "code", value = "设备编码", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getFaultList")
    public Result getFaultList(Long customerId, Integer status, String code,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Map<String, Object>> list = service.getFaultList(new Page(pageNum,pageSize),customerId, status, code);
        return Result.success(list);
    }


    @ApiOperation(value = "获取故障解决方案列表", notes = "获取故障解决方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "设备类型", required = true),
    })
    @GetMapping("/getFaultSolutionList")
    public Result getFaultSolutionList(Integer type) {
        List<TeFaultSolution> list = service.getFaultSolutionList(type);
        return Result.success(list);
    }
}
