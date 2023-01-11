package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.BuSignature;
import com.maizhiyu.yzt.enums.SmsTemplateEnum;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.ro.WaitTreatmentRo;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.BuSignatureService;
import com.maizhiyu.yzt.service.IBuPrescriptionItemService;
import com.maizhiyu.yzt.service.ISmsService;
import com.maizhiyu.yzt.serviceimpl.MsCustomerService;
import com.maizhiyu.yzt.utils.sms.SendSmsUtil;
import com.maizhiyu.yzt.vo.WaitSignatureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "治疗签到接口")
@Slf4j
@RestController
@RequestMapping("/signature")
public class BuSignatureController extends BaseController {

    @Resource
    private IBuPrescriptionItemService buPrescriptionItemService;

    @Resource
    private BuSignatureService buSignatureService;

    @Resource
    private MsCustomerService msCustomerService;

    @Resource
    private ISmsService smsService;

    @ApiOperation("待签到列表")
    @PostMapping("/waitSignatureList")
    public Result<Page<WaitSignatureVo>> waitSignatureList(@RequestBody WaitSignatureRo waitSignatureRo) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        waitSignatureRo.setCustomerId(customerId);
        Page<WaitSignatureVo> page = buPrescriptionItemService.selectWaitSignatureList(waitSignatureRo);
        return Result.success(page);
    }


    @ApiOperation("签到")
    @PostMapping("/signature")
    public Result<Boolean> signature(@RequestBody BuSignature buSignature) {
        HsUserDetails hsUserDetails = getHsUserDetails();
        buSignature.setCustomerId(hsUserDetails.getCustomerId());
        Boolean result = buSignatureService.addSignature(buSignature);
        return Result.success(result);
    }

    @ApiOperation("待治疗列表")
    @PostMapping("/waitTreatmentList")
    public Result<Page<BuSignature>> waitTreatmentList(@RequestBody WaitTreatmentRo waitTreatmentRo) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        waitTreatmentRo.setCustomerId(customerId);
        Page<BuSignature> pageResult = buSignatureService.waitTreatmentList(waitTreatmentRo);
        return Result.success(pageResult);
    }

    @ApiOperation("短信提醒")
    @PostMapping("/smsNotify")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "患者姓名", required = true),
            @ApiImplicitParam(name = "phone",value = "手机号码", required = true),
            @ApiImplicitParam(name = "tsName",value = "适宜技术名称", required = true)
    })
    public Result<String> smsNotify(String name,String phone,String tsName) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        Map<String, Object> customer = msCustomerService.getCustomer(customerId);
        Map<String, String> map = new HashMap<>();
        map.put("code", "1234");
        String result = null;
        try {
            result = smsService.sendSms(SmsTemplateEnum.VERIFICATION_CODE.getCode(), phone, map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送短信提醒失败 "  + e.getMessage());
        }
        return Result.success(result);
    }
}























