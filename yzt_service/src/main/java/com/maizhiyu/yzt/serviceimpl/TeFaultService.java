package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TeFault;
import com.maizhiyu.yzt.entity.TeFaultSolution;
import com.maizhiyu.yzt.mapper.TeFaultMapper;
import com.maizhiyu.yzt.mapper.TeFaultSolutionMapper;
import com.maizhiyu.yzt.service.ITeFaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TeFaultService extends ServiceImpl<TeFaultMapper,TeFault> implements ITeFaultService {

    @Autowired
    private TeFaultMapper mapper;

    @Autowired
    private TeFaultSolutionMapper solutionMapper;


    @Override
    public Integer addFault(TeFault fault) {
        return mapper.insert(fault);
    }

    @Override
    public Integer delFault(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setFault(TeFault fault) {
        return mapper.updateById(fault);
    }

    @Override
    public TeFault getFault(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getFaultList(Long customerId, Integer status, String code) {
        List<Map<String, Object>> list = mapper.selectFaultList(customerId, status, code);
        return list;
    }

    @Override
    public List<TeFaultSolution> getFaultSolutionList(Integer type) {
        QueryWrapper<TeFaultSolution> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        List<TeFaultSolution> list = solutionMapper.selectList(wrapper);
        return list;
    }
}
