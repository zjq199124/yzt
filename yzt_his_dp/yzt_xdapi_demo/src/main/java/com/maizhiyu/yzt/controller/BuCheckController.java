package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "检查接口")
@RestController
@RequestMapping("/check")
public class BuCheckController {
    @Autowired
    private FeignYptClient yptClient;

    @ApiOperation(value = "获取检查列表(门诊)", notes = "获取检查列表(门诊)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "门诊ID", required = true),
    })
    @GetMapping("/getCheckListOfOutpatient")
    public Result getCheckListOfOutpatient(String phone) {
        return  yptClient.getCheckListOfOutpatient(phone);
    }

}
