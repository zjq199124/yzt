package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.MsAgency;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IMsAgencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "代理商接口")
@RestController
@RequestMapping("/agency")
public class MsAgencyController {

    @Autowired
    private IMsAgencyService service;


    @ApiOperation(value = "增加代理商", notes = "增加代理商")
    @PostMapping("/addAgency")
    public Result addAgency(@RequestBody MsAgency agency) {
        agency.setStatus(1);
        agency.setCreateTime(new Date());
        agency.setUpdateTime(agency.getCreateTime());
        Integer res = service.addAgency(agency);
        return Result.success(agency);
    }


    @ApiOperation(value = "删除代理商", notes = "删除代理商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "代理商id", required = true)
    })
    @GetMapping("/delAgency")
    public Result delAgency(Long id) {
        Integer res = service.delAgency(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改代理商信息", notes = "修改代理商信息")
    @PostMapping("/setAgency")
    public Result setAgency(@RequestBody MsAgency agency) {
        agency.setUpdateTime(new Date());
        Integer res = service.setAgency(agency);
        return Result.success(agency);
    }


    @ApiOperation(value = "修改代理商状态", notes = "修改代理商状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "代理商id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setAgencyStatus")
    public Result setAgencyStatus(Long id, Integer status) {
        MsAgency agency = new MsAgency();
        agency.setId(id);
        agency.setStatus(status);
        agency.setUpdateTime(new Date());
        service.setAgency(agency);
        return Result.success(agency);
    }


    @ApiOperation(value = "获取代理商信息", notes = "获取代理商信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "代理商id", required = true)
    })
    @GetMapping("/getAgency")
    public Result getAgency(Long id) {
        MsAgency agency = service.getAgency(id);
        return Result.success(agency);
    }


    @ApiOperation(value = "获取代理商列表", notes = "获取代理商列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getAgencyList")
    public Result getAgencyList(Integer status, String term,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getAgencyList(status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
