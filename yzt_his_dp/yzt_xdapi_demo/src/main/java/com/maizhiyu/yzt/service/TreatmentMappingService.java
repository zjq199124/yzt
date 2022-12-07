package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TreatmentMapping;

/**
 * <p>
 * 焦作妇幼his-云平台-治疗(适宜技术)映射表 服务类
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-18
 */
public interface TreatmentMappingService extends IService<TreatmentMapping> {

    TreatmentMapping getTreatmentByCode(String code);

    TreatmentMapping getTreatmentByName(String name);

    TreatmentMapping getTreatmentByHisCode(String hisCode);

    TreatmentMapping getTreatmentByHisName(String hisName);
}
