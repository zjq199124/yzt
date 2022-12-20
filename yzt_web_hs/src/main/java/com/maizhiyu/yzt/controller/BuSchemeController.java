package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.*;
import com.maizhiyu.yzt.vo.CustomerHerbsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private IHsCustomerHerbsService customerHerbsService;

    @ApiOperation(value = "获取药材列表", notes = "获取药材列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "herbsName", value = "药材名称", required = false),
//            @ApiImplicitParam(value = "鉴权token",name = "token",paramType  = "header", dataType = "String", required=true)
    })
    @GetMapping("/getHsCustomerHerbsList")
    public Result getMsHerbsList(String herbsName) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        IPage<CustomerHerbsVO> paperList = customerHerbsService.getHsCustomerHerbsList(customerId, herbsName, 1, 999999);
        return Result.success(paperList.getRecords());
    }


    @ApiOperation(value = "获取中药方案列表", notes = "获取中药方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
//            @ApiImplicitParam(value = "鉴权token",name = "token",paramType  = "header", dataType = "String", required=true)
    })
    @GetMapping("/getZhongyaoSchemeList")
    public Result getZhongyaoSchemeList(String term,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {

//        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        IPage<Map<String, Object>> list = zhongyaoService.getZhongyaoList(new Page(pageNum, pageSize), null, null, term);
        return Result.success(list);
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
        IPage<Map<String, Object>> list = chengyaoService.getChengyaoList(new Page(pageNum, pageSize), null, null, term);
        return Result.success(list);
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
        IPage<Map<String, Object>> list = xiedingService.getXiedingList(new Page(pageNum, pageSize), null, null, term);
        return Result.success(list);
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
        IPage<Map<String, Object>> list = sytechService.getSytechList(new Page(pageNum, pageSize), null, null, null, term);
        return Result.success(list);
    }
}
