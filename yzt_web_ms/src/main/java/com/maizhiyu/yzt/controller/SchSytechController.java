package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.SchSytech;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ISchSytechService;
import com.maizhiyu.yzt.service.ISynSyndromeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(tags = "适宜方案接口")
@RestController
@RequestMapping("/schtech")
public class SchSytechController {

    @Autowired
    private ISchSytechService service;

    @Autowired
    private ISynSyndromeService syndromeService;


    @ApiOperation(value = "增加适宜方案", notes = "增加适宜方案")
    @PostMapping("/addSytech")
    public Result addSytech(@RequestBody SchSytech sytech) {
        // 如果有辨证ID则只增加一调记录
        if (sytech.getSyndromeId() != null) {
            sytech.setStatus(1);
            Integer res = service.addSytech(sytech);
            return Result.success(sytech);
        }
        // 如果无辨证ID则查询疾病对应的所有辨证ID，每个辨证增加一条记录
        else {
            IPage<Map<String, Object>> pages = syndromeService.getSyndromeList(new Page(0, -1), sytech.getDiseaseId(), null, null);
            for (Map<String, Object> map : pages.getRecords()) {
                sytech.setStatus(1);
                sytech.setSyndromeId((Long) map.get("id"));
                Integer res = service.addSytech(sytech);
            }
            return Result.success();
        }
    }


    @ApiOperation(value = "删除适宜方案", notes = "删除适宜方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜方案id", required = true)
    })
    @GetMapping("/delSytech")
    public Result delSytech(Long id) {
        Integer res = service.delSytech(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改适宜方案信息", notes = "修改适宜方案信息")
    @PostMapping("/setSytech")
    public Result setSytech(@RequestBody SchSytech sytech) {
        Integer res = service.setSytech(sytech);
        return Result.success(sytech);
    }


    @ApiOperation(value = "修改适宜方案状态", notes = "修改适宜方案状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜方案id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setSytechStatus")
    public Result setSytechStatus(Long id, Integer status) {
        SchSytech sytech = new SchSytech();
        sytech.setId(id);
        sytech.setStatus(status);
        Integer res = service.setSytech(sytech);
        return Result.success(sytech);
    }


    @ApiOperation(value = "获取适宜方案信息", notes = "获取适宜方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜方案id", required = true)
    })
    @GetMapping("/getSytech")
    public Result getSytech(Long id) {
        SchSytech sytech = service.getSytech(id);
        return Result.success(sytech);
    }


    @ApiOperation(value = "获取适宜方案列表", notes = "获取适宜方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sytechId", value = "适宜技术ID", required = false),
            @ApiImplicitParam(name = "diseaseId", value = "疾病ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getSytechList")
    public Result getSytechList(Long sytechId, Long diseaseId, Integer status, String term,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Map<String, Object>> list = service.getSytechList(new Page(pageNum, pageSize), sytechId, diseaseId, status, term);
        return Result.success(list);
    }

}
