package com.maizhiyu.yzt.controller;


import cn.hutool.core.lang.Assert;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.bean.avo.DictSymptomVo;
import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
import com.maizhiyu.yzt.entity.JzfyDiseaseMapping;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.JzfyDiseaseMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * <p>
 * 症状接口
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-11
 */
@Slf4j
@Api(tags = "症状接口")
@RestController
@RequestMapping("/dictDiseaseInfo")
public class DictSymptomController {

        @Resource
        private JzfyDiseaseMappingService jzfyDiseaseMappingService;

        @Resource
        private FeignYptClient feignYptClient;

        @ApiOperation(value = "获取his疾病和云平台疾病的映射关系",notes = "获取his疾病和云平台疾病的映射关系")
        @ApiImplicitParam(name = "hisDiseaseName", value = "his方疾病名称", required = true)
        @GetMapping("/check")
        public Result getYptDiseaseName(String hisDiseaseName) {
                Assert.notNull(hisDiseaseName, "疾病名称hisDiseaseName不能为空!");

                //1.对his传过来的疾病名称和云平台疾病名称进行翻译
                JzfyDiseaseMapping jzfyDiseaseMapping = jzfyDiseaseMappingService.selectByHisName(hisDiseaseName);
                boolean status = Objects.nonNull(jzfyDiseaseMapping) ? true : false;
                return Result.success(status);
        }

        @ApiOperation(value = "获取疾病的所有症状和分型列表",notes = "获取疾病的所有症状和分型列表")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "westDiagnose", value = "his西医诊断名称"),
                @ApiImplicitParam(name = "tcmDiagnose", value = "his中医诊断名称")
        })
        @GetMapping("/list")
        public Result DictSymptomList(@RequestParam(required = false) String westDiagnose, @RequestParam(required = false) String tcmDiagnose) {
                Preconditions.checkArgument(Objects.nonNull(westDiagnose) || Objects.nonNull(tcmDiagnose), "西医诊断和中医诊断不能同时为空!");

                //1.对his传过来的疾病名称和云平台疾病名称进行映射(有西医诊断优先匹配西医诊断)
                String hisDiseaseName = Objects.nonNull(westDiagnose) ? westDiagnose : tcmDiagnose;
                JzfyDiseaseMapping jzfyDiseaseMapping = jzfyDiseaseMappingService.selectByHisName(hisDiseaseName);
                Preconditions.checkArgument(Objects.nonNull(jzfyDiseaseMapping), "his中的诊断：" + hisDiseaseName + " 在云平台中没有与之相匹配的中医诊断!");

                //2.通过Feign远程调用云平台中获取疾病所有症状的接口
                Result<List<DictSymptomVo>> dictSymptomResult = feignYptClient.selectDictSymptomList(jzfyDiseaseMapping.getDiseaseId());

                //3.通过Feign远程调用云平台中获取疾病所有分型的接口
                Result<List<DictSyndromeVo>> dictSyndromeResult = feignYptClient.selectDictSyndromeListByDiseaseId(jzfyDiseaseMapping.getDiseaseId());

                Map<String, Object> resultMap = new HashMap<>();
                //疾病症状数据集合
                resultMap.put("dictSymptomList", dictSymptomResult.getData());
                //疾病分型数据集合
                resultMap.put("dictSyndromeList", dictSyndromeResult.getData());
                //云平台中医诊断名称
                resultMap.put("yptDiseaseName", jzfyDiseaseMapping.getName());
                //云平台中医诊断Id
                resultMap.put("yptDiseaseId", jzfyDiseaseMapping.getDiseaseId());
                return Result.success(resultMap);
        }
}



































