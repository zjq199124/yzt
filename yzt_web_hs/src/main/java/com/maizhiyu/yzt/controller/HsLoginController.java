package com.maizhiyu.yzt.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录接口")
@RestController
public class HsLoginController {

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
}
