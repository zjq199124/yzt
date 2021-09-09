package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.TsSytech;
import com.maizhiyu.yzt.entity.TsSytechItem;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITsSytechItemService;
import com.maizhiyu.yzt.service.ITsSytechService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "适宜技术考核项目接口")
@RestController
@RequestMapping("/sytechItem")
public class TsSytechItemController {

    @Autowired
    private ITsSytechItemService service;


    @ApiOperation(value = "增加考核项目", notes = "增加考核项目")
    @PostMapping("/addItem")
    public Result addItem(@RequestBody TsSytechItem item) {
        Integer res = service.addSytechItem(item);
        return Result.success(item);
    }


    @ApiOperation(value = "删除考核项目", notes = "删除考核项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考核项目id", required = true)
    })
    @GetMapping("/delItem")
    public Result delItem(Long id) {
        Integer res = service.delSytechItem(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改考核项目信息", notes = "修改考核项目信息")
    @PostMapping("/setItem")
    public Result setItem(@RequestBody TsSytechItem item) {
        Integer res = service.setSytechItem(item);
        return Result.success(item);
    }


    @ApiOperation(value = "获取考核项目信息", notes = "获取考核项目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考核项目id", required = true)
    })
    @GetMapping("/getItem")
    public Result getItem(Long id) {
        TsSytechItem item = service.getSytechItem(id);
        return Result.success(item);
    }


    @ApiOperation(value = "获取考核项目列表", notes = "获取考核项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sytechId", value = "适宜技术ID", required = true),
    })
    @GetMapping("/getItemList")
    public Result getItemList(Long sytechId) {
        List<TsSytechItem> list = service.getSytechItemList(sytechId);
        return Result.success(list);
    }

}
