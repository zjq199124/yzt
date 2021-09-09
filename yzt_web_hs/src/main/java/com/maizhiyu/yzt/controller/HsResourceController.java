package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.HsResource;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IHsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "资源接口")
@RestController
@RequestMapping("/resource")
public class HsResourceController {

    @Autowired
    private IHsResourceService service;


    @ApiOperation(value = "获取所有资源列表", notes = "获取所有资源列表")
    @GetMapping("/getResourceList")
    public Result getResourceList() {
        List<Map<String, Object>> list = service.getResourceList();
        return Result.success(list);
    }


    @ApiOperation(value = "获取角色资源列表", notes = "获取角色资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true),
    })
    @GetMapping("/getRoleResourceList")
    public Result getRoleResourceList(Long roleId) {
        List<Map<String, Object>> list = service.getRoleResourceList(roleId);
        return Result.success(list);
    }


    @ApiOperation(value = "获取用户资源列表", notes = "获取用户资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
    })
    @GetMapping("/getUserResourceList")
    public Result getUserResourceList(Long userId) {
        List<Map<String, Object>> list = service.getUserResourceList(userId);
        return Result.success(list);
    }
}
