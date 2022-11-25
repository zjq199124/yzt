package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.enums.CheckTypeEnum;
import com.maizhiyu.yzt.enums.OSSCatalogEnum;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.SysMultimediaService;
import com.maizhiyu.yzt.serviceimpl.BuCheckService;
import com.maizhiyu.yzt.serviceimpl.BuOutpatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "经络检查物联接口")
@RestController
@RequestMapping("/jinluo")
@Slf4j
public class TeJinLuoController {

    @Resource
    SysMultimediaService sysMultimediaService;
    @Resource
    BuCheckService buCheckService;

    @ApiOperation(value = "经络检测文件接收上传", notes = "经络检测文件接收上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true),
    })
    @PostMapping(value = "/receiveFile")
    public Result<String> receiveFile(@RequestParam MultipartFile file, String patientPhone) {
        try {
            SysMultimedia sysMultimedia = sysMultimediaService.saveMultimedia(file, OSSCatalogEnum.MERIDIAN.getPath(), true, "红外检查报告");
            //保存患者检查数据
            BuCheck buCheck = new BuCheck();
            buCheck.setType(CheckTypeEnum.MERIDIAN.getCode());
            buCheck.setMultimediaId(sysMultimedia.getId());
            buCheck.setCreateTime(new Date());
            buCheck.setMobile(patientPhone);
            buCheckService.addCheck(buCheck);
            return Result.success(sysMultimediaService.saveMultimedia(file, OSSCatalogEnum.MERIDIAN.getPath(), true, "红外检查报告"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
