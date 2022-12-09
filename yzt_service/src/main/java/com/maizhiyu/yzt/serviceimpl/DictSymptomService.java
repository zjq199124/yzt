package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.DictSymptom;
import com.maizhiyu.yzt.mapper.DictSymptomMapper;
import com.maizhiyu.yzt.service.IDictSymptomService;
import com.maizhiyu.yzt.utils.ExistCheck;
import com.maizhiyu.yzt.vo.DictSymptomVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor=Exception.class)
public class DictSymptomService implements IDictSymptomService {

    @Autowired
    private DictSymptomMapper mapper;

    @Override
    @ExistCheck(clazz = DictSymptom.class, fname = "content", message = "症状已存在")
    public Integer addSymptom(DictSymptom symptom) {
        return mapper.insert(symptom);
    }

    @Override
    public Integer delSymptom(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setSymptom(DictSymptom symptom) {
        return mapper.updateById(symptom);
    }

    @Override
    public DictSymptom getSymptom(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getSymptomList(Integer status, String term) {
        QueryWrapper<DictSymptom> wrapper = new QueryWrapper<>();
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

    @Override
    public List<DictSymptomVo> selectByDiseaseId(Long diseaseId) {
        List<DictSymptom> list = mapper.selectByDiseaseId(diseaseId);
        if(CollectionUtils.isEmpty(list))
            return Collections.emptyList();

        List<DictSymptomVo> collect = list.stream().map(item -> {
            DictSymptomVo dictSymptomVo = new DictSymptomVo();
            BeanUtils.copyProperties(item, dictSymptomVo);
            return dictSymptomVo;
        }).collect(Collectors.toList());
        return collect;
    }
}
