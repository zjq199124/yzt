package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.JzfyDiseaseMapping;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.JzfyDiseaseMappingService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 焦作妇幼his-云平台-疾病映射表 前端控制器
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-14
 */
@RestController
@RequestMapping("/jzfyDiseaseMapping")
public class JzfyDiseaseMappingController {

    @Resource
    private JzfyDiseaseMappingService jzfyDiseaseMappingService;

    @ApiOperation(value = "焦作妇幼疾病映射接口")
    @ApiImplicitParam(name = "hisDiseaseName",value = "his疾病名称",required = true)
    @GetMapping("/jzfy")
    public Result<Long> selectDiseaseMappingByHisDiseaseName(String hisDiseaseName) {
        JzfyDiseaseMapping jzfyDiseaseMapping = jzfyDiseaseMappingService.selectByHisName(hisDiseaseName);
        return Result.success(jzfyDiseaseMapping);
    }

}
