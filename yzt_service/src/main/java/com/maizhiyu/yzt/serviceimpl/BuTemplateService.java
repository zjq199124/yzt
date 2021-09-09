package com.maizhiyu.yzt.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.BuTemplate;
import com.maizhiyu.yzt.mapper.BuTemplateMapper;
import com.maizhiyu.yzt.service.IBuTemplateService;
import com.maizhiyu.yzt.service.IBuTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuTemplateService implements IBuTemplateService {

    @Autowired
    private BuTemplateMapper mapper;

    @Override
    public Integer addTemplate(BuTemplate template) {
        return mapper.insert(template);
    }

    @Override
    public Integer delTemplate(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setTemplate(BuTemplate template) {
        return mapper.updateById(template);
    }

    @Override
    public BuTemplate getTemplate(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<BuTemplate> getTemplateList(Long doctocId) {
        QueryWrapper<BuTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("doctor_id", doctocId);
        return mapper.selectList(wrapper);
    }
}
