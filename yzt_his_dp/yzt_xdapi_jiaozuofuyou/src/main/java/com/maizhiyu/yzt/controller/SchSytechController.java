package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Slf4j
@Api(tags = "适宜技术接口")
@RestController
@RequestMapping("/schtech")
public class SchSytechController {

    @Resource
    private FeignYptClient yptClient;

    @Value("${customer.name}")
    private String customerName;

    @ApiOperation(value = "获取符合适宜技术列表", notes = "获取适宜技术列表")
    @GetMapping("/getSytechList")
    public Result getSytechList(@RequestParam Long diseaseId,
                                @RequestParam Long syndromeId,
                               @RequestParam(required = false) String search) {

        // 调用开放接口获取诊断推荐
        Result recommendResult = yptClient.getSytechList(diseaseId,syndromeId,search,customerName);
        return Result.success(recommendResult.getData());
    }

    @ApiOperation(value = "获取适宜技术详情", notes = "获取适宜技术详情")
    @GetMapping("/getSytechBySytechId")
    public Result getSytechBySytechId(@RequestParam(value = "diseaseId") Long diseaseId,
                                @RequestParam(value = "syndromeId") Long syndromeId,
                                @RequestParam(value = "sytechId") Long sytechId) {

        // 调用开放接口获取诊断推荐
        Result result = yptClient.getSytechBySytechId(diseaseId,syndromeId,sytechId);
        return Result.success(result.getData());
    }
}
