package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.entity.MsCustomer;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IHsUserService;
import com.maizhiyu.yzt.service.IMsCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "客户接口")
@RestController
@RequestMapping("/customer")
public class MsCustomerController {

    @Autowired
    private IMsCustomerService service;

    @Autowired
    private IHsUserService hsUserService;


    @ApiOperation(value = "增加客户", notes = "增加客户：管理员密码还没有处理")
    @PostMapping("/addCustomer")
    public Result addCustomer(@RequestBody MsCustomer customer) {
        customer.setStatus(1);
        customer.setCreateTime(new Date());
        customer.setUpdateTime(customer.getCreateTime());
        Integer res = service.addCustomer(customer);
        return Result.success(customer);
    }


    @ApiOperation(value = "删除客户", notes = "删除客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户id", required = true)
    })
    @GetMapping("/delCustomer")
    public Result delCustomer(Long id) {
        Integer res = service.delCustomer(id);
        return Result.success(res);
    }

//    @ApiOperation(value = "test", notes = "test")
//    @GetMapping("/test")
//    public Result test() {
//        service.test();
//        return Result.success();
//    }

    @ApiOperation(value = "修改客户信息", notes = "修改客户信息")
    @PostMapping("/setCustomer")
    public Result setCustomer(@RequestBody MsCustomer customer) {
        customer.setUpdateTime(new Date());
        Integer res = service.setCustomer(customer);
        return Result.success(customer);
    }


    @ApiOperation(value = "修改客户状态", notes = "修改客户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setCustomerStatus")
    public Result setCustomerStatus(Long id, Integer status) {
        MsCustomer customer = new MsCustomer();
        customer.setId(id);
        customer.setStatus(status);
        customer.setUpdateTime(new Date());
        Integer res = service.setCustomer(customer);
        return Result.success(customer);
    }


    @ApiOperation(value = "获取客户信息", notes = "获取客户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户id", required = true)
    })
    @GetMapping("/getCustomer")
    public Result getCustomer(Long id) {
        Map<String, Object> map = service.getCustomer(id);
        return Result.success(map);
    }


    @ApiOperation(value = "获取客户列表", notes = "获取客户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agencyId", value = "代理商ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getCustomerList")
    public Result getCustomerList(Long agencyId, Integer status, String term,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "20") Integer pageSize) {
        IPage<Map<String, Object>> list = service.getCustomerList(new Page(pageNum, pageSize), agencyId, status, term);
        return Result.success(list);
    }


    @ApiOperation(value = "增加管理员", notes = "增加管理员")
    @PostMapping("/addAdmin")
    public Result addAdmin(@RequestBody HsUser user) {
        // 设置角色列表(管理员的roleid为1)
        List<Long> list = new ArrayList<>();
        list.add(1L);
        // 设置用户信息
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String encoded = bcryptPasswordEncoder.encode(user.getPassword());
        user.setStatus(1);
        user.setPassword(encoded);
        user.setRoleList(list);
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        Integer res = hsUserService.addUser(user);
        return Result.success(user);
    }


    @ApiOperation(value = "修改管理员", notes = "修改管理员")
    @PostMapping("/setAdmin")
    public Result setAdmin(@RequestBody HsUser user) {
        user.setUpdateTime(new Date());
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String encoded = bcryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        user.setUpdateTime(user.getCreateTime());
        Integer res = hsUserService.setAdmin(user);
        return Result.success(user);
    }


    @ApiOperation(value = "获取管理员信息", notes = "获取管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户Id", required = true)
    })
    @GetMapping("/getAdmin")
    public Result getAdmin(Long customerId) {
        HsUser user = hsUserService.getAdmin(customerId);
        return Result.success(user);
    }

}
