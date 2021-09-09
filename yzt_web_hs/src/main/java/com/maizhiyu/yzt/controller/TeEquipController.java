package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.TeEquip;
import com.maizhiyu.yzt.entity.TeMaintain;
import com.maizhiyu.yzt.entity.TxXzcData;
import com.maizhiyu.yzt.entity.TxXzcRun;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITeEquipService;
import com.maizhiyu.yzt.service.ITeMaintainService;
import com.maizhiyu.yzt.service.ITeWarnService;
import com.maizhiyu.yzt.service.ITxXzcService;
import com.maizhiyu.yzt.utils.MyDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "设备接口")
@RestController
@RequestMapping("/equip")
public class TeEquipController {

    @Autowired
    private ITeEquipService equipService;

    @Autowired
    private ITeMaintainService maintainService;

    @Autowired
    private ITeWarnService warnService;

    @Autowired
    private ITxXzcService xzcService;


    @ApiOperation(value = "修改设备信息", notes = "修改设备信息")
    @PostMapping("/setEquip")
    public Result setEquip(@RequestBody TeEquip equip) {
        equip.setUpdateTime(new Date());
        Integer res = equipService.setEquip(equip);
        return Result.success(equip);
    }


    @ApiOperation(value = "修改设备状态", notes = "修改设备状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setEquipStatus")
    public Result setEquipStatus(Long id, Integer status) {
        TeEquip equip = new TeEquip();
        equip.setId(id);
        equip.setStatus(status);
        equip.setUpdateTime(new Date());
        Integer res = equipService.setEquip(equip);
        return Result.success(equip);
    }


    @ApiOperation(value = "获取设备信息", notes = "获取设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备id", required = true)
    })
    @GetMapping("/getEquip")
    public Result getEquip(Long id) {
        TeEquip equip = equipService.getEquip(id);
        return Result.success(equip);
    }


    @ApiOperation(value = "获取设备列表", notes = "获取设备列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "type", value = "设备类型", required = false),
            @ApiImplicitParam(name = "modelId", value = "型号ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getEquipList")
    public Result getEquipList(Long customerId, Integer type, Long modelId, Integer status, String term,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = equipService.getEquipList(null, customerId, type, modelId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取设备列表带运行数据", notes = "获取设备列表带运行数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "type", value = "设备类型", required = false),
            @ApiImplicitParam(name = "modelId", value = "型号ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
    })
    @GetMapping("/getEquipListWithRunData")
    public Result getEquipListWithRunData(Long customerId, Integer type, Long modelId, Integer status, String term) {
        List<Map<String, Object>> list = equipService.getEquipListWithRunData(null, customerId, type, modelId, status, term);
        return Result.success(list);
    }


    @ApiOperation(value = "增加维护", notes = "增加维护")
    @PostMapping("/addMaintain")
    public Result addUser(@RequestBody TeMaintain maintain) {
        maintain.setTime(new Date());
        Integer res = maintainService.addMaintain(maintain);
        return Result.success(maintain);
    }


    @ApiOperation(value = "获取设备列表带维护信息", notes = "获取设备列表带维护信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "type", value = "设备类型", required = false),
            @ApiImplicitParam(name = "modelId", value = "型号ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getEquipListWithMaintain")
    public Result getEquipListWithMaintain(Long customerId, Integer type, Long modelId, Integer status, String term,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = equipService.getEquipListWithMaintain(null, customerId, type, modelId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取设备维护列表", notes = "获取设备维护列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "equipId", value = "设备ID", required = true),
            @ApiImplicitParam(name = "type", value = "维护类型", required = false),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = false),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getMaintainList")
    public Result getMaintainList(Long equipId, Integer type, String startDate, String endDate,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = maintainService.getMaintainList(equipId, type, startDate, endDate);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取设备预警列表", notes = "获取设备预警列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", required = true),
            @ApiImplicitParam(name = "anencyId", value = "代理商ID", required = false),
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = false),
            @ApiImplicitParam(name = "type", value = "设备类型", required = false),
            @ApiImplicitParam(name = "modelId", value = "型号ID", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getWarnList")
    public Result getWarnList(String date, Long anencyId, Long customerId, Integer type, Long modelId,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = warnService.getWarnList(date, null, customerId, type, modelId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取设备某次运行时的预警列表", notes = "获取设备某次运行时的预警列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "设备编码", required = true),
            @ApiImplicitParam(name = "runid", value = "运行ID", required = false),
    })
    @GetMapping("/getWarnListOfRun")
    public Result getWarnListOfRun(String code, String runid) {
        List<Map<String, Object>> list = warnService.getWarnListOfRun(code, runid);
        return Result.success(list);
    }


    @ApiOperation(value = "获取设备运行列表", notes = "获取设备运行列表-熏蒸床")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "设备编码", required = true),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = false),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getRunList")
    public Result getRunList(String code, String startDate, String endDate,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TxXzcRun> list = xzcService.getRunList(code, startDate, endDate);
        PageInfo<TxXzcRun> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
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


    @ApiOperation(value = "修改设备运行参数", notes = "修改设备运行参数")
    @PostMapping("/setEquipRunParams")
    public Result setEquipRunParams(@RequestBody TxXzcRun run) {
        Integer res = xzcService.setRun(run);
        return Result.success(res);
    }

}
