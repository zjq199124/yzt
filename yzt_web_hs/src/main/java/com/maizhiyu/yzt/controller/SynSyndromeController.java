package com.maizhiyu.yzt.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.SynSyndrome;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IDictSyndromeService;
import com.maizhiyu.yzt.service.ISynSyndromeService;
import com.maizhiyu.yzt.vo.DictSyndromeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Api(tags = "辨证方案接口")
@RestController
@RequestMapping("/syndrome")
public class SynSyndromeController {

    @Resource
    private ISynSyndromeService service;

    @Resource
    private IDictSyndromeService dictSyndromeService;


    @ApiOperation(value = "获取辨证方案信息", notes = "获取辨证方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "辨证方案id", required = true)
    })
    @GetMapping("/getSyndrome")
    public Result getSyndrome(Long id) {
        SynSyndrome syndrome = service.getSyndrome(id);
        return Result.success(syndrome);
    }


    @ApiOperation(value = "获取辨证方案列表", notes = "获取辨证方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseName", value = "疾病名称", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getSyndromeList")
    public Result getSyndromeList(
            String diseaseName, Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Map<String, Object>> list = service.getSyndromeList(new Page(pageNum,pageSize),diseaseName, status, term);
        return Result.success(list);
    }

    @ApiOperation(value = "获取疾病的所有分型列表", notes = "获取疾病的所有分型列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", value = "云平台疾病id"),
            @ApiImplicitParam(name = "search", value = "分型搜索字段")
    })
    @GetMapping("/list")
    public Result<List<DictSyndromeVo>> DictSymptomList(@RequestParam Long diseaseId, @RequestParam(required = false) String search) {
        Assert.notNull(diseaseId, "疾病id不能为空!");
        List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeService.selectByDiseaseId(diseaseId, search);
        return Result.success(dictSyndromeVoList);
    }

}
