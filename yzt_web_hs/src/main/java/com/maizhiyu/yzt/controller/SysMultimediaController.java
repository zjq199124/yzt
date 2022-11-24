package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.SysMultimediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 多媒体数据接口
 */
@RestController
@Api(tags = "多媒体数据接口")
@RequestMapping("/multimedia")
public class SysMultimediaController {

    @Resource
    SysMultimediaService sysMultimediaService;

    @ApiOperation(value = "获取多媒体数据详情", notes = "获取多媒体数据详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "多媒体id", required = true, dataTypeClass = Long.class),
    })
    @GetMapping("/getMultimediaInfo")
    public Result getMultimediaInfo(Long id) {
        SysMultimedia sysMultimedia = sysMultimediaService.getMultimedia(id);
        return Result.success(sysMultimedia);
    }
}
