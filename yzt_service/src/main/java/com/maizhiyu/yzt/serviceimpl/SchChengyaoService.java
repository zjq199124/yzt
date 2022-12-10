package com.maizhiyu.yzt.serviceimpl;

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
    public List<Map<String, Object>> getChengyaoList(Long diseaseId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectChengyaoList(diseaseId, status, term);
        return list;
    }
}
