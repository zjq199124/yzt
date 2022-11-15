package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IDictSymptomService;
import com.maizhiyu.yzt.vo.DictSymptomVo;
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
@Api(tags = "症状接口")
@RestController
@RequestMapping("/dictSymptom")
public class DictSymptomController {

    @Resource
    private IDictSymptomService dictSymptomService;

    @ApiOperation(value = "获取疾病症状的接口")
    @ApiImplicitParam(name = "diseaseId", value = "疾病id", required = true)
    @GetMapping("/list")
    public Result<List<DictSymptomVo>> selectDictSymptomList(Long diseaseId) {
        Assert.notNull(diseaseId, "疾病id不能为空!");
        List<DictSymptomVo> dictSymptomVoList = dictSymptomService.selectByDiseaseId(diseaseId);
        return Result.success(dictSymptomVoList);
    }
}




























