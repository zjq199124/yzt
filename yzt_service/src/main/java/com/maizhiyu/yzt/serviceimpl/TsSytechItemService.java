package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.TsSytechItem;
import com.maizhiyu.yzt.mapper.TsSytechItemMapper;
import com.maizhiyu.yzt.service.ITsSytechItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor=Exception.class)
public class TsSytechItemService implements ITsSytechItemService {

    @Autowired
    private TsSytechItemMapper mapper;

    @Override
    public Integer addSytechItem(TsSytechItem item) {
        return mapper.insert(item);
    }

    @Override
    public Integer delSytechItem(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setSytechItem(TsSytechItem item) {
        return mapper.updateById(item);
    }

    @Override
    public TsSytechItem getSytechItem(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<TsSytechItem> getSytechItemList(Long sytechId) {
        QueryWrapper<TsSytechItem> wrapper = new QueryWrapper<>();
        wrapper.eq("sytech_id", sytechId);
        List<TsSytechItem> list = mapper.selectList(wrapper);
        return list;
    }
}
