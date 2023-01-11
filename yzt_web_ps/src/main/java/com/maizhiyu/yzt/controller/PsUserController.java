package com.maizhiyu.yzt.controller;


import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IPsUserService;
import com.maizhiyu.yzt.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class PsUserController {

    @Autowired
    private IPsUserService service;

    @Resource
    private RedisUtils redisUtils;


    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = false),
    })
    @GetMapping("/getUser")
    public Result getUser(Long id) {
        PsUser user = service.getUser(id);
        return Result.success(user);
    }


    @ApiOperation(value = "验证码登录", notes = "验证码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "验证码", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
    })
    @PostMapping("/AuthCodeLogin")
    public Result AuthCodeLogin(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        //以手机号查询code进行比较
        Object o = redisUtils.get(phone);
        Assert.isNull(o, "当前手机号不存在验证码!");
        String c = String.valueOf(o);
        Assert.isTrue(c.equals(code), "验证码错误!");
        return Result.success(true);
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
