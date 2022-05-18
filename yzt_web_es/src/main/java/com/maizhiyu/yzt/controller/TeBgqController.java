package com.maizhiyu.yzt.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maizhiyu.yzt.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "拔罐器接口")
@RestController
@RequestMapping("/bgq")
public class TeBgqController {


    @ApiOperation(value = "数据接口", notes = "数据接口")
    @ApiImplicitParams({})
    @PostMapping(value = "/data", consumes = "application/octet-stream")
    public Result data(@RequestBody String string) {
        System.out.println("##### " + string);
        JSONObject jsonObject = JSON.parseObject(string);
        return Result.success();
    }

}
