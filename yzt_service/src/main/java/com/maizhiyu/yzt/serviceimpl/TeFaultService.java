package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class TeFaultService implements ITeFaultService {

    @Autowired
    private TeFaultMapper mapper;

    @Autowired
    private TeFaultSolutionMapper solutionMapper;


    @Override
    public Integer addFault(TeFault maintain) {
        return mapper.insert(maintain);
    }

    @Override
    public Integer delFault(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setFault(TeFault maintain) {
        return mapper.updateById(maintain);
    }

    @Override
    public TeFault getFault(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getFaultList(Long customerId, Integer status) {
        List<Map<String, Object>> list = mapper.selectFaultList(customerId, status);
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
