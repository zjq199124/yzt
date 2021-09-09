package com.maizhiyu.yzt.controller;


import com.maizhiyu.yzt.entity.PsOpinion;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IPsOpinionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Api(tags = "意见接口")
@RestController
@RequestMapping("/opinion")
public class PsOpinionController {

    @Autowired
    private IPsOpinionService service;


    @ApiOperation(value = "增加意见", notes = "增加意见")
    @PostMapping("/addOpinion")
    public Result addOpinion(PsOpinion opinion) {
        opinion.setCreateTime(new Date());
        Integer res = service.addOpinion(opinion);
        return Result.success(opinion);
    }


    @ApiOperation(value = "获取意见信息", notes = "获取意见信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "意见ID", required = true),
    })
    @GetMapping("/getOpinion")
    public Result getOpinion(Long id) {
        PsOpinion opinion = service.getOpinion(id);
        return Result.success(opinion);
    }
    

    @ApiOperation(value = "获取意见列表", notes = "获取意见列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", required = true),
    })
    @GetMapping("/getOpinionList")
    public Result getOpinionList(Long uid) {
        List<PsOpinion> list = service.getOpinionList(uid);
        return Result.success(list);
    }

}
