package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.DictSymptom;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IDictSymptomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "症状接口")
@RestController
@RequestMapping("/dict")
public class DictSymptomController {

    @Autowired
    private IDictSymptomService service;

    @ApiOperation(value = "增加症状", notes = "增加症状")
    @PostMapping("/addSymptom")
    public Result addSymptom(@RequestBody DictSymptom symptom) {
        symptom.setStatus(1);
        Integer res = service.addSymptom(symptom);
        return Result.success(symptom);
    }


    @ApiOperation(value = "删除症状", notes = "删除症状")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "症状id", required = true)
    })
    @GetMapping("/delSymptom")
    public Result delSymptom(Long id) {
        Integer res = service.delSymptom(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改症状信息", notes = "修改症状信息")
    @PostMapping("/setSymptom")
    public Result setSymptom(@RequestBody DictSymptom symptom) {
        Integer res = service.setSymptom(symptom);
        return Result.success();
    }


    @ApiOperation(value = "修改症状状态", notes = "修改症状状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "症状id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setSymptomStatus")
    public Result setSymptomStatus(Long id, Integer status) {
        DictSymptom symptom = new DictSymptom();
        symptom.setId(id);
        symptom.setStatus(status);
        service.setSymptom(symptom);
        return Result.success(symptom);
    }


    @ApiOperation(value = "获取症状信息", notes = "获取症状信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "症状id", required = true)
    })
    @GetMapping("/getSymptom")
    public Result getSymptom(Long id) {
        DictSymptom symptom = service.getSymptom(id);
        return Result.success(symptom);
    }


    @ApiOperation(value = "获取症状列表", notes = "获取症状列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getSymptomList")
    public Result getSymptomList(Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getSymptomList(status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
