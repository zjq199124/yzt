package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.SchChengyao;

import java.util.Map;

public interface ISchChengyaoService extends IService<SchChengyao> {

    public Integer addChengyao(SchChengyao chengyao);

    public Integer delChengyao(Long id);

    public Integer setChengyao(SchChengyao chengyao);

    public SchChengyao getChengyao(Long id);

    public IPage<Map<String, Object>> getChengyaoList(Page page, Long diseaseId, Integer status, String term);
}
