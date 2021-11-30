package com.maizhiyu.yzt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IHsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class HsUserController {

    @Autowired
    private IHsUserService service;


    @ApiOperation(value = "增加用户", notes = "增加用户")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody HsUser user) {
        String password;
        // 密码为空时使用默认密码123456
        if (user.getPassword() == null || user.getPassword().trim().length() == 0) {
            password = "123456";
        } else {
            password = user.getPassword();
        }
        // 密码加密处理
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String encoded = bcryptPasswordEncoder.encode(password);
        user.setPassword(encoded);
        // 设置其他数据
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
    public Result setUser(@RequestBody HsUser user) {
        if (user.getPassword() != null && user.getPassword().trim().length()>0) {
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
    public Result setUserBasic(@RequestBody HsUser user) {
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
        HsUser user = new HsUser();
        user.setId(id);
        user.setStatus(status);
        user.setUpdateTime(new Date());
        Integer res = service.setUserStatus(user);
        return Result.success(user);
    }


    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true)
    })
    @GetMapping("/getUser")
    public Result getUser(Long id) {
        Map<String, Object> user = service.getUser(id);
        return Result.success(user);
    }


    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = false),
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态(0:停用,1:启用)", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数(默认1)", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小(默认10)", required = false),
    })
    @GetMapping("/getUserList")
    public Result getUserList(Long customerId, Long departmentId, Long roleId, Integer status, String term,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getUserList(customerId, departmentId, roleId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取医生列表", notes = "获取医生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态(0:停用,1:启用)", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数(默认1)", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小(默认10)", required = false),
    })
    @GetMapping("/getDoctorList")
    public Result getDoctorList(Long customerId, Long departmentId, Integer status, String term,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getDoctorList(customerId, departmentId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取治疗师列表", notes = "获取治疗师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "departmentId", value = "部门ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态(0:停用,1:启用)", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数(默认1)", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小(默认10)", required = false),
    })
    @GetMapping("/getTherapistList")
    public Result getTherapistList(Long customerId, Long departmentId, Integer status, String term,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getTherapistList(customerId, departmentId, status, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }

}
