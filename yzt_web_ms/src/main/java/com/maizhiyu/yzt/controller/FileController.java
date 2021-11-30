package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IFileService;
import com.maizhiyu.yzt.utils.MyDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.UUID;

@Api(tags = "文件接口")
@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private IFileService service;


    @ApiOperation(value = "上传图片", notes = "上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true),
    })
    @PostMapping(value = "/uploadImage")
    public Result uploadImage(HttpServletRequest request, @RequestParam MultipartFile file) {
        // 获取文件名称
        String fileName = file.getOriginalFilename();                               // 获取文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));      // 截取后缀名
        // 生成文件路径
        String date = MyDate.getTodayStr().replace("-", "");
        String pathname = "/file/image/" + date + "/" + UUID.randomUUID() + suffixName;
        // 生成文件URL
        String servletPath = request.getServletPath();
        String requestUrl = request.getRequestURL().toString();
        String imageUrl = requestUrl.replace(servletPath, "") + "/files" + pathname;
        // 保存文件
        service.upload(file, pathname);
        // 返回结果
        return Result.success(imageUrl);
    }


    @ApiOperation(value = "展示图片", notes = "展示图片")
    @GetMapping("/image/**")
    public Result image(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathname = request.getServletPath();
        service.image(response, pathname);
        return null;
    }

}
