package com.maizhiyu.yzt.controller;

import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.MsHerbs;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IMsHerbsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * className:MsHerbsController
 * Package:com.maizhiyu.yzt.controller
 * Description:
 *
 * @DATE:2021/12/16 9:15 上午
 * @Author:2101825180@qq.com
 */

@Api(tags = "药材管理接口")
@RestController
@RequestMapping("/herbs")
public class MsHerbsController {

    @Autowired
    private IMsHerbsService msHerbsService;

    @ApiOperation(value = "增加药材", notes = "增加药材")
    @PostMapping("/addMsHerbs")
    public Result addMsHerbs(@RequestBody MsHerbs item) {
        item.setCreateTime(new Date());
        Integer res = msHerbsService.addMsHerbs(item);
        return Result.success();
    }

    @ApiOperation(value = "删除药材", notes = "删除药材")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "药材id", required = true)
    })
    @GetMapping("/delMsHerbs")
    public Result delMsHerbs(@RequestParam Long id) {
        Integer res = msHerbsService.delMsHerbs(id);
        return Result.success();
    }

    @ApiOperation(value = "修改药材", notes = "修改药材")
    @PostMapping("/setMsHerbs")
    public Result setMsHerbs(@RequestBody MsHerbs item) {
        Integer res = msHerbsService.setMsHerbs(item);
        return res > 0 ? Result.success(item) : Result.failure();
    }

    @ApiOperation(value = "获取药材", notes = "获取药材")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "药材id", required = true)
    })
    @GetMapping("/getMsHerbs")
    public Result getMsHerbs(@RequestParam Long id) {
        MsHerbs item = msHerbsService.getMsHerbs(id);
        return item != null ? Result.success(item) : Result.failure();
    }

    @ApiOperation(value = "获取药材列表", notes = "获取药材列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "herbsName", value = "药材名称", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false)
    })
    @GetMapping("/getMsHerbsList")
    public Result getMsHerbsList(String herbsName,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<MsHerbs> paperList = msHerbsService.getMsHerbsList(herbsName,pageNum,pageSize,null);
        return Result.success(paperList);
    }


}
