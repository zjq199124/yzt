package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.enums.FileSaveTypeEnum;
import com.maizhiyu.yzt.utils.ossKit.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Api(tags = "文件接口")
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    AliOssUtil aliOssUtil;

    @ApiOperation("获取文件输出流")
    @GetMapping("/getFileOutputStream")
    public void transfer(HttpServletResponse res, @RequestParam(value = "filePath") String filePath) throws Exception {
        String url=aliOssUtil.generatePresignedUrl(filePath);
        InputStream in = new URL(url).openStream();
        ServletOutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        int n;
        while ((n = in.read(buff)) != -1) {
            //将字节数组的数据全部写入到输出流中
            outputStream.write(buff, 0, n);
        }
        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
    }
}
