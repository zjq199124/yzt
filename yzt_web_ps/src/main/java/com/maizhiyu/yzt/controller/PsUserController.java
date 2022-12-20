package com.maizhiyu.yzt.controller;


import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IPsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class PsUserController {

    @Autowired
    private IPsUserService service;


    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true),
    })
    @GetMapping("/getUser")
    public Result getUser(Long id) {
        PsUser user = service.getUser(id);
        return Result.success(user);
    }


    @ApiOperation(value = "设置用户信息", notes = "设置用户信息")
    @ApiImplicitParams({})
    @PostMapping("/setUser")
    public Result setUser(@RequestBody PsUser user) {
        user.setUpdateTime(new Date());
        Integer res = service.setUser(user);
        return Result.success(user);
    }
}
