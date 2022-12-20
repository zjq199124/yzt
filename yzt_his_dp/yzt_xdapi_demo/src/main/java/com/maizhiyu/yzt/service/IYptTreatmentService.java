package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.YptTreatment;

public interface IYptTreatmentService {

    Integer addTreatment(YptTreatment medicant);

    Integer delTreatment(Integer id);

    Integer setTreatment(YptTreatment medicant);

    YptTreatment getTreatment(Integer id);

    YptTreatment getTreatmentByCode(String code);

    YptTreatment getTreatmentByName(String name);

    YptTreatment getTreatmentByHisCode(String code);

    YptTreatment getTreatmentByHisName(String name);

    IPage<YptTreatment> getTreatmentList(Page page, String term);

}
