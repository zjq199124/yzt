package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuTemplate;
import com.maizhiyu.yzt.mapper.BuTemplateMapper;
import com.maizhiyu.yzt.service.IBuTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuTemplateService extends ServiceImpl<BuTemplateMapper,BuTemplate> implements IBuTemplateService {

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
    public IPage<BuTemplate> getTemplateList(Page page, Long doctocId) {
        QueryWrapper<BuTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("doctor_id", doctocId);
        return mapper.selectPage(page,wrapper);
    }
}
