package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.bean.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.entity.DiseaseMapping;
import com.maizhiyu.yzt.entity.HisDoctor;
import com.maizhiyu.yzt.entity.HisOutpatient;
import com.maizhiyu.yzt.entity.HisPatient;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.mapperhis.HisDoctorMapper;
import com.maizhiyu.yzt.mapperhis.HisOutpatientMapper;
import com.maizhiyu.yzt.mapperhis.HisPatientMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.DiseaseMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Api(tags = "HIS诊断接口")
@RestController
@RequestMapping("/diagnose")
public class BuDiagnoseController {

    @Autowired
    private FeignYptClient yptClient;

    @Resource
    private DiseaseMappingService diseaseMappingService;

    @Resource
    private HisOutpatientMapper outpatientMapper;

    @Resource
    private HisPatientMapper hisPatientMapper;

    @Resource
    private HisDoctorMapper hisDoctorMapper;

    @Value("${customer.name}")
    private String customerName;

    //TODO 这里的参数为his中的参数，逻辑是不能通的
    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result getRecommend(@RequestBody @Valid BuDiagnoseRO.GetRecommendRO ro) {
        Assert.notNull(ro, "查询对象不能为空!");
        ro.setCustomerName(customerName);
        //1.对his传过来的疾病名称和云平台疾病名称进行映射(有西医诊断优先匹配西医诊断)
        if (Objects.isNull(ro.getDiseaseId())) {
            String hisDiseaseName = Objects.nonNull(ro.getWestDiagnose()) ? ro.getWestDiagnose() : ro.getTcmDiagnose();
            DiseaseMapping jzfyDiseaseMapping = diseaseMappingService.selectByHisName(hisDiseaseName);
            Preconditions.checkArgument(Objects.nonNull(jzfyDiseaseMapping), "his中的诊断：" + hisDiseaseName + " 在云平台中没有与之相匹配的中医诊断!");
            ro.setDiseaseId(jzfyDiseaseMapping.getDiseaseId());
            ro.setDisease(jzfyDiseaseMapping.getName());
        }
        LambdaQueryWrapper<HisOutpatient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HisOutpatient::getRegistrationId, ro.getOutpatientId())
                .last("limit 1");
        HisOutpatient outpatient = outpatientMapper.selectOne(queryWrapper);
        if (Objects.nonNull(outpatient)) {
            ro.setOutpatientId(Long.parseLong(outpatient.getCode()));
        }
        // 调用开放接口获取诊断推荐
        Result result=yptClient.getRecommend(ro);
        return result;
    }

    @ApiOperation(value = "获取门诊诊断和患者信息", notes = "获取门诊诊断和患者信息")
    @ApiImplicitParam(name = "outpatientId", value = "门诊ID", required = true)
    @GetMapping("/getDiagnoseOfOutpatient")
    public Result getDiagnoseOfOutpatient(@RequestParam Long outpatientId) throws Exception {
        //内部这里的outpatientId是内部his的registration_id，我们查询出视图中的code（就是内部his中的medical_record_id） 当做云平台的outpatient_id
        // 获取token字段
        Assert.notNull(outpatientId, "outpatientId不能为空!");
        LambdaQueryWrapper<HisOutpatient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HisOutpatient::getRegistrationId, outpatientId)
                .last("limit 1");
        HisOutpatient hisOutpatient = outpatientMapper.selectOne(queryWrapper);
        if (Objects.isNull(hisOutpatient))
            throw new Exception("不存在outpatientId为: " + outpatientId + " 的门诊信息!");

        HisPatient hisPatient = hisPatientMapper.selectById(hisOutpatient.getPatientId());
        if (Objects.isNull(hisPatient))
            throw new Exception("不存在outpatientId为: " + outpatientId + " 对应的患者的信息!");

        HisDoctor hisDoctor = hisDoctorMapper.selectById(hisOutpatient.getDoctorId());
        if (Objects.isNull(hisDoctor))
            throw new Exception("不存在outpatientId为: " + outpatientId + " 的此次门诊对应的医生信息!");

        Map<String, Object> result = new HashMap<>();
        result.put("hisOutpatient", hisOutpatient);
        result.put("hisPatient", hisPatient);
        result.put("hisDoctor", hisDoctor);
        result.put("customerName", customerName);

        return Result.success(result);
    }
}
