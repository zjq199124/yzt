package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsSytechItem;
import com.maizhiyu.yzt.mapper.TsSytechItemMapper;
import com.maizhiyu.yzt.service.ITsSytechItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class TsSytechItemService extends ServiceImpl<TsSytechItemMapper, TsSytechItem> implements ITsSytechItemService {

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

    @Override
    public IPage<TsSytechItem> getSytechItemList(Long examinationPaperId, Long pageNum, Long pageSize) {
        LambdaQueryWrapper<TsSytechItem> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(examinationPaperId != null, TsSytechItem::getExaminationPaperId, examinationPaperId);
        IPage pages = mapper.selectPage(new Page(pageNum, pageSize),wrapper);
        return pages;
    }
}
