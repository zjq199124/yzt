package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.DictCommon;

import java.util.List;
import java.util.Map;

public interface IDictCommonService extends IService<DictCommon> {

    // 字典类别接口
    Integer addCate(DictCommon cate);

    Integer delCate(Long id);

    Integer setCate(DictCommon cate);

    DictCommon getCate(Long id);

    List<Map<String,Object>> getCateList(Integer status, String term);


    // 字典条目接口
    Integer addItem(DictCommon item);

    Integer delItem(Long id);

    Integer setItem(DictCommon item);

    DictCommon getItem(Long id);

    IPage<DictCommon> getItemList(Page page, String cate, Integer status, String term);


    // 业务相关接口
    List<Map<String,Object>> getEquipTypeList();

    List<Map<String,Object>> getMaintainTypeList();

    List<Map<String,Object>> getSymptomTypeList();
}
