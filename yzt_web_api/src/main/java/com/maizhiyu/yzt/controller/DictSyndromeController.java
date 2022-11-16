package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IDictSymptomService;
import com.maizhiyu.yzt.service.IDictSyndromeService;
import com.maizhiyu.yzt.vo.DictSymptomVo;
import com.maizhiyu.yzt.vo.DictSyndromeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Api(tags = "分型接口")
@RestController
@RequestMapping("/dictSyndrome")
public class DictSyndromeController {

    @Resource
    private IDictSyndromeService dictSyndromeService;

    @ApiOperation(value = "根据症状确定分型的接口",notes = "根据症状确定分型的接口")
    @ApiImplicitParam(name = "symptomIdList",value = "症状id的list")
    @PostMapping("/selectBySymptom")
    public Result<List<DictSyndromeVo>> selectDictSyndromeBySymptomIdList(@RequestBody List<Long> symptomIdList) {
        Assert.notNull(symptomIdList, "症状id不能为空!");
        List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeService.selectDictSyndromeBySymptomIdList(symptomIdList);
        return Result.success(dictSyndromeVoList);
    }

    @ApiOperation(value = "获取疾病所有分型的接口")
    @ApiImplicitParam(name = "diseaseId", value = "疾病id", required = true)
    @GetMapping("/list")
    public Result<List<DictSyndromeVo>> selectDictSyndromeListByDiseaseId(Long diseaseId) {
        Assert.notNull(diseaseId, "疾病id不能为空!");
        List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeService.selectByDiseaseId(diseaseId);
        return Result.success(dictSyndromeVoList);
    }
}




























