package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TeMaintainService extends ServiceImpl<TeMaintainMapper,TeMaintain> implements ITeMaintainService {

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
    public IPage<Map<String, Object>> getMaintainList(Page page, Long customerId, Long equipId, Integer type, String startDate, String endDate) {
        IPage<Map<String, Object>> list = mapper.selectMaintainList(page,customerId, equipId, type, startDate, endDate);
        return list;
    }

}
