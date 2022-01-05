package com.maizhiyu.yzt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "方案接口")
@RestController
@RequestMapping("/scheme")
public class BuSchemeController extends BaseController {

    @Autowired
    private ISchZhongyaoService zhongyaoService;

    @Autowired
    private ISchChengyaoService chengyaoService;

    @Autowired
    private ISchXiedingService xiedingService;

    @Autowired
    private ISchSytechService sytechService;


    @ApiOperation(value = "获取中药方案列表", notes = "获取中药方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
            @ApiImplicitParam(value = "鉴权token",name = "token",paramType  = "header", dataType = "String", required=true)
    })
    @GetMapping("/getZhongyaoSchemeList")
    public Result getZhongyaoSchemeList(String term,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {

        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = zhongyaoService.getZhongyaoList2(null, null, term,customerId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取成药方案列表", notes = "获取成药方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getChengyaoSchemeList")
    public Result getChengyaoSchemeList(String term,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = chengyaoService.getChengyaoList(null, null, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取协定方案列表", notes = "获取协定方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getXiedingSchemeList")
    public Result getXiedingSchemeList(String term,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = xiedingService.getXiedingList(null, null, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取适宜方案列表", notes = "获取适宜方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getSytechSchemeList")
    public Result getSytechSchemeList(String term,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = sytechService.getSytechList(null,null, null, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }
}
