package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.TeModel;
import com.maizhiyu.yzt.mapper.TeModelMapper;
import com.maizhiyu.yzt.service.ITeModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor=Exception.class)
public class TeModelService implements ITeModelService {

    @Autowired
    private TeModelMapper mapper;

    @Override
    public Integer addModel(TeModel model) {
        return mapper.insert(model);
    }

    @Override
    public Integer delModel(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setModel(TeModel model) {
        return mapper.updateById(model);
    }

    @Override
    public TeModel getModel(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<TeModel> getModelList(Integer status, Integer type, String term) {
        QueryWrapper<TeModel> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (type != null) {
            wrapper.eq("type", type);
        }
        if (term != null) {
            wrapper.like("name", term);
        }
        List<TeModel> list = mapper.selectList(wrapper);
        return list;
    }
}
