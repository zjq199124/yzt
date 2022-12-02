package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.SysMultimedia;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * (SysMultimedia)表服务接口
 *
 * @author makejava
 * @since 2022-11-21 15:07:01
 */
public interface SysMultimediaService extends IService<SysMultimedia> {

    /**
     * 多媒体文件上传保存
     *
     * @param file      文件
     * @param path      文件保存路径
     * @param isPrivate 是否保存为私有
     * @return
     */
    SysMultimedia saveMultimedia(MultipartFile file, String path, boolean isPrivate, String remark);

    /**
     * 多媒体文件上传保存
     *
     * @param file      文件
     * @param path      文件保存路径
     * @param isPrivate 是否保存为私有
     * @return
     */
    SysMultimedia saveMultimedia(InputStream inputStream, String fileName, String path, boolean isPrivate, String remark);


    /**
     * 以id列表批量获取
     *
     * @return
     */
    List<SysMultimedia> getBatchByIds(List<Long> ids);

    /**
     * 以id获取文件显示路径
     *
     * @param id
     * @return
     */
    String getFileUrlByid(Serializable id);

    /**
     * 以id获取多媒体数据详情
     * @param id
     * @return
     */
    SysMultimedia getMultimedia(Serializable id);


}

