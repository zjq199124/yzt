package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.SchChengyao;

import java.util.List;
import java.util.Map;

public interface ISchChengyaoService {

    public Integer addChengyao(SchChengyao chengyao);

    public Integer delChengyao(Long id);

    public Integer setChengyao(SchChengyao chengyao);

    public SchChengyao getChengyao(Long id);

    public List<Map<String,Object>> getChengyaoList(Long diseaseId, Integer status, String term);
}
