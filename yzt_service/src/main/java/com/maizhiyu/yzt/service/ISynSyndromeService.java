package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.SynSyndrome;

import java.util.Map;

public interface ISynSyndromeService extends IService<SynSyndrome> {

    Integer addSyndrome(SynSyndrome syndrome);

    Integer delSyndrome(Long id);

    Integer setSyndrome(SynSyndrome syndrome);

    SynSyndrome getSyndrome(Long id);

    IPage<Map<String, Object>> getSyndromeList(Page page, Long diseaseId, Integer status, String term);

    IPage<Map<String, Object>> getSyndromeList(Page page,String diseaseName, Integer status, String term);
}
