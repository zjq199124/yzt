package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.SchSytech;
import com.maizhiyu.yzt.mapper.SchSytechMapper;
import com.maizhiyu.yzt.service.ISchSytechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class SchSytechService extends ServiceImpl<SchSytechMapper,SchSytech> implements ISchSytechService {

    @Autowired
    private SchSytechMapper mapper;

    @Override
    public Integer addSytech(SchSytech chengyao) {
        return mapper.insert(chengyao);
    }

    @Override
    public Integer delSytech(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setSytech(SchSytech chengyao) {
        return mapper.updateById(chengyao);
    }

    @Override
    public SchSytech getSytech(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public IPage<Map<String, Object>> getSytechList(Page page, Long sytechId, Long diseaseId, Integer status, String term) {
        IPage<Map<String, Object>> list = mapper.selectSytechList(page,sytechId, diseaseId, status, term);
        return list;
    }

    @Override
    public Map<String, Object> getSytechBySytechId(Long diseaseId, Long syndromeId, Long sytechId) {
        Map<String, Object> map = mapper.getSytechBySytechId(sytechId, diseaseId, syndromeId);
        return map;
    }
}
