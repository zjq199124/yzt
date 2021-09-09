package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.SchChengyao;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ISchChengyaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "成药方案接口")
@RestController
@RequestMapping("/chengyao")
public class SchChengyaoController {

    @Autowired
    private ISchChengyaoService service;


    @ApiOperation(value = "增加成药方案", notes = "增加成药方案")
    @PostMapping("/addChengyao")
    public Result addChengyao(@RequestBody SchChengyao chengyao) {
        chengyao.setStatus(1);
        Integer res = service.addChengyao(chengyao);
        return Result.success(chengyao);
    }


    @ApiOperation(value = "删除成药方案", notes = "删除成药方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成药方案id", required = true)
    })
    @GetMapping("/delChengyao")
    public Result delChengyao(Long id) {
        Integer res = service.delChengyao(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改成药方案信息", notes = "修改成药方案信息")
    @PostMapping("/setChengyao")
    public Result setChengyao(@RequestBody SchChengyao chengyao) {
        Integer res = service.setChengyao(chengyao);
        return Result.success(chengyao);
    }


    @ApiOperation(value = "修改成药方案状态", notes = "修改成药方案状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成药方案id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setChengyaoStatus")
    public Result setChengyaoStatus(Long id, Integer status) {
        SchChengyao chengyao = new SchChengyao();
        chengyao.setId(id);
        chengyao.setStatus(status);
        Integer res = service.setChengyao(chengyao);
        return Result.success(chengyao);
    }


    @ApiOperation(value = "获取成药方案信息", notes = "获取成药方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成药方案id", required = true)
    })
    @GetMapping("/getChengyao")
    public Result getChengyao(Long id) {
        SchChengyao chengyao = service.getChengyao(id);
        return Result.success(chengyao);
    }


    @ApiOperation(value = "获取成药方案列表", notes = "获取成药方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", value = "疾病ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getChengyaoList")
    public Result getChengyaoList(Long diseaseId, Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getChengyaoList(diseaseId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
