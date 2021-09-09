package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.TsSytech;
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
