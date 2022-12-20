package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TxXzcData;
import com.maizhiyu.yzt.entity.TxXzcRun;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITeEquipService;
import com.maizhiyu.yzt.service.ITeMaintainService;
import com.maizhiyu.yzt.service.ITeWarnService;
import com.maizhiyu.yzt.service.ITxXzcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Api(tags = "熏蒸床调试接口")
@RestController
@RequestMapping("/xzcdebug")
public class TeXzcDebugController {

    @Autowired
    private ITeEquipService equipService;

    @Autowired
    private ITxXzcService xzcService;

    @Autowired
    private ITeWarnService warnService;

    @Autowired
    private ITeMaintainService maintainService;


    @ApiOperation(value = "获取设备运行列表", notes = "获取设备运行列表-熏蒸床")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "设备编码", required = true),
    })
    @GetMapping("/getRunList")
    public Result getRunList(String code) {
        String startDate = "2020-01-01";
        String endDate = "2030-01-01";
        IPage<TxXzcRun> list = xzcService.getRunList(new Page(-1,0),code, startDate, endDate);
        return Result.success(list);
    }


    @ApiOperation(value = "获取设备运行数据", notes = "获取设备运行数据-熏蒸床")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "设备编码", required = true),
            @ApiImplicitParam(name = "runId", value = "运行ID", required = true),
    })
    @GetMapping("/getRunData")
    public Result getRunData(String code, String runId) {
        List<TxXzcData> list = xzcService.getRunData(code, runId);
        return Result.success(list);
    }


    @ApiOperation(value = "获取设备维护列表", notes = "获取设备维护列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "equipId", value = "设备ID", required = true),
    })
    @GetMapping("/getMaintainList")
    public Result getMaintainList(Long equipId) {
        IPage<Map<String, Object>> list = maintainService.getMaintainList(null,null, equipId, null, null, null);
        return Result.success(list);
    }


    @ApiOperation(value = "获取设备预警列表", notes = "获取设备预警列表")
    @ApiImplicitParams({})
    @GetMapping("/getWarnList")
    public Result getWarnList() {
        IPage<Map<String, Object>> list = warnService.getWarnList(null,null, null, null, null, null, null);
        return Result.success(list);
    }

}
