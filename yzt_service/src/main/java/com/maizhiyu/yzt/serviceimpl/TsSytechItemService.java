package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.MsExaminationPaper;
import com.maizhiyu.yzt.entity.TsSytechItem;
import com.maizhiyu.yzt.mapper.TsSytechItemMapper;
import com.maizhiyu.yzt.service.ITsSytechItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor=Exception.class)
public class TsSytechItemService extends ServiceImpl<TsSytechItemMapper,TsSytechItem> implements ITsSytechItemService {

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
    public PageInfo<TsSytechItem> getSytechItemList(Long examinationPaperId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TsSytechItem> list = mapper.selectList(
//                Wrappers.<MsExaminationPaper>lambdaQuery()
//                .eq(sytechId != null, MsExaminationPaper::getSytechId,sytechId);
                new LambdaQueryWrapper<TsSytechItem>().eq(examinationPaperId != null, TsSytechItem::getExaminationPaperId, examinationPaperId));
        PageInfo<TsSytechItem> pageInfo = new PageInfo<>(list, pageSize);

        return pageInfo;
    }
}
