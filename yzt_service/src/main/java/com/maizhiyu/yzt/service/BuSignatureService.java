package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuSignature;
import com.maizhiyu.yzt.ro.WaitTreatmentRo;

/**
 * 治疗签到表(BuSignature)表服务接口层
 *
 * @author liuyzh
 * @since 2022-12-19 19:03:08
 */
public interface BuSignatureService extends IService<BuSignature>{

    /**
     * 签到
     * @param buSignature
     * @return
     */
    Boolean addSignature(BuSignature buSignature);

    Page<BuSignature> waitTreatmentList(WaitTreatmentRo waitTreatmentRo);
}
