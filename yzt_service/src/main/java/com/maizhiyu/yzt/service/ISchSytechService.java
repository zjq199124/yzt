package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.SchSytech;

import java.util.Map;

public interface ISchSytechService extends IService<SchSytech> {

    public Integer addSytech(SchSytech sytech);

    public Integer delSytech(Long id);

    public Integer setSytech(SchSytech sytech);

    public SchSytech getSytech(Long id);

    public IPage<Map<String, Object>> getSytechList(Page page, Long sytechId, Long diseaseId, Integer status, String term);

    public Map<String, Object> getSytechBySytechId(Long diseaseId, Long syndromeId, Long sytechId);
}
