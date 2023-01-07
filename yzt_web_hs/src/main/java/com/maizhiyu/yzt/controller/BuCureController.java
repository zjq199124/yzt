package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.BuCure;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.BuCureRO;
import com.maizhiyu.yzt.ro.BuCureSearchRO;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.BuCureService;
import com.maizhiyu.yzt.vo.BuCureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 治疗表(BuCure)表服务控制层
 *
 * @author liuyzh
 * @since 2022-12-19 18:34:06
 */
@Api(tags = "治疗表(BuCure)")
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("buCure")
public class BuCureController extends BaseController {

    @Resource
    private final BuCureService buCureService;

    @ApiOperation("开始或编辑治疗接口")
    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody BuCureRO buCureRO) {
        Assert.notNull(buCureRO.getSignatureId(), "签到id不能为空!");
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        buCureRO.setCustomerId(customerId);
        BuCure buCure = new BuCure();
        BeanUtils.copyProperties(buCureRO, buCure);
        boolean res = buCureService.saveOrUpdate(buCure);
        return Result.success(res);
    }

    @ApiOperation("结束治疗")
    @PostMapping("/endTreatment")
    public Result<Boolean> endTreatment(Long id) {
        Assert.notNull(id, "治疗id不能为空!");
        boolean res = buCureService.endTreatment(id);
        return Result.success(res);
    }


    @ApiOperation("治疗列表接口")
    @PostMapping("/treatmentList")
    public Result<Page<BuCure>> treatmentList(@RequestBody BuCureSearchRO buCureSearchRO) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        buCureSearchRO.setCustomerId(customerId);
        Page<BuCure> resultPage = buCureService.treatmentList(buCureSearchRO);
        return Result.success(resultPage);
    }

    @ApiOperation("治疗记录详情")
    @GetMapping("/treatmentRecordDetail")
    @ApiImplicitParam(name = "signatureId", value = "签到id")
    public Result<BuCureVo> selectCureDetailBySignatureId(Long signatureId) {
        BuCureVo buCureVo = buCureService.selectCureDetailBySignatureId(signatureId);
        return Result.success(buCureVo);
    }
}






















