package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.MedicantMapping;

import java.util.List;

/**
 * <p>
 * 焦作妇幼his-云平台-药材映射表 服务类
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-18
 */
public interface MedicantMappingService extends IService<MedicantMapping> {

    MedicantMapping getMedicantByCode(String code);

    MedicantMapping getMedicantByName(String name);

    List<MedicantMapping> getMedicantByCodeList(List<String> zhongyaoCodeList);

    List<MedicantMapping> getMedicantByNameList(List<String> zhongyaoNameList);
}
