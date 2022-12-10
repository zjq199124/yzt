package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TeModel;
import com.maizhiyu.yzt.mapper.TeModelMapper;
import com.maizhiyu.yzt.service.ITeModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TeModelService extends ServiceImpl<TeModelMapper,TeModel> implements ITeModelService {

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
    public List<Map<String, Object>> getModelList(Integer status, Integer type, String term) {
        return mapper.selectModelList(status, type, term);
//        QueryWrapper<TeModel> wrapper = new QueryWrapper<>();
//        if (status != null) {
//            wrapper.eq("status", status);
//        }
//        if (type != null) {
//            wrapper.eq("type", type);
//        }
//        if (term != null) {
//            wrapper.like("name", term);
//        }
//        List<TeModel> list = mapper.selectList(wrapper);
//        return list;
    }
}
