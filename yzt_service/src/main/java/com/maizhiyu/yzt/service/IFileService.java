package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public interface IFileService  {

    void upload(MultipartFile file, String path);

    void download(HttpServletResponse response, String path) throws IOException;

    void image(HttpServletResponse response, String path) throws IOException;
}
