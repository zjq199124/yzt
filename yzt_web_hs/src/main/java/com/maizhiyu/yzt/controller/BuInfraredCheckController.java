package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.TxInfraredDataService;
import com.maizhiyu.yzt.vo.InfraredCheckVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


@Api(tags = "红外检查接口")
@RestController
@RequestMapping("/infraredCheck")
public class BuInfraredCheckController {

    @Resource
    TxInfraredDataService txInfraredDataService;

    @ApiOperation(value = "获取红外检测比对列表和用户检测所有的日期", notes = "获取红外检测比对列表和用户检测所有的日期")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "idCard", value = "身份证号", required = false, dataTypeClass = String.class),
    })
    @GetMapping("/getCheckAndDate")
    public Result<Map<String, Object>> getCheckAndDate(String mobile, String idCard) {
        return Result.success(txInfraredDataService.getCheckAndDate(mobile, idCard));
    }



    @ApiOperation(value = "获取红外检测数据内容", notes = "获取红外检测数据内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "part", value = "检测部位", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "date", value = "查询日期", required = true, dataTypeClass = String.class),
    })
    @GetMapping("/getInfrareDateCheck")
    public Result<InfraredCheckVO> getInfrareDateCheck(String part, String date) {
        return Result.success(txInfraredDataService.getInfrareDateCheck(part, date));
    }
}
