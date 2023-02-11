package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.enums.FileSaveTypeEnum;
import com.maizhiyu.yzt.mapper.SysMultimediaMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.SysMultimediaService;
import com.maizhiyu.yzt.utils.ossKit.AliOssUtil;
import lombok.experimental.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * (SysMultimedia)表服务实现类
 *
 * @author makejava
 * @since 2022-11-21 15:07:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMultimediaServiceImpl extends ServiceImpl<SysMultimediaMapper, SysMultimedia> implements SysMultimediaService {

    @Resource
    AliOssUtil aliOssUtil;

    @Override
    public SysMultimedia saveMultimedia(MultipartFile file, String path, boolean isPrivate, String remark,Integer type) {
        // 获取文件名称
        String fileName = file.getOriginalFilename();                               // 获取文件名
        try {
            return saveMultimedia(file.getInputStream(), fileName, path, isPrivate, remark,type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public SysMultimedia saveMultimedia(InputStream inputStream, String fileName, String path, boolean isPrivate, String remark, Integer type) {
        String suffixName = fileName.substring(fileName.lastIndexOf("."));      // 截取后缀名
        try {
            Long size = Long.valueOf(inputStream.available());
            String url = aliOssUtil.uploadInputStream(inputStream, path, fileName, isPrivate);
            int start = url.indexOf("//") > 0 ? url.indexOf("//") : 0;
            int end = url.indexOf("?") >0 ? url.indexOf("?") : url.length() ;
            String saveUrl = url.substring(start+2, end);
            SysMultimedia sysMultimedia = new SysMultimedia();
            sysMultimedia.setName(fileName);
            sysMultimedia.setMessage(remark);
            sysMultimedia.setType(type);
            int a = url.indexOf("/");
            sysMultimedia.setServicePath(saveUrl.substring(0, saveUrl.indexOf("/")));
            sysMultimedia.setFilePath(saveUrl.substring(saveUrl.indexOf("/")));
            sysMultimedia.setSize(size);
            sysMultimedia.setSaveType(isPrivate ? FileSaveTypeEnum.ALI_PRIVACY.getCode() : FileSaveTypeEnum.ALI_PUBLIC.getCode());
            sysMultimedia.setExtension(suffixName);
            save(sysMultimedia);
            return sysMultimedia;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<SysMultimedia> getBatchByIds(List<Long> ids) {
        LambdaQueryWrapper<SysMultimedia> wrapper = Wrappers.lambdaQuery();
        wrapper.in(SysMultimedia::getId, ids);
        return list(wrapper);
    }

    @Override
    public String getFileUrlByid(Serializable id) {
        SysMultimedia sysMultimedia = getById(id);
        //TODO 这里 sysMultimedia 为空或匹配不到save_type的情况 需处理
        if (sysMultimedia != null) {
            if (sysMultimedia.getServicePath().equals(FileSaveTypeEnum.ALI_PUBLIC.getCode())) {
                return aliOssUtil.getPublicUrl(sysMultimedia.getFilePath());
            } else {
                return aliOssUtil.generatePresignedUrl(sysMultimedia.getFilePath());
            }
        }
        return null;
    }

    @Override
    public Result<SysMultimedia> getMultimedia(Serializable id) {
        SysMultimedia sysMultimedia = getById(id);
        String url=null;
        if (sysMultimedia != null) {
            if (sysMultimedia.getServicePath().equals(FileSaveTypeEnum.ALI_PUBLIC.getCode())) {
                 url=aliOssUtil.getPublicUrl(sysMultimedia.getFilePath());
            } else {
                 url=aliOssUtil.generatePresignedUrl(sysMultimedia.getFilePath());
            }
        }
        sysMultimedia.setUrl(url);
        return Result.success(sysMultimedia);
    }


}

