package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录接口")
@RestController
public class MsLoginController {


    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
    })
    @PostMapping("/login")
    public void login(String username, String password) {
        // 这里面不需要写任何代码，单纯为了在swagger页面上可以展示出此接口
        // 并且代码也完全不会执行到这里，已经被security拦截了
        // 真正的逻辑由UserDeatilsService处理
    }


    @ApiOperation(value = "登出", notes = "登出")
    @ApiImplicitParams({})
    @PostMapping("/logout")
    public void logout() {
        // 这里面不需要写任何代码，单纯为了在swagger页面上可以展示出此接口
        // 并且代码也完全不会执行到这里，已经被security拦截了
    }


    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @GetMapping("/getCode")
    public Result getCode() {
        return Result.success();
    }


    @ApiOperation(value = "申请找回密码", notes = "申请找回密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", required = true)
    })
    @GetMapping("/applyPassword")
    public Result applyPassword(String username, String phone, String code) {
        return Result.success();
    }


    @ApiOperation(value = "执行找回密码", notes = "执行找回密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", required = true)
    })
    @GetMapping("/findPassword")
    public Result findPassword(String username, String phone, String code) {
        return Result.success();
    }

}
