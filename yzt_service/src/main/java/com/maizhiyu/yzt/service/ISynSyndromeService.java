package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.SynSyndrome;

import java.util.List;
import java.util.Map;

public interface ISynSyndromeService {

    Integer addSyndrome(SynSyndrome syndrome);

    Integer delSyndrome(Long id);

    Integer setSyndrome(SynSyndrome syndrome);

    SynSyndrome getSyndrome(Long id);

    List<Map<String,Object>> getSyndromeList(Long diseaseId, Integer status, String term);

    List<Map<String,Object>> getSyndromeList(String diseaseName, Integer status, String term);
}
