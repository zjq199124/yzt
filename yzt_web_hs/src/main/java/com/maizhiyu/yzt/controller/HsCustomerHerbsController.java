package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.HsCustomerHerbs;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IHsCustomerHerbsService;
import com.maizhiyu.yzt.vo.CustomerHerbsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * className:HsCustomerHerbsController
 * Package:com.maizhiyu.yzt.controller
 * Description:
 *
 * @DATE:2021/12/16 10:36 上午
 * @Author:2101825180@qq.com
 */
@Api(tags = "药材管理接口")
@RestController
@RequestMapping("/herbs")
public class HsCustomerHerbsController extends BaseController {

    @Autowired
    private IHsCustomerHerbsService customerHerbsService;

    @ApiOperation(value = "增加客户药材设置", notes = "增加客户药材设置")
    @ApiImplicitParam(value = "鉴权token", name = "token", paramType = "header", dataType = "String", required = true)
    @PostMapping("/addHsCustomerHerbs")
    public Result addHsCustomerHerbs(@RequestBody HsCustomerHerbs item) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        item.setCustomerId(customerId);
        item.setCreateTime(new Date());
        Integer res = customerHerbsService.addHsCustomerHerbs(item);
        return Result.success();
    }

//    @ApiOperation(value = "删除药材", notes = "删除药材")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "药材id", required = true)
//    })
//    @GetMapping("/delMsHerbs")
//    public Result delMsHerbs(@RequestParam Long id) {
//        Integer res = msHerbsService.delMsHerbs(id);
//        return Result.success();
//    }

    @ApiOperation(value = "修改客户药材设置", notes = "修改客户药材设置")
    @ApiImplicitParam(value = "鉴权token", name = "token", paramType = "header", dataType = "String", required = true)
    @PostMapping("/setHsCustomerHerbs")
    public Result setMsHerbs(@RequestBody HsCustomerHerbs item) {
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        item.setCustomerId(customerId);

        Integer res = customerHerbsService.setHsCustomerHerbs(item);
        return res > 0 ? Result.success() : Result.failure();
    }

    @ApiOperation(value = "获取药材", notes = "获取药材")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户药材ID", required = true)
    })
    @ApiImplicitParam(value = "鉴权token", name = "token", paramType = "header", dataType = "String", required = true)
    @GetMapping("/getHsCustomerHerbs")
    public Result getMsHerbs(@RequestParam Long id) {

        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        CustomerHerbsVO item = customerHerbsService.getHsCustomerHerbs(customerId, id);
        return item != null ? Result.success(item) : Result.failure();
    }

    @ApiOperation(value = "获取药材列表", notes = "获取药材列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "herbsName", value = "药材名称", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false)
    })
    @ApiImplicitParam(value = "鉴权token", name = "token", paramType = "header", dataType = "String", required = true)
    @GetMapping("/getHsCustomerHerbsList")
    public Result getMsHerbsList(HttpServletRequest request, String herbsName,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {


        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        IPage<CustomerHerbsVO> paperList = customerHerbsService.getHsCustomerHerbsList(customerId, herbsName, pageNum, pageSize);
        return Result.success(paperList);
    }


}
