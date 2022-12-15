package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.MsCustomer;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.BuDiagnoseRO;
import com.maizhiyu.yzt.service.*;
import com.maizhiyu.yzt.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Api(tags = "诊断接口")
@RestController
@RequestMapping("/diagnose")
public class BuDiagnoseController {

    @Autowired
    private IBuMedicantService medicantService;

    @Autowired
    private IBuRecommendService recommendService;

    @Resource
    private IMsCustomerService msCustomerService;

    @Resource
    private IBuDiagnoseService diagnoseService;

    @Resource
    private IBuOutpatientService buOutpatientService;

    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result<Map<String, Object>> getRecommend(@RequestBody BuDiagnoseRO.GetRecommendRO ro) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        ro.setCustomerName(currentPrincipalName);
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        ro.setCustomerId(customerId);
        //查询云平台outpatient
        BuOutpatient buOutpatient = buOutpatientService.getOutpatientByHisId(customerId, ro.getOutpatientId());
        //赋值为云平台数据
        if (buOutpatient != null) {
            ro.setOutpatientId(buOutpatient.getId());
            ro.setPatientId(buOutpatient.getPatientId());
        }
        Map<String, Object> map = recommendService.selectRecommend(ro);
        return Result.success(map);
    }


    @ApiOperation(value = "保存诊断信息接口")
    @PostMapping(value = "/addDiagnoseInfo")
    public Result<Boolean> addDiagnose(@RequestBody BuPrescriptionRO.AddPrescriptionShiyi ro) throws Exception {
        MsCustomer msCustomer = msCustomerService.getCustomerByName(ro.getDiagnoseInfo().getCustomerName());
        if (Objects.isNull(msCustomer))
            throw new Exception("不存在名称为：" + ro.getDiagnoseInfo().getCustomerName() + " 的客户!");
        ro.getDiagnoseInfo().setCustomerId(msCustomer.getId());
        BuDiagnose buDiagnose = new BuDiagnose();
        buDiagnose.setId(ro.getDiagnoseInfo().getId());
        buDiagnose.setDoctorId(ro.getBaseInfo().getDoctorId());
        buDiagnose.setPatientId(ro.getBaseInfo().getPatientId());
        buDiagnose.setOutpatientId(ro.getBaseInfo().getOutpatientId());
        buDiagnose.setId(ro.getDiagnoseInfo().getId());
        buDiagnose.setCustomerId(ro.getDiagnoseInfo().getCustomerId());
        buDiagnose.setDepartmentId(ro.getDiagnoseInfo().getDepartmentId());
        buDiagnose.setDisease(ro.getDiagnoseInfo().getDisease());
        buDiagnose.setDiseaseId(ro.getDiagnoseInfo().getDiseaseId());
        buDiagnose.setDisease(ro.getDiagnoseInfo().getDisease());
        buDiagnose.setSymptoms(ro.getDiagnoseInfo().getSymptoms());
        buDiagnose.setSymptomIds(ro.getDiagnoseInfo().getSymptomIds());
        buDiagnose.setSyndrome(ro.getDiagnoseInfo().getSyndrome());
        buDiagnose.setSyndromeId(ro.getDiagnoseInfo().getSyndromeId());
        buDiagnose.setStatus(1);
        buDiagnose.setUpdateTime(new Date());
        if (Objects.isNull(buDiagnose.getId())) {
            buDiagnose.setCreateTime(new Date());
        }
        boolean isSave = diagnoseService.saveOrUpdate(buDiagnose);
        return Result.success(isSave);
    }


    @ApiOperation(value = "获取诊断详情")
    @PostMapping(value = "/getDetail")
    public Result getDetail(HttpServletRequest request, @RequestBody BuDiagnoseRO.GetRecommendRO ro) throws Exception {
        Assert.notNull(ro.getPatientId(), "his端患者id不能为空!");
        Assert.notNull(ro.getOutpatientId(), "his端患者门诊预约id不能为空!");
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        ro.setCustomerId(customerId);
        Map<String, Object> result = diagnoseService.getDetails(ro);
        return Result.success(result);
    }

    @ApiOperation(value = "获取云平台对应的outpatientID")
    @GetMapping(value = "/getYptOutpatient")
    public Result<Long> getYptOutpatientByHisId(Long outpatientId) {
        BuOutpatient buOutpatient = buOutpatientService.getOutpatientByHisId(null, outpatientId);
        Long id = Optional.ofNullable(buOutpatient).orElse(new BuOutpatient()).getId();
        return Result.success(id);
    }
}


























