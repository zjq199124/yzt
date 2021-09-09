package com.maizhiyu.yzt.controller;


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


    @ApiOperation(value = "增加规范项", notes = "增加规范项")
    @PostMapping("/addStandard")
    public Result addStandard(@RequestBody TsStandard standard) {
        service.addStandard(standard);
        return Result.success(standard);
    }


    @ApiOperation(value = "删除规范项", notes = "删除规范项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "规范ID", required = true)
    })
    @GetMapping("/delStandard")
    public Result delStandard(Long id) {
        Integer res = service.delStandard(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改适宜技术信息", notes = "修改适宜技术信息")
    @PostMapping("/setStandard")
    public Result setStandard(@RequestBody TsStandard standard) {
        Integer res = service.setStandard(standard);
        return Result.success(res);
    }


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
