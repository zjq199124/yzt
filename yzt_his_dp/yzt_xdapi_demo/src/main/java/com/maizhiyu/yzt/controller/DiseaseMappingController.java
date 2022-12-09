package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.DiseaseMapping;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.DiseaseMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 焦作妇幼his-云平台-疾病映射表 前端控制器
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-14
 */
@Api(tags = "内部his系统疾病翻译接口")
@RestController
@RequestMapping("/jzfyDiseaseMapping")
public class DiseaseMappingController {

    @Resource
    private DiseaseMappingService diseaseMappingService;

    @ApiOperation(value = "内部his系统疾病映射接口")
    @ApiImplicitParam(name = "hisDiseaseName",value = "his疾病名称",required = true)
    @GetMapping("/jzfy")
    public Result selectDiseaseMappingByHisDiseaseName(String hisDiseaseName) {
        DiseaseMapping diseaseMapping = diseaseMappingService.selectByHisName(hisDiseaseName);
        boolean result = Objects.nonNull(diseaseMapping) ? true : false;
        return Result.success(result);
    }


    @ApiOperation(value = "获取所有疾病列表")
    @ApiImplicitParam(name = "search",value = "搜索字段")
    @GetMapping("/list")
    public Result diseaseList(String search) {
        List<DiseaseMapping> jzfyDiseaseMappingList = diseaseMappingService.diseaseList(search);
        return Result.success(jzfyDiseaseMappingList);
    }

}
