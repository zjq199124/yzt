package com.maizhiyu.yzt.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TxInfraredImage;

import java.io.InputStream;

/**
 * (TxInfraredImage)表服务接口
 *
 * @author makejava
 * @since 2022-11-22 08:37:14
 */
public interface TxInfraredImageService extends IService<TxInfraredImage> {
    /**
     * 保存红外检测图片
     * @param inputStream
     * @param fileName
     * @param path
     * @param remark
     * @return
     */
    TxInfraredImage saveTxInfraredImage(InputStream inputStream,String type,String remark,Long InfraredId);


}

