package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TsSytech;

import java.util.List;

public interface ITsSytechService {

    public Integer addSytech(TsSytech sytech);

    public Integer delSytech(Integer id);

    public Integer setSytech(TsSytech sytech);

    public TsSytech getSytech(Integer id);

    public TsSytech getSytechByName(String name);

    public List<TsSytech> getSytechList(Integer status, String term, Integer display);

    List<TsSytech> selectSytechList(Long diseaseId, Long syndromeId, String search);
}
