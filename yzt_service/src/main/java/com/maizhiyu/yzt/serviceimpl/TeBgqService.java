package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TeBgq;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.TeBgqMapper;
import com.maizhiyu.yzt.service.ITeBgqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TeBgqService extends ServiceImpl<TeBgqMapper,TeBgq> implements ITeBgqService {

    @Autowired
    private TeBgqMapper mapper;

    @Override
    public Integer addBgq(TeBgq bgq) {
        return mapper.insert(bgq);
    }

    @Override
    public Integer delBgq(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setBgq(TeBgq bgq) {
        UpdateWrapper<TeBgq> wrapper = new UpdateWrapper<>();
        // id不空按id查询
        if (bgq.getId() != null) {
            wrapper.eq("id", bgq.getId());
        }
        // id为空按code查询
        else if (bgq.getCode() != null) {
            wrapper.eq("code", bgq.getCode());
        }
        // id和code都空则错误
        else {
            throw new BusinessException("id和code不能都为空");
        }
        return mapper.update(bgq, wrapper);
    }

    @Override
    public TeBgq getBgq(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<TeBgq> getBgqList(Long agencyId, Long customerId, Integer status, String term) {
        QueryWrapper<TeBgq> wrapper = new QueryWrapper<>();
        if (agencyId != null) wrapper.eq("agency_id", agencyId);
        if (customerId != null) wrapper.eq("customer_id", customerId);
        if (status != null) wrapper.eq("status", status);
        if (term != null) wrapper.like("term", term);
        return mapper.selectList(wrapper);
    }

    @Override
    public List<Map<String, Object>> getBgqListWithRunData(Long agencyId, Long customId, Integer status, String term) {
        return null;
    }

}
