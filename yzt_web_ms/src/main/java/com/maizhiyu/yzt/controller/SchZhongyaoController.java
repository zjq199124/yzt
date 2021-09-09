package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.SchZhongyao;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ISchZhongyaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "中药方案接口")
@RestController
@RequestMapping("/zhongyao")
public class SchZhongyaoController {

    @Autowired
    private ISchZhongyaoService service;


    @ApiOperation(value = "增加中药方案", notes = "增加中药方案")
    @PostMapping("/addZhongyao")
    public Result addZhongyao(@RequestBody SchZhongyao zhongyao) {
        zhongyao.setStatus(1);
        Integer res = service.addZhongyao(zhongyao);
        return Result.success(zhongyao);
    }


    @ApiOperation(value = "删除中药方案", notes = "删除中药方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "中药方案id", required = true)
    })
    @GetMapping("/delZhongyao")
    public Result delZhongyao(Long id) {
        Integer res = service.delZhongyao(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改中药方案信息", notes = "修改中药方案信息")
    @PostMapping("/setZhongyao")
    public Result setZhongyao(@RequestBody SchZhongyao zhongyao) {
        Integer res = service.setZhongyao(zhongyao);
        return Result.success(zhongyao);
    }


    @ApiOperation(value = "修改中药方案状态", notes = "修改中药方案状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "中药方案id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setZhongyaoStatus")
    public Result setZhongyaoStatus(Long id, Integer status) {
        SchZhongyao zhongyao = new SchZhongyao();
        zhongyao.setId(id);
        zhongyao.setStatus(status);
        Integer res = service.setZhongyao(zhongyao);
        return Result.success(zhongyao);
    }


    @ApiOperation(value = "获取中药方案信息", notes = "获取中药方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "中药方案id", required = true)
    })
    @GetMapping("/getZhongyao")
    public Result getZhongyao(Long id) {
        SchZhongyao zhongyao = service.getZhongyao(id);
        return Result.success(zhongyao);
    }


    @ApiOperation(value = "获取中药方案列表", notes = "获取中药方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", value = "疾病ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getZhongyaoList")
    public Result getZhongyaoList(Long diseaseId, Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getZhongyaoList(diseaseId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }

}
