package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.bean.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.bean.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.bean.avo.DictSymptomVo;
import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
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
import com.maizhiyu.yzt.service.MedicantMappingService;
import com.maizhiyu.yzt.service.TreatmentMappingService;
import com.maizhiyu.yzt.serviceimpl.YptDiseaseService;
import com.maizhiyu.yzt.serviceimpl.YptMedicantService;
import com.maizhiyu.yzt.serviceimpl.YptTreatmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Api(tags = "HIS诊断接口")
@RestController
@RequestMapping("/diagnose")
public class BuDiagnoseController {

    @Autowired
    private FeignYptClient yptClient;

    @Autowired
    private YptDiseaseService diseaseService;

    @Autowired
    private YptMedicantService medicantService;

    @Autowired
    private YptTreatmentService treatmentService;

    @Resource
    private MedicantMappingService medicantMappingService;

    @Resource
    private TreatmentMappingService treatmentMappingService;

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
        }

        LambdaQueryWrapper<HisOutpatient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HisOutpatient::getRegistrationId, ro.getOutpatientId())
                .last("limit 1");
        HisOutpatient outpatient = outpatientMapper.selectOne(queryWrapper);
        if (Objects.nonNull(outpatient)) {
            ro.setOutpatientId(Long.parseLong(outpatient.getCode()));
        }
        //在没有分型syndromeIdList以及没有症状集合symptomIdList先查询下这次挂号看病是否已经有保存诊断信息和治疗处方
        if (CollectionUtils.isEmpty(ro.getSymptomIdList()) && CollectionUtils.isEmpty(ro.getSyndromeIdList())) {
            Result result = yptClient.getDetail(ro);
            if (Objects.nonNull(result.getData()))
                return result;
        }
        // 调用开放接口获取诊断推荐
        Map<String, Object> recommendMap = yptClient.getRecommend(ro);

        return Result.success(recommendMap);
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
