package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.entity.TxInfraredImage;
import com.maizhiyu.yzt.enums.FileTypeEnum;
import com.maizhiyu.yzt.enums.OSSCatalogEnum;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.TxInfraredImageMapper;
import com.maizhiyu.yzt.service.SysMultimediaService;
import com.maizhiyu.yzt.service.TxInfraredImageService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * (TxInfraredImage)表服务实现类
 *
 * @author makejava
 * @since 2022-11-22 08:37:14
 */
@Service
public class TxInfraredImageServiceImpl extends ServiceImpl<TxInfraredImageMapper, TxInfraredImage> implements TxInfraredImageService {

    @Resource
    SysMultimediaService sysMultimediaService;

    @SneakyThrows
    @Override
    public TxInfraredImage saveTxInfraredImage(InputStream inputStream, String type, String remark, Long infraredId) {
        try {
            SysMultimedia sysMultimedia = sysMultimediaService.saveMultimedia(inputStream, infraredId + type + ".png", OSSCatalogEnum.INFRARED_IMG.getPath(), true, remark, FileTypeEnum.FILE.getCode());
            Assert.isFalse(sysMultimedia == null, "检测图片保存失败.....");
            TxInfraredImage txInfraredImage = new TxInfraredImage();
            txInfraredImage.setInfraredDataId(infraredId);
            txInfraredImage.setMultimediaId(sysMultimedia.getId());
            txInfraredImage.setLeibie(type);
            save(txInfraredImage);
            return txInfraredImage;
        } catch (Exception e) {
            throw new BusinessException(e);
        }finally {
            inputStream.close();
        }

    }
}

