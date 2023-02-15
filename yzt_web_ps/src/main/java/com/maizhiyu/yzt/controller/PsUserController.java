package com.maizhiyu.yzt.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maizhiyu.yzt.entity.PsFamily;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.enums.SmsSceneEnum;
import com.maizhiyu.yzt.enums.SmsTemplateEnum;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.result.SuccessBusinessCode;
import com.maizhiyu.yzt.service.IPsFamilyService;
import com.maizhiyu.yzt.service.IPsUserService;
import com.maizhiyu.yzt.service.ISmsService;
import com.maizhiyu.yzt.serviceimpl.PsUserService;
import com.maizhiyu.yzt.utils.IdcardToAge;
import com.maizhiyu.yzt.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;


@Api(tags = "用户接口")
@RestController
@Slf4j
@RequestMapping("/user")
public class PsUserController {

    @Value("${sms.signName}")
    private String signName;

    @Autowired
    private IPsUserService service;

    @Resource
    private PsUserService psUserService;

    @Resource
    private IPsFamilyService psFamilyService;

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
        PsUser user = psUserService.getById(id);
        user.setAge(IdcardToAge.getAge(user.getIdCard()));
        return Result.success(user);
    }


    @ApiOperation(value = "验证码登录", notes = "验证码登录")
    @ApiImplicitParam(name = "map", value = "验证参数map对象包含有 phone,code,openId 三个key", required = false)
    @PostMapping("/AuthCodeLogin")
    public Result AuthCodeLogin(@RequestBody Map<String,String> map) throws Exception {
        Assert.notNull(map.get("phone"), "phone不能为空!");
        //1：以手机号phone查询code进行比较
        Object o = redisUtils.get(SmsSceneEnum.LOGIN_PREFIX.getCode() + "_" + map.get("phone"));
        Assert.notNull(o, "验证码已过期!");
        String c = String.valueOf(o);
        Assert.isTrue(c.equals(map.get("code")), "验证码错误!");
        //2：以手机号查询账户信息
        PsUser psUser = service.getUserByPhone(map.get("phone"));
        if (Objects.isNull(psUser)) {
            //3:没有账户信息就要创建账户信息
            psUser = createPsUser(map.get("phone"));
        }

        //4:此时还没有完善资料，要继续完善个人资料
        if (psUser.getIsCompleteDetail() == 0) {
           return Result.success(psUser, SuccessBusinessCode.PS_COMPLETE_MESSAGE);
        }
        return Result.success(psUser);
    }

    private PsUser createPsUser(String phone) {
        PsUser psUser = new PsUser();
        psUser.setPhone(phone);
        psUser.setStatus(1);
        psUser.setIsCompleteDetail(0);
        psUser.setCreateTime(new Date());
        psUser.setUpdateTime(psUser.getCreateTime());
        service.save(psUser);
        return psUser;
    }


    @ApiOperation(value = "设置用户信息", notes = "设置用户信息")
    @ApiImplicitParams({})
    @PostMapping("/setUser")
    public Result setUser(@RequestBody PsUser user) {
        if (user.getPhone() != null) {
            if (user.getPhone().length() != 11) {
                return Result.failure("电话号码不符合位数要求");
            }
        }
        if (user.getIdCard() != null) {
            if (user.getIdCard().length() != 18) {
                return Result.failure("身份证号码不符合位数要求");
            }
        }

        Boolean res = psUserService.setUser(user);
        return Result.success(res);
    }

    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true)
    @GetMapping("/sendVerificationCode")
    public Result sendVerificationCode(@RequestParam("phone") String phone) {
        Map<String, String> map = new HashMap<>();
        String verificationCode = getVerificationCode();
        map.put("code", verificationCode);
        String result = null;
        try {
            Boolean res = smsService.sendSms(signName,SmsTemplateEnum.VERIFICATION_CODE.getCode(), phone, map);
            if (res) {
                boolean ret = redisUtils.set(SmsSceneEnum.LOGIN_PREFIX.getCode() + "_" + phone, verificationCode, 120);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送验证码失败 " + e.getMessage());
        }
        return Result.success(result);
    }

    private String getVerificationCode() {
        Random randObj = new Random();
        String verificationCode = Integer.toString(100000 + randObj.nextInt(900000));
        return verificationCode;
    }
}
