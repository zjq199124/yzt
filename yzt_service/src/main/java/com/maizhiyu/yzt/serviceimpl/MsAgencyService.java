package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.DictDisease;
import com.maizhiyu.yzt.entity.MsAgency;
import com.maizhiyu.yzt.mapper.MsAgencyMapper;
import com.maizhiyu.yzt.service.IMsAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsAgencyService implements IMsAgencyService {

    @Autowired
    private MsAgencyMapper mapper;

    @Override
    public Integer addAgency(MsAgency agency) {
        return mapper.insert(agency);
    }

    @Override
    public Integer delAgency(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setAgency(MsAgency agency) {
        return mapper.updateById(agency);
    }

    @Override
    public MsAgency getAgency(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getAgencyList(Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectAgencyList(status, term);
        return list;
    }
}
