package com.maizhiyu.yzt.serviceimpl;

import com.maizhiyu.yzt.entity.SchZhongyao;
import com.maizhiyu.yzt.mapper.SchZhongyaoMapper;
import com.maizhiyu.yzt.service.ISchZhongyaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class SchZhongyaoService implements ISchZhongyaoService {

    @Autowired
    private SchZhongyaoMapper mapper;

    @Override
    public Integer addZhongyao(SchZhongyao chengyao) {
        return mapper.insert(chengyao);
    }

    @Override
    public Integer delZhongyao(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setZhongyao(SchZhongyao chengyao) {
        return mapper.updateById(chengyao);
    }

    @Override
    public SchZhongyao getZhongyao(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getZhongyaoList(Long diseaseId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectZhongyaoList(status, diseaseId, term);
        return list;
    }
}
