package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.SynSyndrome;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ISynSyndromeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "辨证方案接口")
@RestController
@RequestMapping("/syndrome")
public class SynSyndromeController {

    @Autowired
    private ISynSyndromeService service;


    @ApiOperation(value = "增加辨证方案", notes = "增加辨证方案")
    @PostMapping("/addSyndrome")
    public Result addSyndrome(@RequestBody SynSyndrome syndrome) {
        syndrome.setStatus(1);
        Integer res = service.addSyndrome(syndrome);
        return Result.success(syndrome);
    }


    @ApiOperation(value = "删除辨证方案", notes = "删除辨证方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "辨证方案id", required = true)
    })
    @GetMapping("/delSyndrome")
    public Result delSyndrome(Long id) {
        Integer res = service.delSyndrome(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改辨证方案信息", notes = "修改辨证方案信息")
    @PostMapping("/setSyndrome")
    public Result setSyndrome(@RequestBody SynSyndrome syndrome) {
        Integer res = service.setSyndrome(syndrome);
        return Result.success(syndrome);
    }


    @ApiOperation(value = "修改辨证方案状态", notes = "修改辨证方案状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "辨证方案id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setSyndromeStatus")
    public Result setSyndromeStatus(Long id, Integer status) {
        SynSyndrome syndrome = new SynSyndrome();
        syndrome.setId(id);
        syndrome.setStatus(status);
        Integer res = service.setSyndrome(syndrome);
        return Result.success(syndrome);
    }


    @ApiOperation(value = "获取辨证方案信息", notes = "获取辨证方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "辨证方案id", required = true)
    })
    @GetMapping("/getSyndrome")
    public Result getSyndrome(Long id) {
        SynSyndrome syndrome = service.getSyndrome(id);
        return Result.success(syndrome);
    }


    @ApiOperation(value = "获取辨证方案列表", notes = "获取辨证方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", value = "疾病ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getSyndromeList")
    public Result getSyndromeList(Long diseaseId, Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getSyndromeList(diseaseId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
