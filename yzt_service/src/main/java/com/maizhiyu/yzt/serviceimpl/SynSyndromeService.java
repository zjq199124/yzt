package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.DictDisease;
import com.maizhiyu.yzt.entity.SynSyndrome;
import com.maizhiyu.yzt.mapper.DictDiseaseMapper;
import com.maizhiyu.yzt.mapper.SynSyndromeMapper;
import com.maizhiyu.yzt.service.ISynSyndromeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class SynSyndromeService extends ServiceImpl<SynSyndromeMapper,SynSyndrome> implements ISynSyndromeService {

    @Autowired
    private SynSyndromeMapper mapper;

    @Autowired
    private DictDiseaseMapper dictDiseaseMapper;

    @Override
    public Integer addSyndrome(SynSyndrome agency) {
        return mapper.insert(agency);
    }

    @Override
    public Integer delSyndrome(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer setSyndrome(SynSyndrome syndrome) {
        return mapper.updateById(syndrome);
    }

    @Override
    public SynSyndrome getSyndrome(Long id) {
        SynSyndrome synSyndrome = mapper.selectById(id);
        return synSyndrome;
    }

    @Override
    public List<Map<String, Object>> getSyndromeList(Long diseaseId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectSyndromeList(diseaseId, null, status, term);
        return list;
    }

    @Override
    public List<Map<String, Object>> getSyndromeList(String diseaseName, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectSyndromeList(null, diseaseName, status, term);
        return list;
    }
}
