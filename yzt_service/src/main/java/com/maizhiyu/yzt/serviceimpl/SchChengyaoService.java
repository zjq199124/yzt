package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.SchChengyao;
import com.maizhiyu.yzt.mapper.SchChengyaoMapper;
import com.maizhiyu.yzt.service.ISchChengyaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class SchChengyaoService extends ServiceImpl<SchChengyaoMapper,SchChengyao> implements ISchChengyaoService {

    @Autowired
    private SchChengyaoMapper mapper;

    @Override
    public Integer addChengyao(SchChengyao chengyao) {
        return mapper.insert(chengyao);
    }

    @Override
    public Integer delChengyao(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setChengyao(SchChengyao chengyao) {
        return mapper.updateById(chengyao);
    }

    @Override
    public SchChengyao getChengyao(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public IPage<Map<String, Object>> getChengyaoList(Page page, Long diseaseId, Integer status, String term) {
        IPage<Map<String, Object>> list = mapper.selectChengyaoList(page,diseaseId, status, term);
        return list;
    }
}
