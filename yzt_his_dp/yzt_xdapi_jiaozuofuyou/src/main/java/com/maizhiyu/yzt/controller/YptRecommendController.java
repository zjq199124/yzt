package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.bean.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.bean.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.entity.YptMedicant;
import com.maizhiyu.yzt.entity.YptTreatment;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IYptCommonService;
import com.maizhiyu.yzt.service.JzfyDiseaseMappingService;
import com.maizhiyu.yzt.serviceimpl.YptDiseaseService;
import com.maizhiyu.yzt.serviceimpl.YptMedicantService;
import com.maizhiyu.yzt.serviceimpl.YptTreatmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 推荐方案接口
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-15
 */
@Slf4j
@Api(tags = "推荐方案")
@RestController
@RequestMapping("/sytech")
public class YptRecommendController {

    @Resource
    private YptDiseaseService diseaseService;

    @Resource
    private YptMedicantService medicantService;

    @Resource
    private YptTreatmentService treatmentService;

    @Resource
    private IYptCommonService yptRecommendService;

    @Resource
    private FeignYptClient yptClient;

    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result getRecommend(Long diseaseId,Long syndromeId,String term) {

        Result res = yptRecommendService.getRecommend(diseaseId,syndromeId,term);

        //TODO res 中获取的数据都是云平台的数据，需要经过中间翻译表，翻译成his
        return res;
    }

    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result<BuDiagnoseVO.GetRecommendVO> getRecommend(@RequestBody @Valid BuDiagnoseRO.GetRecommendRO ro) {
        Assert.notNull(ro.getDiseaseId(), "疾病id不能为空!");
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


























