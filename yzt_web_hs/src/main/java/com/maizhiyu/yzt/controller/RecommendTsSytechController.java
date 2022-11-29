package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ISchSytechService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "适宜技术方案接口")
@RestController
@RequestMapping("/sytech")
public class RecommendTsSytechController {

    @Autowired
    private ISchSytechService service;

/*    @ApiOperation(value = "获取推荐方案", notes = "获取推荐方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "syndromeId", value = "分型ID", required = false),
            @ApiImplicitParam(name = "diseaseId", value = "疾病ID", required = false),
            @ApiImplicitParam(name = "term",value = "关键词",required = false),
    })
    @GetMapping("/getRecommend")
    public Result getRecommend(Long syndromeId, Long diseaseId,String term){
        List<Map<String, Object>> list = service.getRecommend(syndromeId, diseaseId,term);
        return Result.success(list);
    }*/



}
