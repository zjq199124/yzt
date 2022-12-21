package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.YptDisease;

import java.util.List;

public interface IYptDiseaseService {

    Integer addDisease(YptDisease medicant);

    Integer delDisease(Integer id);

    Integer setDisease(YptDisease medicant);

    YptDisease getDisease(Integer id);

    YptDisease getDiseaseByHiscode(String code);

    YptDisease getDiseaseByHisname(String name);

    Page<YptDisease> getDiseaseList(Page page,String term);
}
