package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuOutpatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


@Api(tags = "门诊预约接口：业务上暂时不需要")
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


    @ApiOperation(value = "获取用户门诊预约列表", notes = "获取用户门诊预约列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "patientId", value = "患者ID", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getUserOutpatientList")
    public Result getUserOutpatientList(Long userId, Long patientId,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Map<String, Object>> list = service.getPsUserOutpatientList(new Page(pageNum, pageSize), userId, patientId, null, null);
        return Result.success(list);
    }

}
