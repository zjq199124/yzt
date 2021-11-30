package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.TeModel;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITeModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "型号接口")
@RestController
@RequestMapping("/model")
public class TeModelController {

    @Autowired
    private ITeModelService service;


    @ApiOperation(value = "获取型号信息", notes = "获取型号信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "型号id", required = true)
    })
    @GetMapping("/getModel")
    public Result getModel(Long id) {
        TeModel model = service.getModel(id);
        return Result.success(model);
    }


    @ApiOperation(value = "获取型号列表", notes = "获取型号列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "type", value = "设备类型", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getModelList")
    public Result getModelList(Integer status, Integer type, String term,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getModelList(status, type, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
