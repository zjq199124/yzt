package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.bean.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.bean.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.entity.YptDisease;
import com.maizhiyu.yzt.entity.YptMedicant;
import com.maizhiyu.yzt.entity.YptTreatment;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.serviceimpl.YptDiseaseService;
import com.maizhiyu.yzt.serviceimpl.YptMedicantService;
import com.maizhiyu.yzt.serviceimpl.YptTreatmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


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

    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
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
    }

}
