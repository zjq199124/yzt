package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.bean.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.bean.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.bean.avo.DictSymptomVo;
import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.mapperhis.HisDoctorMapper;
import com.maizhiyu.yzt.mapperhis.HisOutpatientMapper;
import com.maizhiyu.yzt.mapperhis.HisPatientMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.JzfyDiseaseMappingService;
import com.maizhiyu.yzt.service.JzfyMedicantMappingService;
import com.maizhiyu.yzt.service.JzfyTreatmentMappingService;
import com.maizhiyu.yzt.serviceimpl.YptDiseaseService;
import com.maizhiyu.yzt.serviceimpl.YptMedicantService;
import com.maizhiyu.yzt.serviceimpl.YptTreatmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    private JzfyMedicantMappingService jzfyMedicantMappingService;

    @Resource
    private JzfyTreatmentMappingService jzfyTreatmentMappingService;

    @Resource
    private JzfyDiseaseMappingService jzfyDiseaseMappingService;

    @Resource
    private HisOutpatientMapper outpatientMapper;

    @Resource
    private HisPatientMapper hisPatientMapper;

    @Resource
    private HisDoctorMapper hisDoctorMapper;

    @Value("${customer.name}")
    private String customerName;

   /* @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result<BuDiagnoseVO.GetRecommendVO> getRecommend(@RequestBody @Valid BuDiagnoseRO.GetRecommendRO ro) {
        // 诊断疾病ID映射
        try {
            YptDisease disease = diseaseService.getDiseaseByHisname(ro.getDisease());
            // 存在映射关系则更新为新的疾病名称
            if (disease != null) {
                ro.setDisease(disease.getName());
            }
        } catch (Exception e) {
            log.warn("诊断疾病ID映射异常：" + ro.getDisease() + " " + e.getMessage());
            e.printStackTrace();
        }
        // 调用开放接口获取诊断推荐
        Result<BuDiagnoseVO.GetRecommendVO> result = yptClient.getRecommend(ro);
        // 中药处方ID映射
        for (BuDiagnoseVO.ZhongyaoVO vo : result.getData().getZhongyaoList()) {
            for (BuDiagnoseVO.ZhongyaoComponentVO it : vo.getComponent()) {
                String codeOld = it.getCode();
                String nameOld = it.getName();
                try {
                    // 按code映射
                    if (codeOld != null && codeOld.length() > 0) {
                        YptMedicant medicant = medicantService.getMedicantByCode(it.getCode());
                        if (medicant != null) {
                            it.setCode(medicant.getHiscode());
                            it.setName(medicant.getHisname());
                            continue;
                        }
                    }
                    // 按名称映射
                    if (nameOld != null && nameOld.length() > 0) {
                        YptMedicant medicant = medicantService.getMedicantByName(it.getName());
                        if (medicant != null) {
                            it.setCode(medicant.getHiscode());
                            it.setName(medicant.getHisname());
                            continue;
                        }
                    }
                } catch (Exception e) {
                    log.warn("中药处方ID映射异常：" + codeOld + '-' + nameOld + " " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        // 协定处方ID映射
        for (BuDiagnoseVO.XiedingVO vo : result.getData().getXiedingList()) {
            for (BuDiagnoseVO.XiedingComponentVO it : vo.getComponent()) {
                String codeOld = it.getCode();
                String nameOld = it.getName();
                try {
                    // 按code映射
                    if (codeOld != null && codeOld.length() > 0) {
                        YptMedicant medicant = medicantService.getMedicantByCode(it.getCode());
                        if (medicant != null) {
                            it.setCode(medicant.getHiscode());
                            it.setName(medicant.getHisname());
                            continue;
                        }
                    }
                    // 按名称映射
                    if (nameOld != null && nameOld.length() > 0) {
                        YptMedicant medicant = medicantService.getMedicantByName(it.getName());
                        if (medicant != null) {
                            it.setCode(medicant.getHiscode());
                            it.setName(medicant.getHisname());
                            continue;
                        }
                    }
                } catch (Exception e) {
                    log.warn("协定处方ID映射异常：" + codeOld + '-' + nameOld + " " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        // 协定治疗ID映射
        for (BuDiagnoseVO.ShiyiVO vo : result.getData().getShiyiList()) {
            try {
                // 按code映射
                String codeOld = vo.getCode();
                if (codeOld != null && codeOld.length() > 0) {
                    YptTreatment treatment = treatmentService.getTreatmentByCode(vo.getCode());
                    if (treatment != null) {
                        vo.setCode(treatment.getHiscode());
                        vo.setName(treatment.getHisname());
                        continue;
                    }
                }
                // 按名称映射
                String nameOld = vo.getName();
                if (nameOld != null && nameOld.length() > 0) {
                    YptTreatment treatment = treatmentService.getTreatmentByName(vo.getName());
                    if (treatment != null) {
                        vo.setCode(treatment.getHiscode());
                        vo.setName(treatment.getHisname());
                        continue;
                    }
                }
            } catch (Exception e) {
                log.warn("适宜处方ID映射异常：" + e.getMessage());
            }
        }
        // 返回结果(如果都为空则code设置为1)
        Result res = Result.success(result.getData());
        if (result.getData().getZhongyaoList().size() == 0
                && result.getData().getChengyaoList().size() == 0
                && result.getData().getXiedingList().size() == 0
                && result.getData().getShiyiList().size() == 0) {
            res.setCode(1);
            res.setMsg("获取诊断方案为空");
        }
        return res;
    }*/

   /* @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result<BuDiagnoseVO.GetRecommendVO> getRecommend(@RequestBody @Valid BuDiagnoseRO.GetRecommendRO ro) {
        Assert.notNull(ro.getDiseaseId(), "疾病id不能为空!");
        // 调用开放接口获取诊断推荐
        Result<BuDiagnoseVO.GetRecommendVO> result = yptClient.getRecommend(ro);

        BuDiagnoseVO.GetRecommendVO data = result.getData();

        // 中药处方ID映射

        //1:所有的中药code集合
        List<String> zhongyaoCodeList = data.getZhongyaoList().stream()
                .filter(item -> !CollectionUtils.isEmpty(item.getComponent()))
                .map(item -> item.getComponent().stream().map(BuDiagnoseVO.ZhongyaoComponentVO::getCode).collect(Collectors.toList()))
                .flatMap(Collection::stream).collect(Collectors.toSet()).stream().collect(Collectors.toList());

        //2:所有的中药name集合
        List<String> zhongyaoNameList = data.getZhongyaoList().stream()
                .filter(item -> !CollectionUtils.isEmpty(item.getComponent()))
                .map(item -> item.getComponent().stream().map(BuDiagnoseVO.ZhongyaoComponentVO::getName).collect(Collectors.toList()))
                .flatMap(Collection::stream).collect(Collectors.toSet()).stream().collect(Collectors.toList());

        List<JzfyMedicantMapping> jzfyMedicantMappingCodeList = jzfyMedicantMappingService.getMedicantByCodeList(zhongyaoCodeList);

        Map<String, JzfyMedicantMapping> codeZhongyaomap = jzfyMedicantMappingCodeList.stream().collect(Collectors.toMap(JzfyMedicantMapping::getCode, Function.identity()));

        List<JzfyMedicantMapping> jzfyMedicantMappingNameList = jzfyMedicantMappingService.getMedicantByNameList(zhongyaoNameList);

        Map<String, JzfyMedicantMapping> nameZhongyaomap = jzfyMedicantMappingNameList.stream().collect(Collectors.toMap(JzfyMedicantMapping::getName, Function.identity()));

        //3：遍历所有的中药
        data.getZhongyaoList().forEach(item -> {
            if(CollectionUtils.isEmpty(item.getComponent()))
                return;

            item.getComponent().forEach(component -> {
                try {
                    JzfyMedicantMapping jzfyMedicantMapping = null;
                    // 按code映射
                    if (StringUtils.isNotBlank(component.getCode()) && MapUtil.isNotEmpty(codeZhongyaomap)) {
                        jzfyMedicantMapping = codeZhongyaomap.get(component.getCode());
                    }else if (StringUtils.isNotBlank(component.getName()) && MapUtil.isNotEmpty(nameZhongyaomap)) {// 按名称映射
                        jzfyMedicantMapping = nameZhongyaomap.get(component.getName());
                    }

                    if (Objects.isNull(jzfyMedicantMapping))
                        return;

                    component.setCode(jzfyMedicantMapping.getHisCode());
                    component.setName(jzfyMedicantMapping.getHisName());
                } catch (Exception e) {
                    log.error("中药处方ID映射异常："  + e.getMessage());
                    e.printStackTrace();
                }
            });
        });

        // 协定处方ID映射
         data.getXiedingList().forEach(item -> {
            if(CollectionUtils.isEmpty(item.getComponent()))
                return;

            item.getComponent().forEach(component -> {
                try {
                    JzfyMedicantMapping jzfyMedicantMapping = null;
                    // 按code映射
                    if (StringUtils.isNotBlank(component.getCode())) {
                        jzfyMedicantMapping = jzfyMedicantMappingService.getMedicantByCode(component.getCode());
                    } else if (StringUtils.isNotBlank(component.getName())) {// 按名称映射
                        jzfyMedicantMapping = jzfyMedicantMappingService.getMedicantByName(component.getCode());
                    }

                    if(Objects.isNull(jzfyMedicantMapping))
                        return;

                    component.setCode(jzfyMedicantMapping.getHisCode());
                    component.setName(jzfyMedicantMapping.getHisName());
                }catch (Exception e) {
                    log.error("协定处方ID映射异常："  + e.getMessage());
                    e.printStackTrace();
                }
            });
        });


        // 协定治疗ID映射
        data.getShiyiList().forEach(item -> {
            try {
                JzfyTreatmentMapping jzfyTreatmentMapping = null;
                if (StringUtils.isNotBlank(item.getCode())) {
                    jzfyTreatmentMapping = jzfyTreatmentMappingService.getTreatmentByCode(item.getCode());
                }else if (StringUtils.isNotBlank(item.getName())) {
                    jzfyTreatmentMapping = jzfyTreatmentMappingService.getTreatmentByName(item.getName());
                }

                if(Objects.isNull(jzfyTreatmentMapping))
                    return;

                item.setCode(jzfyTreatmentMapping.getHisCode());
                item.setName(jzfyTreatmentMapping.getHisName());
            }catch (Exception e) {
                log.error("协定治疗ID映射异常："  + e.getMessage());
                e.printStackTrace();
            }
        });

        // 返回结果(如果都为空则code设置为1)
        Result res = Result.success(result.getData());
        if (result.getData().getZhongyaoList().size() == 0
                && result.getData().getChengyaoList().size() == 0
                && result.getData().getXiedingList().size() == 0
                && result.getData().getShiyiList().size() == 0) {
            res.setCode(1);
            res.setMsg("获取诊断方案为空");
        }
        return res;
    }*/

    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result getRecommend(@RequestBody @Valid BuDiagnoseRO.GetRecommendRO ro) {
        Assert.notNull(ro, "查询对象不能为空!");
        Map<String, Object> resultMap = new HashMap<>();

        ro.setCustomerName(customerName);

        //1.对his传过来的疾病名称和云平台疾病名称进行映射(有西医诊断优先匹配西医诊断)
        if (Objects.isNull(ro.getDiseaseId())) {
            String hisDiseaseName = Objects.nonNull(ro.getWestDiagnose()) ? ro.getWestDiagnose() : ro.getTcmDiagnose();
            JzfyDiseaseMapping jzfyDiseaseMapping = jzfyDiseaseMappingService.selectByHisName(hisDiseaseName);
            Preconditions.checkArgument(Objects.nonNull(jzfyDiseaseMapping), "his中的诊断：" + hisDiseaseName + " 在云平台中没有与之相匹配的中医诊断!");
            //云平台中医诊断名称
            resultMap.put("yptDiseaseName", jzfyDiseaseMapping.getName());
            //云平台中医诊断Id
            resultMap.put("yptDiseaseId", jzfyDiseaseMapping.getDiseaseId());

            ro.setDiseaseId(jzfyDiseaseMapping.getDiseaseId());
        }

        //在没有分型syndromeIdList以及没有症状集合symptomIdList先查询下这次挂号看病是否已经有保存诊断信息和治疗处方
        if (CollectionUtils.isEmpty(ro.getSymptomIdList()) && CollectionUtils.isEmpty(ro.getSyndromeIdList())) {
            Result result = yptClient.getDetail(ro);
            if(Objects.nonNull(result.getData()))
                return result;
        }

        //2.没有syndromeIdList的情况下，判断是否有传症状集合symptomIdList，没有的话通过Feign远程调用云平台中获取疾病所有症状的接口
        if (CollectionUtils.isEmpty(ro.getSyndromeIdList()) && CollectionUtils.isEmpty(ro.getSymptomIdList())) {
            Result<List<DictSymptomVo>> dictSymptomResult = yptClient.selectDictSymptomList(ro.getDiseaseId());
            //疾病症状数据集合
            List<DictSymptomVo> dictSymptomVoList = dictSymptomResult.getData();
            resultMap.put("dictSymptomList", dictSymptomVoList);

            if (!CollectionUtils.isEmpty(dictSymptomVoList)) {
                List<Long> symptomIdList = dictSymptomVoList.stream().map(DictSymptomVo::getId).collect(Collectors.toList());
                ro.setSymptomIdList(symptomIdList);
            }
        }

        //3.判断是否有传分型集合syndromeIdList，没有的话使用symptomIdList通过Feign远程调用云平台中获取疾病所有分型的接口
        if (CollectionUtils.isEmpty(ro.getSyndromeIdList())) {
            Result<List<DictSyndromeVo>> dictSyndromeResult = yptClient.selectDictSyndromeBySymptomIdList(ro.getSymptomIdList());
            List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeResult.getData();
            //疾病分型数据集合
            resultMap.put("dictSyndromeList", dictSyndromeVoList);
            if (!CollectionUtils.isEmpty(dictSyndromeVoList)) {
                List<Long> syndromeIdList = dictSyndromeVoList.stream().map(DictSyndromeVo::getId).collect(Collectors.toList());
                ro.setSyndromeIdList(syndromeIdList);
            }
        }

        // 调用开放接口获取诊断推荐
        Result<BuDiagnoseVO.GetRecommendVO> recommendResult = yptClient.getRecommend(ro);
        resultMap.put("shiyiList", recommendResult.getData().getShiyiList());
        resultMap.put("yptDiagnoseId", null);
        resultMap.put("yptPrescriptionId", null);
        resultMap.put("prescriptionItemList", Collections.emptyList());

        return Result.success(resultMap);
    }

    @ApiOperation(value = "获取门诊诊断和患者信息", notes = "获取门诊诊断和患者信息")
    @ApiImplicitParam(name = "outpatientId", value = "门诊ID", required = true)
    @GetMapping("/getDiagnoseOfOutpatient")
    public Result getDiagnoseOfOutpatient(Long outpatientId) throws Exception {
        Assert.notNull(outpatientId, "outpatientId不能为空!");
        HisOutpatient hisOutpatient = outpatientMapper.selectById(outpatientId);
        if(Objects.isNull(hisOutpatient))
            throw new Exception("不存在outpatientId为: " + outpatientId + " 的门诊信息!");

        HisPatient hisPatient = hisPatientMapper.selectById(hisOutpatient.getPatientId());
        if(Objects.isNull(hisPatient))
            throw new Exception("不存在outpatientId为: " + outpatientId + " 对应的患者的信息!");

        HisDoctor hisDoctor = hisDoctorMapper.selectById(hisOutpatient.getDoctorId());
        if(Objects.isNull(hisDoctor))
            throw new Exception("不存在outpatientId为: " + outpatientId + " 的此次门诊对应的医生信息!");

        Result idResult = yptClient.getYptOutpatientByHisId(outpatientId);


        Map<String, Object> result = new HashMap<>();
        result.put("hisOutpatient", hisOutpatient);
        result.put("hisPatient", hisPatient);
        result.put("hisDoctor", hisDoctor);
        result.put("yptOutpatientId", idResult.getData());

        return Result.success(result);
    }
}
