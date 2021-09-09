package com.maizhiyu.yzt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IHsDepartmentService;
import com.maizhiyu.yzt.service.IMsCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "医院接口")
@RestController
@RequestMapping("/customer")
public class MsCustomerController {

    @Autowired
    private IMsCustomerService customerService;

    @Autowired
    private IHsDepartmentService departmentService;


    @ApiOperation(value = "获取医院列表", notes = "获取医院列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province", value = "省", required = false),
            @ApiImplicitParam(name = "city", value = "市", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getCustomerList")
    public Result getCustomerList(String province, String city, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = customerService.getCustomerList(province, city, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取医院科室列表", notes = "获取医院科室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "医院ID", required = true),
    })
    @GetMapping("/getCustomerDepartmentList")
    public Result getCustomerDepartmentList(Long customerId) {
        List<Map<String, Object>> list = departmentService.getDepartmentList(customerId, 1, null);
        return Result.success(list);
    }


    @ApiOperation(value = "获取医院预约时段列表", notes = "获取医院预约时段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "医院ID", required = true),
    })
    @GetMapping("/getCustomerTimeslotList")
    public Result getCustomerTimeslotList(Long customerId) {
        List<String> list = customerService.getCustomerTimeslotList(customerId);
        return Result.success(list);
    }


    @ApiOperation(value = "获取医院的省份列表", notes = "获取医院的省份列表")
    @ApiImplicitParams({})
    @GetMapping("/getCustomerProvinceList")
    public Result getProvinceCoveredList() {
        List<Map<String, Object>> list = customerService.getCustomerProvinceList();
        return Result.success(list);
    }


    @ApiOperation(value = "获取医院的城市列表", notes = "获取医院的城市列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province", value = "省份", required = true),
    })
    @GetMapping("/getCustomerCityList")
    public Result getCustomerCityList(String province) {
        List<Map<String, Object>> list = customerService.getCustomerCityList(province);
        return Result.success(list);
    }
}
