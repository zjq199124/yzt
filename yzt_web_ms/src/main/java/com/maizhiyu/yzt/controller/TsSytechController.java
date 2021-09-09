package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.TsSytech;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITsSytechService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Api(tags = "适宜技术接口")
@RestController
@RequestMapping("/sytech")
public class TsSytechController {

    @Autowired
    private ITsSytechService service;


    @ApiOperation(value = "增加适宜技术", notes = "增加适宜技术")
    @PostMapping("/addSytech")
    public Result addSytech(@RequestBody TsSytech sytech) {
        Date date = new Date();
        sytech.setStatus(1);
        sytech.setUpdateTime(date);
        sytech.setCreateTime(date);
        service.addSytech(sytech);
        return Result.success(sytech);
    }


    @ApiOperation(value = "删除适宜技术", notes = "删除适宜技术")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜技术id", required = true)
    })
    @GetMapping("/delSytech")
    public Result delSytech(Integer id) {
        Integer res = service.delSytech(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改适宜技术信息", notes = "修改适宜技术信息")
    @PostMapping("/setSytech")
    public Result setSytech(@RequestBody TsSytech sytech) {
        sytech.setUpdateTime(new Date());
        Integer res = service.setSytech(sytech);
        return Result.success(sytech);
    }


    @ApiOperation(value = "修改适宜技术状态", notes = "修改适宜技术状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜技术id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setSytechStatus")
    public Result setSytechStatus(Long id, Integer status) {
        TsSytech sytech = new TsSytech();
        sytech.setId(id);
        sytech.setStatus(status);
        sytech.setUpdateTime(new Date());
        service.setSytech(sytech);
        return Result.success();
    }


    @ApiOperation(value = "获取适宜技术信息", notes = "获取适宜技术信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜技术id", required = true)
    })
    @GetMapping("/getSytech")
    public Result getSytech(Integer id) {
        TsSytech sytech = service.getSytech(id);
        return Result.success(sytech);
    }


    @ApiOperation(value = "获取适宜技术列表", notes = "获取适宜技术列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getSytechList")
    public Result getSytechList(Integer status, String term,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TsSytech> list = service.getSytechList(status, term);
        PageInfo<TsSytech> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
