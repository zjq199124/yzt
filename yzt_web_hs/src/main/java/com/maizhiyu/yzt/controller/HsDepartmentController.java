package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.HsDepartment;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IHsDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Api(tags = "科室接口")
@RestController
@RequestMapping("/department")
public class HsDepartmentController {

    @Autowired
    private IHsDepartmentService service;


    @ApiOperation(value = "增加科室", notes = "增加科室")
    @PostMapping(value = "/addDepartment")
    public Result addDepartment(@RequestBody HsDepartment department) {
        department.setStatus(1);
        department.setCreateTime(new Date());
        department.setUpdateTime(department.getCreateTime());
        Integer res = service.addDepartment(department);
        return Result.success(department);
    }


    @ApiOperation(value = "删除科室", notes = "删除科室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "科室id", required = true)
    })
    @GetMapping(value = "/delDepartment")
    public Result delDepartment(Long id) {
        Integer res = service.delDepartment(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改科室信息", notes = "修改科室信息")
    @PostMapping(value = "/setDepartment")
    public Result setDepartment(@RequestBody HsDepartment department) {
        department.setUpdateTime(new Date());
        Integer res = service.setDepartment(department);
        return Result.success(department);
    }


    @ApiOperation(value = "修改科室状态", notes = "修改科室状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "科室id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping(value = "/setDepartmentStatus")
    public Result setDepartmentStatus(Long id, Integer status) {
        HsDepartment department = new HsDepartment();
        department.setId(id);
        department.setStatus(status);
        department.setUpdateTime(new Date());
        Integer res = service.setDepartment(department);
        return Result.success(department);
    }


    @ApiOperation(value = "获取科室信息", notes = "获取科室信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "科室id", required = true)
    })
    @GetMapping(value = "/getDepartment")
    public Result getDepartment(Long id) {
        HsDepartment department = service.getDepartment(id);
        return Result.success(department);
    }


    @ApiOperation(value = "获取科室列表", notes = "获取科室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数(默认1)", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小(默认10)", required = false),
    })
    @GetMapping(value = "/getDepartmentList")
    public Result getDepartmentList(Long customerId, Integer status, String term,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Map<String, Object>> list = service.getDepartmentList(new Page(pageNum, pageSize), customerId, status, term);
        return Result.success(list);
    }
}
