package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.TsStandard;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITsStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "规范接口")
@RestController
@RequestMapping("/standard")
public class TsStandardController {

    @Autowired
    private ITsStandardService service;


    @ApiOperation(value = "获取规范信息", notes = "获取规范信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "规范id", required = true)
    })
    @GetMapping("/getStandard")
    public Result getStandard(Long id) {
        TsStandard fault = service.getStandard(id);
        return Result.success(fault);
    }


    @ApiOperation(value = "获取规范列表", notes = "获取规范列表")
    @ApiImplicitParams({})
    @GetMapping("/getStandardList")
    public Result getStandardList() {
        List<Map<String, Object>> list = service.getStandardList();
        return Result.success(list);
    }
}
