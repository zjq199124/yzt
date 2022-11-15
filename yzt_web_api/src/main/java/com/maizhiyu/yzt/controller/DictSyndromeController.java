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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Api(tags = "分型接口")
@RestController
@RequestMapping("/dictSyndrome")
public class DictSyndromeController {

    @Resource
    private IDictSyndromeService dictSyndromeService;

    @ApiOperation(value = "获取疾病分型的接口")
    @ApiImplicitParam(name = "diseaseId", value = "疾病id", required = true)
    @GetMapping("/selectBySymptom")
    public Result<List<DictSyndromeVo>> selectDictSyndromeBySymptomIdList(List<Long> symptomIdList) {
        Assert.notNull(symptomIdList, "症状id不能为空!");
        List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeService.selectDictSyndromeBySymptomIdList(symptomIdList);
        return Result.success(dictSyndromeVoList);
    }
}




























