package com.maizhiyu.yzt.controller;


import com.maizhiyu.yzt.entity.TeTrouble;
import com.maizhiyu.yzt.entity.TeTrouble;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ITeTroubleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "问题接口")
@RestController
@RequestMapping("/trouble")
public class TeTroubleController {

    @Autowired
    private ITeTroubleService service;


    @ApiOperation(value = "增加问题", notes = "增加问题")
    @PostMapping("/addTrouble")
    public Result addTrouble(@RequestBody TeTrouble trouble) {
        Integer res = service.addTrouble(trouble);
        return Result.success(trouble);
    }


    @ApiOperation(value = "删除问题", notes = "删除问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题id", required = true)
    })
    @PostMapping("/delTrouble")
    public Result getTroubleList(Long id) {
        Integer res = service.delTrouble(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改问题", notes = "修改问题")
    @PostMapping("/setTrouble")
    public Result setTrouble(@RequestBody TeTrouble trouble) {
        Integer res = service.setTrouble(trouble);
        return Result.success(res);
    }


    @ApiOperation(value = "获取问题详情", notes = "获取问题详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题id", required = true)
    })
    @GetMapping("/getTrouble")
    public Result getTrouble(Long id) {
        TeTrouble trouble = service.getTrouble(id);
        return Result.success(trouble);
    }


    @ApiOperation(value = "获取问题列表", notes = "获取问题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "etype", value = "设备类型", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
    })
    @GetMapping("/getTroubleList")
    public Result getTroubleList(Integer status, Integer etype) {
        List<Map<String, Object>> list = service.getTroubleList(etype, status);
        return Result.success(list);
    }
}
