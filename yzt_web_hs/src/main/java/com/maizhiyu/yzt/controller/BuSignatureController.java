package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.BuSignature;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.ro.WaitTreatmentRo;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.BuSignatureService;
import com.maizhiyu.yzt.service.IBuPrescriptionItemService;
import com.maizhiyu.yzt.vo.WaitSignatureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Api(tags = "治疗签到接口")
@RestController
@RequestMapping("/signature")
public class BuSignatureController extends BaseController {

    @Resource
    private IBuPrescriptionItemService buPrescriptionItemService;

    @Resource
    private BuSignatureService buSignatureService;

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
        buSignature.setRegistrantId(hsUserDetails.getId());
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
}























