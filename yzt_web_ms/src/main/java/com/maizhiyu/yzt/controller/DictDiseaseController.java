package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.DictDisease;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IDictDiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "疾病接口")
@RestController
@RequestMapping("/dict")
public class DictDiseaseController {

    @Autowired
    private IDictDiseaseService service;


    @ApiOperation(value = "增加疾病", notes = "增加疾病")
    @PostMapping("/addDisease")
    public Result addDisease(@RequestBody DictDisease disease) {
        disease.setStatus(1);
        Integer res = service.addDisease(disease);
        return Result.success(disease);
    }


    @ApiOperation(value = "删除疾病", notes = "删除疾病")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "疾病id", required = true)
    })
    @GetMapping("/delDisease")
    public Result delDisease(Long id) {
        Integer res = service.delDisease(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改疾病信息", notes = "修改疾病信息")
    @PostMapping("/setDisease")
    public Result setDisease(@RequestBody DictDisease disease) {
        Integer res = service.setDisease(disease);
        return Result.success(disease);
    }


    @ApiOperation(value = "修改疾病状态", notes = "修改疾病状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "疾病id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setDiseaseStatus")
    public Result setDiseaseStatus(Long id, Integer status) {
        DictDisease disease = new DictDisease();
        disease.setId(id);
        disease.setStatus(status);
        Integer res = service.setDisease(disease);
        return Result.success(disease);
    }


    @ApiOperation(value = "获取疾病信息", notes = "获取疾病信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "疾病id", required = true)
    })
    @GetMapping("/getDisease")
    public Result getDisease(Long id) {
        DictDisease disease = service.getDisease(id);
        return Result.success(disease);
    }


    @ApiOperation(value = "获取疾病列表", notes = "获取疾病列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getDiseaseList")
    public Result getDiseaseList(Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getDiseaseList(status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
