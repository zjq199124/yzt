package com.maizhiyu.yzt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.MsDepartment;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IMsDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "部门接口")
@RestController
@RequestMapping("/department")
public class MsDepartmentController {

    @Autowired
    private IMsDepartmentService service;


    @ApiOperation(value = "增加部门", notes = "增加部门")
    @PostMapping(value = "/addDepartment")
    public Result addDepartment(@RequestBody MsDepartment department) {
        department.setStatus(1);
        Integer res = service.addDepartment(department);
        return Result.success(department);
    }


    @ApiOperation(value = "删除部门", notes = "删除部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true)
    })
    @GetMapping(value = "/delDepartment")
    public Result delDepartment(Long id) {
        Integer res = service.delDepartment(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改部门信息", notes = "修改部门信息")
    @PostMapping(value = "/setDepartment")
    public Result setDepartment(@RequestBody MsDepartment department) {
        Integer res = service.setDepartment(department);
        return Result.success(department);
    }


    @ApiOperation(value = "修改部门状态", notes = "修改部门状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping(value = "/setDepartmentStatus")
    public Result setDepartmentStatus(Long id, Integer status) {
        MsDepartment department = new MsDepartment();
        department.setId(id);
        department.setStatus(status);
        Integer res = service.setDepartment(department);
        return Result.success(department);
    }


    @ApiOperation(value = "获取部门信息", notes = "获取部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true)
    })
    @GetMapping(value = "/getDepartment")
    public Result getDepartment(Long id) {
        MsDepartment department = service.getDepartment(id);
        return Result.success(department);
    }


    @ApiOperation(value = "获取部门列表", notes = "获取部门列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数(默认1)", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小(默认10)", required = false),
    })
    @GetMapping(value = "/getDepartmentList")
    public Result getDepartmentList(Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getDepartmentList(status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }
}
