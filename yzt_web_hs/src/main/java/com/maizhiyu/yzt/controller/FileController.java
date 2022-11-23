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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Api(tags = "文件接口")
@RestController
@RequestMapping("/file")
public class FileController {

//    @Autowired
//    OSSKit ossKit;

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
        String imageUrl = requestUrl.replace(servletPath, "") + pathname;
        // 保存文件
        service.upload(file, pathname);
        // 返回结果
        return Result.success(imageUrl);

//        String s = uploadFile(file, false);
//        // 返回结果
//        return Result.success();

    }


    @ApiOperation(value = "上传报告", notes = "上传报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true),
    })
    @PostMapping(value = "/uploadCheck")
    public Result uploadCheck(HttpServletRequest request, @RequestParam MultipartFile file) {
        // 获取文件名称
        String fileName = file.getOriginalFilename();                               // 获取文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));      // 截取后缀名
        // 生成文件路径
        String date = MyDate.getTodayStr().replace("-", "");
        String pathname = "/file/check/" + date + "/" + UUID.randomUUID() + suffixName;
        // 生成文件URL
        String servletPath = request.getServletPath();
        String requestUrl = request.getRequestURL().toString();
        String fileUrl = requestUrl.replace(servletPath, "") + pathname;
        System.out.println("##### " + fileUrl);
        // 保存文件
        service.upload(file, pathname);
//        String s = uploadFile(file, false);
        // 返回结果
        return Result.success(fileUrl);
    }


//    /**
//     * 上传文件
//     * @param file
//     * @param isPrivate
//     * @return
//     */
//    private String uploadFile(MultipartFile file,boolean isPrivate) {
//        String originalFilename = file.getOriginalFilename();
//        int index = originalFilename.   lastIndexOf(".");
//        String suffix = "";
//        if (index >= 0) {
//            suffix = originalFilename.substring(index);
//        }
//        String fileName = "zd/" + UUID.randomUUID() + System.currentTimeMillis() + suffix;
//        return upload(file, isPrivate, fileName, originalFilename);
//    }
//
//    private String upload(MultipartFile file, boolean isPrivate, String fileName, String originName) {
//        try {
////            String url = ossKit.uploadInputStream(file.getInputStream(), fileName, isPrivate);
////            Map map = new HashMap();
////            map.put("url", url);
////            map.put("fileName", fileName);
////            map.put("originName",originName);
//            return url;
//        } catch (IOException e) {
//            throw new BusinessException("上传失败");
//        }
//    }


    @ApiOperation(value = "下载文件", notes = "下载文件")
    @GetMapping("/check/**")
    public Result download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathname = request.getServletPath();
        service.download(response, pathname);
        return null;
    }


    @ApiOperation(value = "展示图片", notes = "展示图片")
    @GetMapping("/image/**")
    public Result image(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathname = request.getServletPath();
        service.image(response, pathname);
        return null;
    }

}
