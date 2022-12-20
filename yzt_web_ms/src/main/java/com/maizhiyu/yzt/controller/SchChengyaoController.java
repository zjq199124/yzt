package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.SchChengyao;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.ISchChengyaoService;
import com.maizhiyu.yzt.service.ISynSyndromeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(tags = "成药方案接口")
@RestController
@RequestMapping("/chengyao")
public class SchChengyaoController {

    @Autowired
    private ISchChengyaoService service;

    @Autowired
    private ISynSyndromeService syndromeService;


    @ApiOperation(value = "增加成药方案", notes = "增加成药方案")
    @PostMapping("/addChengyao")
    public Result addChengyao(@RequestBody SchChengyao chengyao) {
        // 如果有辨证ID则只增加一调记录
        if (chengyao.getSyndromeId() != null) {
            chengyao.setStatus(1);
            Integer res = service.addChengyao(chengyao);
            return Result.success(chengyao);
        }
        // 如果无辨证ID则查询疾病对应的所有辨证ID，每个辨证增加一条记录
        else {
            IPage<Map<String, Object>> pages = syndromeService.getSyndromeList(null, chengyao.getDiseaseId(), null, null);
            for (Map<String, Object> map : pages.getRecords()) {
                chengyao.setStatus(1);
                chengyao.setSyndromeId((Long) map.get("id"));
                Integer res = service.addChengyao(chengyao);
            }
            return Result.success();
        }
    }


    @ApiOperation(value = "删除成药方案", notes = "删除成药方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成药方案id", required = true)
    })
    @GetMapping("/delChengyao")
    public Result delChengyao(Long id) {
        Integer res = service.delChengyao(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改成药方案信息", notes = "修改成药方案信息")
    @PostMapping("/setChengyao")
    public Result setChengyao(@RequestBody SchChengyao chengyao) {
        Integer res = service.setChengyao(chengyao);
        return Result.success(chengyao);
    }


    @ApiOperation(value = "修改成药方案状态", notes = "修改成药方案状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成药方案id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setChengyaoStatus")
    public Result setChengyaoStatus(Long id, Integer status) {
        SchChengyao chengyao = new SchChengyao();
        chengyao.setId(id);
        chengyao.setStatus(status);
        Integer res = service.setChengyao(chengyao);
        return Result.success(chengyao);
    }


    @ApiOperation(value = "获取成药方案信息", notes = "获取成药方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成药方案id", required = true)
    })
    @GetMapping("/getChengyao")
    public Result getChengyao(Long id) {
        SchChengyao chengyao = service.getChengyao(id);
        return Result.success(chengyao);
    }


    @ApiOperation(value = "获取成药方案列表", notes = "获取成药方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", value = "疾病ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getChengyaoList")
    public Result getChengyaoList(Long diseaseId, Integer status, String term,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Map<String, Object>> pages = service.getChengyaoList(new Page(pageNum, pageSize), diseaseId, status, term);
        return Result.success(pages);
    }

}
