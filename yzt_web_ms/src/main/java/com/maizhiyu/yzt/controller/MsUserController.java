package com.maizhiyu.yzt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.MsDepartment;
import com.maizhiyu.yzt.entity.MsUser;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IMsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "用户接口：密码处理暂未处理")
@RestController
@RequestMapping("/user")
public class MsUserController {

    @Autowired
    private IMsUserService service;


    @ApiOperation(value = "增加用户", notes = "增加用户")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody MsUser user) {
        // TODO: 临时用默认密码
        user.setPassword("123456");
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String encoded = bcryptPasswordEncoder.encode(user.getPassword());
        user.setStatus(1);
        user.setPassword(encoded);
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        Integer res = service.addUser(user);
        return Result.success(user);
    }


    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true)
    })
    @GetMapping("/delUser")
    public Result delUser(Long id) {
        Integer res = service.delUser(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @PostMapping("/setUser")
    public Result setUser(@RequestBody MsUser user) {
        if (user.getPassword() != null) {
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            String encoded = bcryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encoded);
        }
        user.setUpdateTime(new Date());
        Integer res = service.setUser(user);
        return Result.success(user);
    }


    @ApiOperation(value = "修改用户基础信息", notes = "修改用户基础信息")
    @PostMapping("/setUserBasic")
    public Result setUserBasic(@RequestBody MsUser user) {
        if (user.getPassword() != null) {
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            String encoded = bcryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encoded);
        }
        user.setUpdateTime(new Date());
        Integer res = service.setUserBasic(user);
        return Result.success(res);
    }


    @ApiOperation(value = "修改用户状态", notes = "修改用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setUserStatus")
    public Result setUserStatus(Long id, Integer status) {
        MsUser user = new MsUser();
        user.setId(id);
        user.setStatus(status);
        Integer res = service.setUserBasic(user);
        return Result.success(res);
    }


    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true)
    })
    @GetMapping("/getUser")
    public Result getUser(Long id) {
        MsUser user = service.getUser(id);
        return Result.success(user);
    }


    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = false),
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态(0:停用,1:启用)", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数(默认1)", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小(默认10)", required = false),
    })
    @GetMapping("/getUserList")
    public Result getUserList(Long departmentId, Long roleId, Integer status, String term,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getUserList(departmentId, roleId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }

}
