package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public Result<BuOutpatient> addOutpatient(@RequestBody BuOutpatient outpatient) {
        outpatient.setStatus(1);
        outpatient.setCreateTime(new Date());
        outpatient.setUpdateTime(outpatient.getCreateTime());
        Integer res = service.addOutpatient(outpatient);
        return Result.success(outpatient);
    }

}
