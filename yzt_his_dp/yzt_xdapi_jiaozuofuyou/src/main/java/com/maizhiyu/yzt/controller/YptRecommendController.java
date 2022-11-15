package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.bean.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.bean.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.entity.JzfyDiseaseMapping;
import com.maizhiyu.yzt.entity.YptDisease;
import com.maizhiyu.yzt.entity.YptMedicant;
import com.maizhiyu.yzt.entity.YptTreatment;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IYptRecommendService;
import com.maizhiyu.yzt.service.JzfyDiseaseMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

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
    private JzfyDiseaseMappingService jzfyDiseaseMappingService;

    @Resource
    private IYptRecommendService yptRecommendService;

    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result getRecommend(Long diseaseId,Long syndromeId,String term) {

        Result res = yptRecommendService.getRecommend(diseaseId,syndromeId,term);

        //TODO res 中获取的数据都是云平台的数据，需要经过中间翻译表，翻译成his
        return res;
    }
}


























