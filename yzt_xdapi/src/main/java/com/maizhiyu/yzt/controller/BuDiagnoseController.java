package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "诊断接口")
@RestController
@RequestMapping("/diagnose")
public class BuDiagnoseController {

    @Autowired
    private FeignYptClient yptClient;

    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result<BuDiagnoseVO.GetRecommendVO> getRecommend(@RequestBody BuDiagnoseRO.GetRecommendRO ro) {
        // 调用开放接口获取诊断推荐
        Result<BuDiagnoseVO.GetRecommendVO> result = yptClient.getRecommend(ro);
        // 本地化处理
        // TODO：ID映射

        return Result.success(result.getData());
    }

}
