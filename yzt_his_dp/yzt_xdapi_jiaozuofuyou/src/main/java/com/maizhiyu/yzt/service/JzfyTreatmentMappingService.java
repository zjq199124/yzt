package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.JzfyTreatmentMapping;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 焦作妇幼his-云平台-治疗(适宜技术)映射表 服务类
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-18
 */
public interface JzfyTreatmentMappingService extends IService<JzfyTreatmentMapping> {

    JzfyTreatmentMapping getTreatmentByCode(String code);

    JzfyTreatmentMapping getTreatmentByName(String name);

    JzfyTreatmentMapping getTreatmentByHisCode(String hisCode);

    JzfyTreatmentMapping getTreatmentByHisName(String hisName);
}
