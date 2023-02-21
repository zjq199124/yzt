package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.HsRole;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IHsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/role")
public class HsRoleController {

    @Autowired
    private IHsRoleService service;


    @ApiOperation(value = "增加角色", notes = "增加角色")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody HsRole role) {
        role.setStatus(1);
        role.setCreateTime(new Date());
        role.setUpdateTime(role.getCreateTime());
        Integer res = service.addRole(role);
        return Result.success(role);
    }


    @ApiOperation(value = "删除角色", notes = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true)
    })
    @GetMapping("/delRole")
    public Result delRole(Long id) {
        Result res = service.delRole(id);
        return res;
    }


    @ApiOperation(value = "修改角色信息", notes = "修改角色信息")
    @PostMapping("/setRole")
    public Result setRole(@RequestBody HsRole role) {
        Boolean res = service.setRole(role);
        return Result.success(role);
    }


    @ApiOperation(value = "修改角色状态", notes = "修改角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setRoleStatus")
    public Result setRoleStatus(Long id, Integer status) {
        HsRole role = new HsRole();
        role.setId(id);
        role.setStatus(status);
        role.setUpdateTime(new Date());
        Integer res = service.setRoleStatus(role);
        return Result.success(role);
    }


    @ApiOperation(value = "获取角色信息", notes = "获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true)
    })
    @GetMapping("/getRole")
    public Result getRole(Long id) {
        HsRole role = service.getRole(id);
        return Result.success(role);
    }


    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数(默认1)", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小(默认10)", required = false),
    })
    @GetMapping("/getRoleList")
    public Result getRoleList(Long customerId, Integer status, String term,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Map<String, Object>> list = service.getRoleList(new Page(pageNum, pageSize), customerId, status, term);
        return Result.success(list);
    }
}
