package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.vo.WaitSignatureVo;

/**
 * (BuPrescriptionItemTask)表服务接口
 *
 * @author makejava
 * @since 2023-02-01 15:10:35
 */
public interface BuPrescriptionItemTaskService extends IService<BuPrescriptionItemTask> {

    Page<WaitSignatureVo> selectWaitSignatureList(WaitSignatureRo waitSignatureRo);

    Boolean signature(Long prescriptionItemTaskId);
}

