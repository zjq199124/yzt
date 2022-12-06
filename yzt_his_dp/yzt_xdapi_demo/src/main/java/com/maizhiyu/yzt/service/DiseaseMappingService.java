package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.DiseaseMapping;

import java.util.List;

/**
 * <p>
 * 焦作妇幼his-云平台-疾病映射表 服务类
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-14
 */
public interface DiseaseMappingService extends IService<DiseaseMapping> {

    DiseaseMapping selectByHisName(String hisDiseaseName);

    List<DiseaseMapping> diseaseList(String search);
}
