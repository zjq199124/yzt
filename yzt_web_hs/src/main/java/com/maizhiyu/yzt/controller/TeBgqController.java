package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TeBgq;
import com.maizhiyu.yzt.entity.TxBgqRun;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITeBgqService;
import com.maizhiyu.yzt.service.ITxBgqRunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Api(tags = "拔罐器接口")
@RestController
@RequestMapping("/bgq")
public class TeBgqController {

    @Autowired
    private ITeBgqService bgqService;

    @Autowired
    private ITxBgqRunService bgqRunService;


    @ApiOperation(value = "修改拔罐器信息", notes = "修改拔罐器信息")
    @PostMapping("/setBgq")
    public Result setBgq(@RequestBody TeBgq bgq) {
        bgq.setUpdateTime(new Date());
        Integer res = bgqService.setBgq(bgq);
        return Result.success(bgq);
    }


    @ApiOperation(value = "修改拔罐器状态", notes = "修改拔罐器状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "拔罐器id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setBgqStatus")
    public Result setBgqStatus(Long id, Integer status) {
        TeBgq bgq = new TeBgq();
        bgq.setId(id);
        bgq.setStatus(status);
        bgq.setUpdateTime(new Date());
        Integer res = bgqService.setBgq(bgq);
        return Result.success(bgq);
    }


    @ApiOperation(value = "获取拔罐器信息", notes = "获取拔罐器信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "拔罐器id", required = true)
    })
    @GetMapping("/getBgq")
    public Result getBgq(Long id) {
        TeBgq bgq = bgqService.getBgq(id);
        return Result.success(bgq);
    }


    @ApiOperation(value = "获取拔罐器列表", notes = "获取拔罐器列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getBgqList")
    public Result getBgqList(Long customerId, Integer status, String term,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<TeBgq> list = bgqService.getBgqList(new Page(pageNum, pageSize), null, customerId, status, term);
        return Result.success(list);
    }


    @ApiOperation(value = "获取拔罐器运行列表", notes = "获取拔罐器运行列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "拔罐器编码", required = true),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = false),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getBgqRunList")
    public Result getBgqRunList(String code, String startDate, String endDate,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<TxBgqRun> list = bgqRunService.getBgqRunList(new Page(pageNum, pageSize), code, startDate, endDate);
        return Result.success(list);
    }

}
