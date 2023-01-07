package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.BuDiagnoseRO;
import com.maizhiyu.yzt.security.HsUserDetails;
import com.maizhiyu.yzt.service.IBuDiagnoseService;
import com.maizhiyu.yzt.service.IBuRecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "诊断接口")
@RestController
@RequestMapping("/diagnose")
public class BuDiagnoseController extends BaseController {

    @Autowired
    private IBuDiagnoseService diagnoseService;

    @Autowired
    private IBuRecommendService recommendService;

    @ApiOperation(value = "增加诊断", notes = "增加诊断")
    @ApiImplicitParams({})
    @PostMapping("/addDiagnose")
    public Result addDiagnose(@RequestBody BuDiagnose diagnose) {
        diagnose.setStatus(1);
        diagnose.setCreateTime(new Date());
        diagnose.setUpdateTime(diagnose.getCreateTime());
        Integer res = diagnoseService.addDiagnose(diagnose);
        return Result.success(diagnose);
    }


    @ApiOperation(value = "修改诊断信息", notes = "修改诊断信息")
    @PostMapping("/setDiagnose")
    public Result setDiagnose(@RequestBody BuDiagnose diagnose) {
        diagnose.setUpdateTime(new Date());
        Integer res = diagnoseService.setDiagnose(diagnose);
        return Result.success(diagnose);
    }


    @ApiOperation(value = "获取诊断信息", notes = "获取诊断信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "诊断ID", required = true),
    })
    @GetMapping("/getDiagnose")
    public Result getDiagnose(Long id) {
        BuDiagnose diagnose = diagnoseService.getDiagnose(id);
        return Result.success(diagnose);
    }


    @ApiOperation(value = "获取诊断信息(门诊)", notes = "获取诊断信息(门诊)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outpatientId", value = "门诊ID", required = true),
    })
    @GetMapping("/getDiagnoseOfOutpatient")
    public Result<Map<String,Object>> getDiagnoseOfOutpatient(Long outpatientId) {
        BuDiagnose diagnose = diagnoseService.getDiagnoseOfOutpatient(outpatientId);
        return Result.success(diagnose);
    }


    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @ApiImplicitParams({})
    @PostMapping("/getRecommend")
    public Result getRecommend(@RequestBody BuDiagnoseRO.GetRecommendRO ro) {
        //TODO 确定医院
        Map<String, Object> map = recommendService.selectRecommend(ro);
        return Result.success(map);
    }


    @ApiOperation(value = "获取诊断方案推荐-通过疾病", notes = "获取诊断方案推荐-通过疾病")
    @ApiImplicitParams({})
    @PostMapping("/getRecommendByDisease")
    public Result getRecommendByDisease(@RequestBody BuDiagnose diagnose) {
        Map<String, Object> map = recommendService.getRecommendByDisease(diagnose);
        return Result.success(map);
    }


    @ApiOperation(value = "获取诊断方案推荐-通过症状", notes = "获取诊断方案推荐-通过症状")
    @ApiImplicitParams({})
    @PostMapping("/getRecommendBySymptom")
    public Result getRecommendBySymptom(@RequestBody BuDiagnose diagnose) {
        Map<String, Object> map = recommendService.getRecommendBySymptom(diagnose);
        return Result.success(map);
    }

    //通过姓名手机号身份证号码查询诊断信息

    @ApiOperation(value = "通过姓名手机号身份证号码查询诊断信息")
    @ApiImplicitParam(name = "term", value = "搜索字段", required = true)
    @GetMapping("/selectDiagnoseList")
    public Result<List<BuDiagnose>> selectDiagnoseList(String term) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        List<BuDiagnose> list = diagnoseService.selectDiagnoseList(customerId,term);
        return Result.success(list);
    }
}
