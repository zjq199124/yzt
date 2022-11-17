package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.entity.RelSyndromeSymptom;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IRelSyndromeSymptomService;
import com.maizhiyu.yzt.vo.RelSyndromeSymptomVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "分型症状关系接口")
@RestController
@RequestMapping("/relSyndromeSymptom")
public class RelSyndromeSymptomController {

    @Resource
    private IRelSyndromeSymptomService relSyndromeSymptomService;

    @ApiOperation(value = "根据分型id列表查询分型症状关系数据接口")
    @ApiImplicitParam(name = "diseaseId", value = "疾病id", required = true)
    @PostMapping(value = "/selectBySyndromeIds")
    Result<List<RelSyndromeSymptomVo>> selectDictSymptomBySyndromeIdList(@RequestBody List<Long> syndromeIds) {
        Assert.notEmpty(syndromeIds, "分型id不能为空!");
        List<RelSyndromeSymptomVo> list = relSyndromeSymptomService.selectDictSymptomBySyndromeIdList(syndromeIds);
        return Result.success(list);
    }
}




















