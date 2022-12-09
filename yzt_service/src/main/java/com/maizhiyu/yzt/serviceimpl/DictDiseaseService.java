package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.DictDisease;
import com.maizhiyu.yzt.mapper.DictDiseaseMapper;
import com.maizhiyu.yzt.service.IDictDiseaseService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class DictDiseaseService extends ServiceImpl<DictDiseaseMapper,DictDisease> implements IDictDiseaseService {

    @Autowired
    private DictDiseaseMapper mapper;

    @Override
    @ExistCheck(clazz = DictDisease.class, fname = "content", message = "疾病已存在")
    public Integer addDisease(DictDisease disease) {
        return mapper.insert(disease);
    }

    @Override
    public Integer delDisease(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setDisease(DictDisease disease) {
        return mapper.updateById(disease);
    }

    @Override
    public DictDisease getDisease(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getDiseaseList(Integer status, String term) {
        QueryWrapper<DictDisease> wrapper = new QueryWrapper<>();
        wrapper.select("*");
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (term != null) {
            wrapper.like("content", term);
        }
        List<Map<String, Object>> list = mapper.selectMaps(wrapper);
        return list;
    }
}
