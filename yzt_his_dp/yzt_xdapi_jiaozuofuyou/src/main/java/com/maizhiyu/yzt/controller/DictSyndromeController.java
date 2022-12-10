package com.maizhiyu.yzt.controller;


import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
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
import java.util.List;


/**
 * <p>
 * 分型接口
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-29
 */
@Slf4j
@Api(tags = "分型接口")
@RestController
@RequestMapping("/dictSyndrome")
public class DictSyndromeController {

        @Resource
        private JzfyDiseaseMappingService jzfyDiseaseMappingService;

        @Resource
        private FeignYptClient feignYptClient;

        @ApiOperation(value = "获取疾病的所有分型列表",notes = "获取疾病的所有分型列表")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "diseaseId", value = "云平台疾病id"),
                @ApiImplicitParam(name = "search", value = "分型搜索字段")
        })
        @GetMapping("/list")
        public Result DictSymptomList(@RequestParam Long diseaseId,@RequestParam(required = false) String search) {
                //1.通过Feign远程调用云平台中获取疾病所有症状的接口
                Result<List<DictSyndromeVo>> listResult = feignYptClient.selectDictSyndromeListByDiseaseId(diseaseId, search);
                return Result.success(listResult.getData());
        }
}



































