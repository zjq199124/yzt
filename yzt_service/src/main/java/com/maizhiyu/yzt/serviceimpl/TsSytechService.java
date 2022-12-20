package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsSytech;
import com.maizhiyu.yzt.mapper.TsSytechMapper;
import com.maizhiyu.yzt.service.ITsSytechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor=Exception.class)
public class TsSytechService extends ServiceImpl<TsSytechMapper,TsSytech> implements ITsSytechService {


    @Autowired
    private TsSytechMapper mapper;

    @Override
    public Integer addSytech(TsSytech techs) {
        return mapper.insert(techs);
    }

    @Override
    public Integer delSytech(Integer id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setSytech(TsSytech techs) {
        return mapper.updateById(techs);
    }

    @Override
    public TsSytech getSytech(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public TsSytech getSytechByName(String name) {
        QueryWrapper<TsSytech> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return mapper.selectOne(wrapper);
    }


    @Override
    public IPage<TsSytech> getSytechList(Page page, Integer status, String term, Integer display) {
        QueryWrapper<TsSytech> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (term != null) {
            wrapper.like("name", term);
        }
        if (display != null) {
            wrapper.eq("display", display);
        }
        IPage<TsSytech> list = mapper.selectPage(page,wrapper);
        return list;
    }

    @Override
    public List<TsSytech> selectSytechList(Long diseaseId, Long syndromeId, String search) {
        List<TsSytech> tsSytechList = mapper.selectSytechList(diseaseId, syndromeId, search);
        return tsSytechList;
    }
}
