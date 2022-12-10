package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.SchXieding;
import com.maizhiyu.yzt.mapper.SchXiedingMapper;
import com.maizhiyu.yzt.service.ISchXiedingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
@RequestMapping("/xieding")
public class SchXiedingService extends ServiceImpl<SchXiedingMapper,SchXieding> implements ISchXiedingService {

    @Autowired
    private SchXiedingMapper mapper;

    @Override
    public Integer addXieding(SchXieding xieding) {
        return mapper.insert(xieding);
    }

    @Override
    public Integer delXieding(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setXieding(SchXieding xieding) {
        return mapper.updateById(xieding);
    }

    @Override
    public SchXieding getXieding(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getXiedingList(Long diseaseId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectXiedingList(status, diseaseId, term);
        return list;
    }
}
