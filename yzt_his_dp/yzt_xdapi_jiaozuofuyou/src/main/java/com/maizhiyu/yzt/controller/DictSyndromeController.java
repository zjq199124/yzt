package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.bean.avo.DictSymptomVo;
import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IYptCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 分型接口
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-14
 */
@Slf4j
@Api(tags = "分型接口")
@RestController
@RequestMapping("/dictSyndrome")
public class DictSyndromeController {

    @Resource
    private IYptCommonService yptCommonService;

    @ApiOperation(value = "根据症状确定分型的接口",notes = "根据症状确定分型的接口")
    @ApiImplicitParam(name = "symptomIdList",value = "症状id的list")
    @PostMapping("/selectBySymptom")
    public Result getSyndrome(@RequestBody List<Long> symptomIdList) {
        Assert.notNull(symptomIdList, "症状id不能为空!");
        //根据症状id查询出符合该症状的分型列表
        Result<List<DictSyndromeVo>> dictSyndromeResult = yptCommonService.selectDictSyndromeBySymptomIdList(symptomIdList);
        return Result.success(dictSyndromeResult.getData());
    }
}
































