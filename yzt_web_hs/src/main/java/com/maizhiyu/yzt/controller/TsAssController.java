package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.TsAss;
import com.maizhiyu.yzt.entity.TsAssess;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.serviceimpl.TsAssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "操作考核接口")
@RestController
@RequestMapping("/asse")
public class TsAssController {

    @Resource
    private TsAssService tsAssService;

    @ApiOperation(value = "增加考核", notes = "增加考核")
    @PostMapping("/addAss")

    public Result addAss(@RequestBody TsAss ass) {
        Boolean res = tsAssService.save(ass);
        return Result.success(res);

    }

    @ApiOperation(value = "删除考核" , notes = "删除考核")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id",value = "考核ID",required = true)
        })
    @GetMapping("/delAss")
    public Result delAss(@RequestParam Long id){
        Boolean res = tsAssService.removeById(id);
        return Result.success(res);
    }

    @ApiOperation(value = "修改考核" , notes = "修改考核")
    @PostMapping("/setAss")
    public Result setAss(@RequestBody TsAss ass){
        Boolean res = tsAssService.saveOrUpdate(ass);
        return Result.success(res);
    }

    @ApiOperation(value = "获取考核" , notes = "获取考核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id" , value = "考核id" , required = true)
    })
    @GetMapping("/getAss")
    public Result getAss(@RequestParam Long id){
        TsAss res =  tsAssService.getById(id);
        return Result.success(res);
    }

    @ApiOperation(value = "获取考核列表" , notes = "获取考核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "therapistId", value = "考核对象ID", required = true),
            @ApiImplicitParam(name = "sytechId", value = "技术id", required = true),
            @ApiImplicitParam(name = "creatTime", value = "开始日期", required = false),
            @ApiImplicitParam(name = "endTime", value = "结束日期", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getAsslist")
    public Result getAsslist(Long therapistId,Long sytechId,String creatTime,String endTime,String term,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = tsAssService.getAsslist(therapistId,sytechId,creatTime,endTime,term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "保存打分" ,notes = "保存打分")
    @




}
