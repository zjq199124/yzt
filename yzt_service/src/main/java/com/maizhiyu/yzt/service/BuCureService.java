package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuCure;
import com.maizhiyu.yzt.ro.BuCureSearchRO;
import com.maizhiyu.yzt.vo.BuCureVo;

import java.util.List;

/**
 * 治疗表(BuCure)表服务接口层
 *
 * @author liuyzh
 * @since 2022-12-19 18:34:06
 */
public interface BuCureService extends IService<BuCure>{

    /**
     * 新增 或编辑治疗
     * @param buCure
     * @return
     */
    boolean saveOrUpdate(BuCure buCure);

    /**
     * 结束治疗
     * @param id
     * @return
     */
    boolean endTreatment(Long id);

    Page<BuCure> treatmentList(BuCureSearchRO buCureSearchRO);

    BuCureVo selectCureDetailBySignatureId(Long signatureId);
}
