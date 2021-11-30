package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.BuPatientScan;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuPatientScanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "患者扫码接口")
@RestController
@RequestMapping("/patientscan")
public class BuPatientScanController {

    @Autowired
    private IBuPatientScanService service;


    @ApiOperation(value = "新增患者扫码", notes = "新增患者扫码")
    @ApiImplicitParams({})
    @PostMapping("/addPatientScan")
    public Result addPatientScan(@RequestBody BuPatientScan scan) {
        scan.setTime(new Date());
        Integer res = service.addPatientScan(scan);
        return Result.success(scan);
    }


    @ApiOperation(value = "删除患者扫码", notes = "删除患者扫码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "扫码ID", required = true),
    })
    @GetMapping("/delPatientScan")
    public Result delPatientScan(Long id) {
        Integer res = service.delPatientScan(id);
        return Result.success(res);
    }


//    @ApiOperation(value = "获取患者扫码列表", notes = "获取患者扫码列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "customerId", value = "医院ID", required = true),
//            @ApiImplicitParam(name = "term", value = "搜索词", required = true),
//    })
//    @GetMapping("/getPatientScanList")
//    public Result getPatientScanList(Long customerId, String term) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<Map<String, Object>> list = service.getPatientScanList(customerId, term);
//        return Result.success(list);
//    }


    @ApiOperation(value = "获取患者扫码列表", notes = "获取患者扫码列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "医院ID", required = true),
            @ApiImplicitParam(name = "term", value = "搜索词", required = true),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getPatientScanList")
    public Result getPatientScanList(Long customerId, String term,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getPatientScanList(customerId, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }

}
