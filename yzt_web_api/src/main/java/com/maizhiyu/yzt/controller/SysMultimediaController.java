package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.enums.FileTypeEnum;
import com.maizhiyu.yzt.enums.OSSCatalogEnum;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.SysMultimediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Type;

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

    @ApiOperation(value = "上传多媒体文件", notes = "上传多媒体文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "文件类型", readOnly = true, dataTypeClass = Integer.class),
    })
    @PostMapping("/saveMultimediaInfo")
    public Result<SysMultimedia> saveMultimediaInfo(@RequestParam MultipartFile file, @RequestParam("type") Integer type) {
        String path = FileTypeEnum.getPathByCode(type);
        String remark = FileTypeEnum.getNameByCode(type);
        SysMultimedia sysMultimedia = sysMultimediaService.saveMultimedia(file, path, false, remark, type);

        return Result.success(sysMultimedia);
    }
}
