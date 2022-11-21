package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.JzfyMedicantMapping;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.YptMedicant;

import java.util.List;

/**
 * <p>
 * 焦作妇幼his-云平台-药材映射表 服务类
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-18
 */
public interface JzfyMedicantMappingService extends IService<JzfyMedicantMapping> {

    JzfyMedicantMapping getMedicantByCode(String code);

    JzfyMedicantMapping getMedicantByName(String name);

    List<JzfyMedicantMapping> getMedicantByCodeList(List<String> zhongyaoCodeList);

    List<JzfyMedicantMapping> getMedicantByNameList(List<String> zhongyaoNameList);
}
