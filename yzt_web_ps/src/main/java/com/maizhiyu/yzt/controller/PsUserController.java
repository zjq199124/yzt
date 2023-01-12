package com.maizhiyu.yzt.controller;


import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.enums.SmsSceneEnum;
import com.maizhiyu.yzt.enums.SmsTemplateEnum;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IPsUserService;
import com.maizhiyu.yzt.service.ISmsService;
import com.maizhiyu.yzt.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Api(tags = "用户接口")
@RestController
@Slf4j
@RequestMapping("/user")
public class PsUserController {

    @Autowired
    private IPsUserService service;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISmsService smsService;


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
        Object o = redisUtils.get(SmsSceneEnum.LOGIN_PREFIX.getCode() + "_" + phone);
        Assert.notNull(o, "当前手机号不存在验证码!");
        String c = String.valueOf(o);
        Assert.isTrue(c.equals(code), "验证码错误!");
        PsUser psUser = service.getUserByPhone(phone);
        return Result.success(psUser);
    }


    @ApiOperation(value = "设置用户信息", notes = "设置用户信息")
    @ApiImplicitParams({})
    @PostMapping("/setUser")
    public Result setUser(@RequestBody PsUser user) {
            user.setUpdateTime(new Date());
        Integer res = service.setUser(user);
        return Result.success(user);
    }

    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true)
    @GetMapping("/sendVerificationCode")
    public Result sendVerificationCode(@RequestParam("phone") String phone) {
        Map<String, String> map = new HashMap<>();
        String verificationCode =  getVerificationCode();
        map.put("code", verificationCode);
        String result = null;
        try {
            Boolean res = smsService.sendSms(SmsTemplateEnum.VERIFICATION_CODE.getCode(), phone, map);
            if (res) {
                redisUtils.set(SmsSceneEnum.LOGIN_PREFIX.getCode() + "_" + phone, verificationCode, 120);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送验证码失败 "  + e.getMessage());
        }
        return Result.success(result);
    }

    private String getVerificationCode() {
        Random randObj = new Random();
        String verificationCode = Integer.toString(100000 + randObj.nextInt(900000));
        return verificationCode;
    }
}
