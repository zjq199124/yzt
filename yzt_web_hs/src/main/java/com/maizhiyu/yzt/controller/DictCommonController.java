package com.maizhiyu.yzt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.DictCommon;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IDictCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "字典接口")
@RestController
@RequestMapping("/dict")
public class DictCommonController {

    @Autowired
    private IDictCommonService service;


    @ApiOperation(value = "增加项目", notes = "增加项目")
    @PostMapping("/addItem")
    public Result addItem(@RequestBody DictCommon item) {
        item.setStatus(1);
        Integer res = service.addItem(item);
        return Result.success(item);
    }


    @ApiOperation(value = "删除项目", notes = "删除项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目id", required = true)
    })
    @GetMapping("/delItem")
    public Result delItem(Long id) {
        Integer res = service.delItem(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改项目信息", notes = "修改项目信息")
    @PostMapping("/setItem")
    public Result setItem(@RequestBody DictCommon item) {
        Integer res = service.setItem(item);
        return Result.success(item);
    }


    @ApiOperation(value = "修改项目状态", notes = "修改项目状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @PostMapping("/setItemStatus")
    public Result setItemStatus(Long id, Integer status) {
        DictCommon item = new DictCommon();
        item.setId(id);
        item.setStatus(status);
        Integer res = service.setItem(item);
        return Result.success(item);
    }


    @ApiOperation(value = "获取项目列表", notes = "获取项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cate", value = "类别", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数(默认1)", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小(默认10)", required = false),
    })
    @GetMapping("/getItemList")
    public Result getItemList(String cate, Integer status, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getItemList(cate, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取类别列表", notes = "获取类别列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
    })
    @GetMapping("/getCateList")
    public Result getItemList(Integer status, String term) {
        List<Map<String, Object>> list = service.getCateList(status, term);
        return Result.success(list);
    }


    @ApiOperation(value = "获取设备类型列表", notes = "获取设备类型列表")
    @ApiImplicitParams({})
    @GetMapping("/getEquipTypeList")
    public Result getEquipTypeList() {
        List<Map<String, Object>> list = service.getEquipTypeList();
        return Result.success(list);
    }


    @ApiOperation(value = "获取设备维护类型列表", notes = "获取设备维护类型列表")
    @ApiImplicitParams({})
    @GetMapping("/getMaintainTypeList")
    public Result getMaintainTypeList() {
        List<Map<String, Object>> list = service.getMaintainTypeList();
        return Result.success(list);
    }
}
