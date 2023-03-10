package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;


@Slf4j
@Api(tags = "适宜技术接口")
@RestController
@RequestMapping("/schtech")
public class SchSytechController {

    @Resource
    private ITsSytechService tsSytechService;

    @Resource
    private ISchSytechService schSytechService;

    @GetMapping("/getSytechList")
    public Result getSytechList(@RequestParam(value = "diseaseId") Long diseaseId,
                         @RequestParam(value = "syndromeId") Long syndromeId,
                         @RequestParam(value = "search",required = false) String search) {
        List<TsSytech> tsSytechList = tsSytechService.selectSytechList(diseaseId, syndromeId, search);
        return Result.success(tsSytechList);
    }

    @GetMapping("/getSytechBySytechId")
    public Result getSytechBySytechId(@RequestParam(value = "diseaseId") Long diseaseId,
                               @RequestParam(value = "syndromeId") Long syndromeId,
                               @RequestParam(value = "sytechId") Long sytechId) {

        Map<String, Object> resultMap = schSytechService.getSytechBySytechId(diseaseId, syndromeId, sytechId);
        return Result.success(resultMap);
    }
}


















































