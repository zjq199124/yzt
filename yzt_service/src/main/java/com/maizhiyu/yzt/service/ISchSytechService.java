package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.SchSytech;

import java.util.List;
import java.util.Map;

public interface ISchSytechService {

    public Integer addSytech(SchSytech sytech);

    public Integer delSytech(Long id);

    public Integer setSytech(SchSytech sytech);

    public SchSytech getSytech(Long id);

    public List<Map<String,Object>> getSytechList(Long sytechId, Long diseaseId, Integer status, String term);

    public List<Map<String, Object>> getSytechBySytechId(Long diseaseId, Long syndromeId, Long sytechId);
}
