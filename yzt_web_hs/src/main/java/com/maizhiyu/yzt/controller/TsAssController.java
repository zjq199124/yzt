package com.maizhiyu.yzt.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TsAss;
import com.maizhiyu.yzt.entity.TsAssess;
import com.maizhiyu.yzt.entity.UserAss;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.serviceimpl.TsAssOperationService;
import com.maizhiyu.yzt.serviceimpl.TsAssService;
import com.maizhiyu.yzt.serviceimpl.UserAssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "操作考核接口")
@RestController
@RequestMapping("/asse")
public class TsAssController {

    @Resource
    private TsAssService tsAssService;

    @Resource
    private UserAssService userAssService;

    @Resource
    private TsAssOperationService tsAssOperationService;

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

    @ApiOperation(value = "获取考核列表" , notes = "获取考核列表")
    @ApiImplicitParams({
    })
    @GetMapping("/getAssList")
    public Result getAssList(Page page){
        IPage<TsAss> list =  tsAssService.getAsslist(page);
        return Result.success(list);
    }

    @ApiOperation(value = "保存打分" ,notes = "保存打分")
    @PostMapping("/saveAss")
    public Result saveAss(@RequestBody UserAss userAss){
        Boolean res = userAssService.save(userAss);
        return Result.success(userAss);

    }


    @ApiOperation(value = "查询适宜技术对应的考核" , notes = "查询适宜技术对应的考核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sytechId", value = "适宜技术id",required = true),
    })
    @GetMapping("/selectAssBySytech")
    public Result selectAssBySytch(@RequestParam Long sytechId){
        List<Map<String ,Object>> list = tsAssOperationService.getAssDetail(sytechId);
        return Result.success(list);
    }

    @ApiOperation(value = "根据用户查询考核列表", notes = "根据用户查询考核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "therapistId",value = "考核对象Id",required = true),
    })
    @GetMapping("/selectAssBytherapistId")
    public Result selectAssBytherapistd(@RequestParam Long therapistId){
        List<TsAss> list = tsAssService.getAssBytherapistId(therapistId);
        return Result.success(list);
    }




}
