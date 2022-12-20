package com.maizhiyu.yzt.controller;


import com.maizhiyu.yzt.entity.MsCustomer;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.MsCustomerTimeslotRO;
import com.maizhiyu.yzt.service.IMsCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


@Api(tags = "客户接口")
@RestController
@RequestMapping("/customer")
public class MsCustomerController {

    @Autowired
    private IMsCustomerService service;


    @ApiOperation(value = "获取客户信息", notes = "获取客户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户ID", required = true),
    })
    @GetMapping("/getCustomer")
    public Result getCustomer(Long id) {
        Map<String, Object> map = service.getCustomer(id);
        return Result.success(map);
    }


    @ApiOperation(value = "修改预约时段", notes = "修改预约时段")
    @PostMapping("/setCustomerTimeslot")
    public Result setCustomerTimeslot(@RequestBody MsCustomerTimeslotRO ro) {

//        // 校验格式
//        String timeslot = ro.getTimeslot();
//        String[] array = timeslot.split(",");
//        for (String item : array) {
//            try {
//                Date data = DateUtils.parseDate(item, "HH:mm");
//            } catch (ParseException e) {
//                return Result.failure(10000, "预约时段格式错误 " + item);
//            }
//        }

        // 添加设置
        MsCustomer customer = new MsCustomer();
        customer.setId(ro.getId());
        customer.setTimeslot(ro.getTimeslot());
        customer.setUpdateTime(new Date());
        Integer res = service.setCustomer(customer);
        return Result.success(res);
    }

}
