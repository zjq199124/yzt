package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.enums.CheckTypeEnum;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Api(tags = "检查接口")
@RestController
@RequestMapping("/check")
public class BuCheckController {

    @Autowired
    private IBuCheckService checkService;


    @ApiOperation(value = "获取检查信息", notes = "获取检查信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "检查ID", required = true),
    })
    @GetMapping("/getCheck")
    public Result getCheck(Long id) {
        BuCheck check = checkService.getCheck(id);

        return Result.success(check);
    }


    @ApiOperation(value = "获取检查列表(门诊)", notes = "获取检查列表(门诊)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outpatientId", value = "门诊ID", required = true),
    })
    @GetMapping("/getCheckListOfOutpatient")
    public Result getCheckListOfOutpatient(Long outpatientId) {
        List<BuCheck> list = checkService.getCheckListOfOutpatient(outpatientId);
        return Result.success(list);
    }

}
