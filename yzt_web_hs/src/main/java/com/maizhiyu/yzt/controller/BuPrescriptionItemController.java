package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuPrescriptionItemService;
import com.maizhiyu.yzt.service.IBuPrescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Api(tags = "处方子项接口")
@RestController
@RequestMapping("/prescriptionItem")
public class BuPrescriptionItemController {

    @Autowired
    private IBuPrescriptionItemService service;


    @ApiOperation(value = "新增处方子项", notes = "新增处方子项")
    @PostMapping("/addPrescriptionItem")
    public Result addPrescriptionItem(@RequestBody BuPrescriptionItem item) {
        Integer res = service.addPrescriptionItem(item);
        return Result.success(item);
    }


    @ApiOperation(value = "删除处方子项", notes = "删除处方子项")
    @GetMapping("/delPrescriptionItem")
    public Result delPrescriptionItem(Long id) {
        Integer res = service.delPrescriptionItem(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改处方信息", notes = "修改处方信息")
    @PostMapping("/setPrescriptionItem")
    public Result setPrescriptionItem (@RequestBody BuPrescriptionItem item) {
        item.setUpdateTime(new Date());
        Integer res = service.setPrescriptionItem(item);
        return Result.success(item);
    }


    @ApiOperation(value = "获取处方子项列表", notes = "获取处方子项列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prescriptionId", value = "处方ID", required = true),
    })
    @GetMapping("/getPrescriptionItemList")
    public Result getPrescriptionItemList(Long prescriptionId) {
        List<BuPrescriptionItem> list = service.getPrescriptionItemList(prescriptionId);
        return Result.success(list);
    }

}
