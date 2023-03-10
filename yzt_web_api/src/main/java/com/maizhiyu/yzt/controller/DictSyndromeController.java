package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IDictSymptomService;
import com.maizhiyu.yzt.service.IDictSyndromeService;
import com.maizhiyu.yzt.vo.DictSymptomVo;
import com.maizhiyu.yzt.vo.DictSyndromeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    public Result<List<DictSyndromeVo>> selectDictSyndromeBySymptomIdList(@RequestParam(value = "diseaseId") Long diseaseId,@RequestBody List<Long> symptomIdList) {
        Assert.notNull(symptomIdList, "症状id不能为空!");
        Assert.notNull(diseaseId, "疾病id不能为空!");
        List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeService.selectDictSyndromeBySymptomIdList(diseaseId,symptomIdList);
        return Result.success(dictSyndromeVoList);
    }

    @ApiOperation(value = "获取疾病所有分型的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", value = "疾病id", required = true),
            @ApiImplicitParam(name = "search", value = "分型搜索字段")
    })
    @GetMapping("/list")
    public Result<List<DictSyndromeVo>> selectDictSyndromeListByDiseaseId(@RequestParam(value = "diseaseId") Long diseaseId,@RequestParam(value = "search",required = false) String search) {
        Assert.notNull(diseaseId, "疾病id不能为空!");
        List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeService.selectByDiseaseId(diseaseId,search);
        return Result.success(dictSyndromeVoList);
    }
}




























