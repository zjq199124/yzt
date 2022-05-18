package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuDiagnoseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@Api(tags = "诊断接口")
@RestController
@RequestMapping("/diagnose")
public class BuDiagnoseController {

    @Autowired
    private IBuDiagnoseService service;


    @ApiOperation(value = "获取诊断列表", notes = "获取诊断列表")
    @PostMapping("/getDiagnoseList")
    public Result<BuDiagnose> getDiagnoseList (
            @RequestParam @NotNull Long customerId,
            @RequestParam @NotNull String start,
            @RequestParam @NotNull String end) {
        List<Map<String, Object>> list = service.getDiagnoseList(customerId, start, end);
        return Result.success(list);
    }

}
