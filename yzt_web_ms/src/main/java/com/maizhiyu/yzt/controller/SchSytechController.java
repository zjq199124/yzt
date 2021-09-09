package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.SchSytech;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ISchSytechService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "适宜方案接口")
@RestController
@RequestMapping("/schtech")
public class SchSytechController {

    @Autowired
    private ISchSytechService service;


    @ApiOperation(value = "增加适宜方案", notes = "增加适宜方案")
    @PostMapping("/addSytech")
    public Result addSytech(@RequestBody SchSytech sytech) {
        sytech.setStatus(1);
        Integer res = service.addSytech(sytech);
        return Result.success(sytech);
    }


    @ApiOperation(value = "删除适宜方案", notes = "删除适宜方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜方案id", required = true)
    })
    @GetMapping("/delSytech")
    public Result delSytech(Long id) {
        Integer res = service.delSytech(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改适宜方案信息", notes = "修改适宜方案信息")
    @PostMapping("/setSytech")
    public Result setSytech(@RequestBody SchSytech sytech) {
        Integer res = service.setSytech(sytech);
        return Result.success(sytech);
    }


    @ApiOperation(value = "修改适宜方案状态", notes = "修改适宜方案状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜方案id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setSytechStatus")
    public Result setSytechStatus(Long id, Integer status) {
        SchSytech sytech = new SchSytech();
        sytech.setId(id);
        sytech.setStatus(status);
        Integer res = service.setSytech(sytech);
        return Result.success(sytech);
    }


    @ApiOperation(value = "获取适宜方案信息", notes = "获取适宜方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜方案id", required = true)
    })
    @GetMapping("/getSytech")
    public Result getSytech(Long id) {
        SchSytech sytech = service.getSytech(id);
        return Result.success(sytech);
    }


    @ApiOperation(value = "获取适宜方案列表", notes = "获取适宜方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sytechId", value = "适宜技术ID", required = false),
            @ApiImplicitParam(name = "diseaseId", value = "疾病ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getSytechList")
    public Result getSytechList(Long sytechId, Long diseaseId, Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getSytechList(sytechId, diseaseId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
