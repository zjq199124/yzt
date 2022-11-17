package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.RelSyndromeSymptom;
import com.maizhiyu.yzt.vo.RelSyndromeSymptomVo;

import java.util.List;

public interface IRelSyndromeSymptomService extends IService<RelSyndromeSymptom> {

    List<RelSyndromeSymptomVo> selectDictSymptomBySyndromeIdList(List<Long> syndromeIds);
}
