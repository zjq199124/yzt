package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TsSytech;

import java.util.List;

public interface ITsSytechService extends IService<TsSytech> {

    public Integer addSytech(TsSytech sytech);

    public Integer delSytech(Integer id);

    public Integer setSytech(TsSytech sytech);

    public TsSytech getSytech(Integer id);

    public TsSytech getSytechByName(String name);

    IPage<TsSytech> getSytechList(Page page, Integer status, String term, Integer display);

    List<TsSytech> selectSytechList(Long diseaseId, Long syndromeId, String search);
}
