package com.maizhiyu.yzt.serviceimpl;

import com.maizhiyu.yzt.entity.TeMaintain;
import com.maizhiyu.yzt.mapper.TeMaintainMapper;
import com.maizhiyu.yzt.service.ITeMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TeMaintainService implements ITeMaintainService {

    @Autowired
    private TeMaintainMapper mapper;

    @Override
    public Integer addMaintain(TeMaintain maintain) {
        return mapper.insert(maintain);
    }

    @Override
    public Integer delMaintain(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setMaintain(TeMaintain maintain) {
        return mapper.updateById(maintain);
    }

    @Override
    public TeMaintain getMaintain(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getMaintainList(Long customerId, Long equipId, Integer type, String startDate, String endDate) {
        List<Map<String, Object>> list = mapper.selectMaintainList(customerId, equipId, type, startDate, endDate);
        return list;
    }

}
