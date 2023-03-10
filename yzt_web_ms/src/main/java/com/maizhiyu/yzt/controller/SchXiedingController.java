package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.SchXieding;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ISchXiedingService;
import com.maizhiyu.yzt.service.ISynSyndromeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(tags = "协定方案接口")
@RestController
public class SchXiedingController {

    @Autowired
    private ISchXiedingService service;

    @Autowired
    private ISynSyndromeService syndromeService;


    @ApiOperation(value = "增加协定方案", notes = "增加协定方案")
    @PostMapping("/addXieding")
    public Result addXieding(@RequestBody SchXieding xieding) {
        // 如果有辨证ID则只增加一调记录
        if (xieding.getSyndromeId() != null) {
            xieding.setStatus(1);
            Integer res = service.addXieding(xieding);
            return Result.success(xieding);
        }
        // 如果无辨证ID则查询疾病对应的所有辨证ID，每个辨证增加一条记录
        else {
            IPage<Map<String, Object>> pages = syndromeService.getSyndromeList(new Page(0, -1), xieding.getDiseaseId(), null, null);
            for (Map<String, Object> map : pages.getRecords()) {
                xieding.setStatus(1);
                xieding.setSyndromeId((Long) map.get("id"));
                Integer res = service.addXieding(xieding);
            }
            return Result.success();
        }
    }


    @ApiOperation(value = "删除协定方案", notes = "删除协定方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "协定方案id", required = true)
    })
    @GetMapping("/delXieding")
    public Result delXieding(Long id) {
        Integer res = service.delXieding(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改协定方案信息", notes = "修改协定方案信息")
    @PostMapping("/setXieding")
    public Result setXieding(@RequestBody SchXieding xieding) {
        Integer res = service.setXieding(xieding);
        return Result.success(xieding);
    }


    @ApiOperation(value = "修改协定方案状态", notes = "修改协定方案状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "协定方案id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setXiedingStatus")
    public Result setXiedingStatus(Long id, Integer status) {
        SchXieding xieding = new SchXieding();
        xieding.setId(id);
        xieding.setStatus(status);
        Integer res = service.setXieding(xieding);
        return Result.success(xieding);
    }


    @ApiOperation(value = "获取协定方案信息", notes = "获取协定方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "协定方案id", required = true)
    })
    @GetMapping("/getXieding")
    public Result getXieding(Long id) {
        SchXieding xieding = service.getXieding(id);
        return Result.success(xieding);
    }


    @ApiOperation(value = "获取协定方案列表", notes = "获取协定方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getXiedingList")
    public Result getXiedingList(Long diseaseId, Integer status, String term,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Map<String, Object>> list = service.getXiedingList(new Page(pageNum, pageSize), diseaseId, status, term);
        return Result.success(list);
    }

}
