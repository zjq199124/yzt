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
            @ApiImplicitParam(name = "diseaseName", value = "疾病名称", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getSyndromeList")
    public Result getSyndromeList(
            String diseaseName, Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getSyndromeList(diseaseName, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
