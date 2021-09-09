package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @Value("${file.static-access-path}")
    private String staticAccessPath;

    @Value("${file.upload-folder}")
    private String uploadFolder;


    @ApiOperation(value = "图片展示", notes = "图片展示")
    @ApiImplicitParams({
    })
    @GetMapping("/image/{imageName}")
    public Result image(HttpServletResponse response, @PathVariable String imageName) throws IOException {
        // 判断文件名称格式
        System.out.println(imageName);
        String[] arr = imageName.split("\\.");
        if (arr.length != 2) {
            throw new BusinessException("图片名称错误");
        }
        String name = arr[0];
        String suffix = arr[1];
        // 判断文件是否存在
        File file = new File(uploadFolder +'/'+ imageName);
        if(!file.exists()){
            throw new BusinessException("文件不存在");
        }
        BufferedImage bufferedImage = ImageIO.read(file);
        // 设置响应数据格式
        response.reset();
        response.setContentType("image/" + suffix);
        response.setContentLength((int) file.length());
        // 设置数据流
        OutputStream os = response.getOutputStream();
        ImageIO.write(bufferedImage, suffix, os);
        // 返回结果
        return null;
    }


    @ApiOperation(value = "下载文件", notes = "下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "文件名", required = true),
    })
    @GetMapping("/download")
    public Result download(HttpServletResponse response, @RequestParam String fileName) {
        // 判断文件是否存在
        File file = new File(uploadFolder +'/'+ fileName);
        if(!file.exists()){
            throw new BusinessException("文件不存在");
        }
        // 设置响应数据格式
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 设置数据流
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
        // 返回结果
        return Result.success(fileName);
    }


    @ApiOperation(value = "上传文件", notes = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true),
    })
    @PostMapping(value = "/upload")
    public Result upload(HttpServletRequest request, @RequestParam MultipartFile file) {
        // 判断文件大小
        if (file.isEmpty()) {
            throw new BusinessException("文件大小不能为空");
        }
        // 生成文件名称
        String fileName = file.getOriginalFilename();                               // 获取文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));      // 截取后缀名
        String newName = UUID.randomUUID() + suffixName;
        String fullName = uploadFolder + "/" + newName;
        int end = request.getRequestURL().indexOf("/upload");
        String fullUrl = request.getRequestURL().substring(0, end) + "/image/" + newName;
        // 判断文件目录
        File fd = new File(fullName);
        if (!fd.getParentFile().exists()) {
            fd.getParentFile().mkdirs();
        }
        // 保存文件
        try {
            file.transferTo(fd);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        // 返回结果
        return Result.success(fullUrl);
    }

}
