package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.TsAssess;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITsAssessService;
import com.maizhiyu.yzt.utils.MyDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "考核接口")
@RestController
@RequestMapping("/assess")
public class TsAssessController {

    @Autowired
    private ITsAssessService service;


    @ApiOperation(value = "增加考核", notes = "增加考核")
    @PostMapping("/addAssess")
    public Result addAssess(@RequestBody TsAssess assess) {
        assess.setStatus(0);
        assess.setTime(new Date());
        Integer res = service.addAssess(assess);
        return Result.success(assess);
    }


    @ApiOperation(value = "修改考核信息", notes = "修改考核信息")
    @PostMapping("/setAssess")
    public Result setUser(@RequestBody TsAssess assess) {
        Integer res = service.setAssess(assess);
        return Result.success(assess);
    }


    @ApiOperation(value = "获取考核信息", notes = "获取考核信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考核id", required = true)
    })
    @GetMapping("/getAssess")
    public Result getAssess(Long id) {
        TsAssess fault = service.getAssess(id);
        return Result.success(fault);
    }


    @ApiOperation(value = "获取考核列表", notes = "获取考核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户id", required = true),
            @ApiImplicitParam(name = "sytechId", value = "技术id", required = true),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = false),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getAssessList")
    public Result getAssessList(Long customerId, Long sytechId, String startDate, String endDate, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        if (startDate == null || startDate.length() == 0) {
            startDate = MyDate.getTodayStr();
        }
        if (endDate == null || endDate.length() == 0) {
            endDate = MyDate.getTomorrowStr();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getAssessList(customerId, sytechId, startDate, endDate, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }
}
