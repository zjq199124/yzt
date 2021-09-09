package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.SchXieding;

import java.util.List;
import java.util.Map;

public interface ISchXiedingService {

    public Integer addXieding(SchXieding agency);

    public Integer delXieding(Long id);

    public Integer setXieding(SchXieding agency);

    public SchXieding getXieding(Long id);

    public List<Map<String,Object>> getXiedingList(Long diseaseId, Integer status, String term);
}
