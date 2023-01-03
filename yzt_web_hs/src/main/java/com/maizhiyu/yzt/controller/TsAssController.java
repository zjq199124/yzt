package com.maizhiyu.yzt.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.TsSytechMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.serviceimpl.HsUserService;
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

    @Resource
    private HsUserService hsUserService;

    @Resource
    private TsSytechMapper tsSytechMapper;

    @ApiOperation(value = "获取用户列表" , notes = "获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isTherapist",value = "是否治疗师（0：否 1：是）",required = true),
    })
    @GetMapping("/selectTherapist")
    public  Result selectTherapist(Page page,Long isTherapist){
        IPage<HsUser> list = hsUserService.getTherapist(page,isTherapist);
        return Result.success(list);
    }



    @ApiOperation(value = "增加或修改考核", notes = "增加或修改考核")
    @PostMapping("/addOrupdateAss")

    public Result addOrupdateAss(@RequestBody TsAss ass) {
        Boolean res = tsAssService.saveOrUpdate(ass);
        return Result.success(res);

    }

    @ApiOperation(value = "批量新增考核" , notes = "批量新增考核")
    @PostMapping("addBatch")

    public Result addBatch(@RequestBody List<TsAss> listAss){
        Boolean res = tsAssService.saveBatch(listAss);
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


    @ApiOperation(value = "获取考核列表" , notes = "获取考核列表")
    @ApiImplicitParams({
    })
    @GetMapping("/getAssList")
    public Result getAssList(Page page,TsAss tsAss){
        IPage<TsAss> list =  tsAssService.getAsslist(page,tsAss);
        return Result.success(list);
    }

    @ApiOperation(value = "保存打分(按照列表的形式)" ,notes = "保存打分(按照列表的形式)")
    @PostMapping("/saveAssList")
    public Result saveAss(@RequestBody List<UserAss> userAsslist){
        Boolean res = userAssService.saveBatch(userAsslist);
        return Result.success(res);

    }

    @ApiOperation(value = "查询最终成绩",notes = "查询最终成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assId",value = "考核ID",required = true),
    })
    @GetMapping("/selectAll")
    public Result selectAll(@RequestParam Long assId){
        List<Map<String,Object>> list = userAssService.getUserGrade(assId);
        return Result.success(list);
    }


    @ApiOperation(value = "查询适宜技术对应的考核" , notes = "查询适宜技术对应的考核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sytechId", value = "适宜技术id",required = true),
    })
    @GetMapping("/selectAssBySytech")
    public Result selectAssBySytch(@RequestParam Long sytechId){
        List<TsAssOperation> list = tsAssOperationService.getAssDetail(sytechId);
        return Result.success(list);
    }

    @ApiOperation(value = "查询适宜技术列表" , notes = "查询适宜技术列表")
    @ApiImplicitParams({
    })
    @GetMapping("/selectSytech")
    public Result selectSytech(){
        List<TsSytech> list = tsSytechMapper.selectList(null);
        return Result.success(list);
    }


//    @ApiOperation(value = "查询用户考核结果" , notes = "查询用户考核结果")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "assId",value = "考核ID",required = true),
//    })
//    @GetMapping("/selectAssGrade")
//    public Result getAssGrade(Page page,UserAss userAss){
//        IPage<UserAss> list =  userAssService.getAssGrade(page,userAss);
//        return Result.success(list);
//    }


//    @ApiOperation(value = "根据用户查询考核列表", notes = "根据用户查询考核列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "therapistId",value = "考核对象Id",required = true),
//    })
//    @GetMapping("/selectAssBytherapistId")
//    public Result selectAssBytherapistd(@RequestParam Long therapistId){
//        List<TsAss> list = tsAssService.getAssBytherapistId(therapistId);
//        return Result.success(list);
//    }




}
