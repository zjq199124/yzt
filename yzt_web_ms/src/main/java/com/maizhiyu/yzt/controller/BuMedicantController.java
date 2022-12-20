package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuMedicant;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuMedicantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "药材接口")
@RestController
@RequestMapping("/medicant")
public class BuMedicantController {

    @Autowired
    private IBuMedicantService service;


    @ApiOperation(value = "增加药材", notes = "增加药材")
    @PostMapping("/addMedicant")
    public Result addMedicant(@RequestBody BuMedicant medicant) {
        medicant.setStatus(1);
        Integer res = service.addMedicant(medicant);
        return Result.success(medicant);
    }


    @ApiOperation(value = "删除药材", notes = "删除药材")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "药材id", required = true)
    })
    @GetMapping("/delMedicant")
    public Result delMedicant(Long id) {
        Integer res = service.delMedicant(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改药材信息", notes = "修改药材信息")
    @PostMapping("/setMedicant")
    public Result setMedicant(@RequestBody BuMedicant medicant) {
        Integer res = service.setMedicant(medicant);
        return Result.success(medicant);
    }


    @ApiOperation(value = "修改药材状态", notes = "修改药材状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "药材id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setMedicantStatus")
    public Result setMedicantStatus(Long id, Integer status) {
        BuMedicant medicant = new BuMedicant();
        medicant.setId(id);
        medicant.setStatus(status);
        service.setMedicant(medicant);
        return Result.success(medicant);
    }


    @ApiOperation(value = "获取药材信息", notes = "获取药材信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "药材id", required = true)
    })
    @GetMapping("/getMedicant")
    public Result getMedicant(Long id) {
        BuMedicant medicant = service.getMedicant(id);
        return Result.success(medicant);
    }


    @ApiOperation(value = "获取药材列表", notes = "获取药材列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getMedicantList")
    public Result getMedicantList(Integer status, String term,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<BuMedicant> list = service.getMedicantList(new Page(pageNum, pageSize), term);
        return Result.success(list);
    }

    @ApiOperation(value = "获取药材列表(通过名称列表)", notes = "获取药材列表(通过名称列表)")
    @GetMapping("/getMedicantListByNaneList")
    public Result getMedicantList(@RequestBody List<String> namelist) {
        List<BuMedicant> list = service.getMedicantListByNameList(namelist);
        return Result.success(list);
    }

}
