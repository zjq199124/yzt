package com.maizhiyu.yzt.serviceimpl;

import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.service.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;


@Service
@Transactional(rollbackFor=Exception.class)
public class FileService implements IFileService {

    @Value("${file.static-access-path}")
    private String staticAccessPath;

    @Value("${file.upload-folder}")
    private String uploadFolder;

    @Override
    public void upload(MultipartFile file, String pathname) {
        // 判断文件大小
        if (file.isEmpty()) {
            throw new BusinessException("文件大小不能为空");
        }
        // 生成文件名称
        String fullname = uploadFolder + "/" + pathname;
        // 判断文件目录
        File fd = new File(fullname);
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
    }

    @Override
    public void download(HttpServletResponse response, String pathname) throws IOException {
        // 判断文件是否存在
        File file = new File(uploadFolder + '/' + pathname);
        if(!file.exists()){
            throw new BusinessException("文件不存在");
        }
        // 设置响应数据格式
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + pathname);
        // 文件写入数据流
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        ServletOutputStream os = response.getOutputStream();
        byte[] buff = new byte[1024];
        int i = 0;
        while ((i = bis.read(buff)) != -1) {
            os.write(buff, 0, i);
            os.flush();
        }
        os.close();
    }

    @Override
    public void image(HttpServletResponse response, String pathname) throws IOException {
        // 判断文件后缀名称
        String[] arr = pathname.split("\\.");
        if (arr.length != 2) {
            throw new BusinessException("图片名称错误");
        }
        // 判断文件是否存在
        File file = new File(uploadFolder + "/" + pathname);
        if(!file.exists()){
            throw new BusinessException("文件不存在");
        }
        // 设置响应数据格式
        String suffix = arr[1];
        response.reset();
        response.setContentType("image/" + suffix);
        response.setContentLength((int) file.length());
        // 文件写入数据流
        OutputStream os = response.getOutputStream();
        BufferedImage bufferedImage = ImageIO.read(file);
        ImageIO.write(bufferedImage, suffix, os);
    }
}
