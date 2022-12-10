package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.MsAgency;

import java.util.List;
import java.util.Map;

public interface IMsAgencyService extends IService<MsAgency> {

    public Integer addAgency(MsAgency agency);

    public Integer delAgency(Long id);

    public Integer setAgency(MsAgency agency);

    public MsAgency getAgency(Long id);

    public List<Map<String,Object>> getAgencyList(Integer status, String term);
}
