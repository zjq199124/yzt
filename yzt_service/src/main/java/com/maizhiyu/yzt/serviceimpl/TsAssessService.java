package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsAssess;
import com.maizhiyu.yzt.mapper.TsAssessMapper;
import com.maizhiyu.yzt.service.ITsAssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TsAssessService extends ServiceImpl<TsAssessMapper,TsAssess> implements ITsAssessService {

    @Autowired
    private TsAssessMapper mapper;

    @Override
    public Integer addAssess(TsAssess assess) {
        return mapper.insert(assess);
    }

    @Override
    public Integer delAssess(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setAssess(TsAssess assess) {
        return mapper.updateById(assess);
    }

    @Override
    public TsAssess getAssess(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public IPage<Map<String,Object>> getAssessList(Page page, Long customerId, Long sytechId, String startDate, String endDate, String term) {
        IPage<Map<String, Object>> list = mapper.selectAssessList(page,customerId, sytechId, startDate, endDate, term);
        return list;
    }
}
