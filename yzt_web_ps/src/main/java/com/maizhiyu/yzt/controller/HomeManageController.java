package com.maizhiyu.yzt.controller;


import com.maizhiyu.yzt.entity.PsFamily;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.serviceimpl.PsFamilyService;
import com.maizhiyu.yzt.serviceimpl.PsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "家庭管理接口")
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeManageController {

    @Resource
    private PsUserService psUserService;

    @Resource
    private PsFamilyService psFamilyService;

    @ApiOperation(value = "获取家庭信息", notes = "获取家庭信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
    })
    @GetMapping("/getFamily")
    public Result getFamily(@RequestParam Long userId) {
        List<PsFamily> list = psFamilyService.getFamily(userId);
        return Result.success(list);
    }

    @ApiOperation(value = "添加或者修改家人信息", notes = "添加或者修改家人信息")
    @PostMapping("/addOrUpdateFamily")
    public Result addFamily(@RequestBody PsFamily psFamily) {
        Boolean res = psFamilyService.addFamily(psFamily);
        return Result.success(res);
    }

//    @ApiOperation(value = "删除家庭成员", notes = "删除家庭成员")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId" ,value = "当前用户id",required = true),
//            @ApiImplicitParam(name = "familyId" , value = "关联用户id", required = true),
//    })
//    @GetMapping("/delFamily")
//    public Result delFamily(@RequestParam Long userId,@RequestParam Long familyId) {
//        RelUser one = relUserService.query().eq("user_id", userId)
//                .eq("another_user_id", familyId).one();
//        relUserService.removeById(one);
//        psFamilyService.removeById(familyId);
//        return Result.success();
//
//    }
//
//    @ApiOperation(value = "修改家庭成员信息",notes = "修改家庭信息")
//    @PostMapping("/updateFamily")
//    public Result updateFamily(@RequestBody FamilyVo familyVo ){
//        Boolean res = relUserService.updateFamily(familyVo);
//        return Result.success();
//
//    }


}
