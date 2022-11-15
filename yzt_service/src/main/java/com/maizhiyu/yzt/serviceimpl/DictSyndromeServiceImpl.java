package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maizhiyu.yzt.entity.DictSymptom;
import com.maizhiyu.yzt.entity.DictSyndrome;
import com.maizhiyu.yzt.mapper.DictSyndromeMapper;
import com.maizhiyu.yzt.service.IDictSyndromeService;
import com.maizhiyu.yzt.vo.DictSymptomVo;
import com.maizhiyu.yzt.vo.DictSyndromeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor=Exception.class)
public class DictSyndromeServiceImpl implements IDictSyndromeService {

    @Resource
    private DictSyndromeMapper dictSyndromeMapper;

    @Override
    public List<DictSyndromeVo> selectByDiseaseId(Long diseaseId) {
        List<DictSyndrome> list = dictSyndromeMapper.selectByDiseaseId(diseaseId);
        if(CollectionUtils.isEmpty(list))
            return Collections.emptyList();
        List<DictSyndromeVo> collect = getDictSyndromeVos(list);
        return collect;
    }

    @Override
    public List<DictSyndromeVo> selectDictSyndromeBySymptomIdList(List<Long> symptomIdList) {
        List<DictSyndrome> list = dictSyndromeMapper.selectDictSyndromeBySymptomIdList(symptomIdList);
        if(CollectionUtils.isEmpty(list))
            return Collections.emptyList();
        List<DictSyndromeVo> collect = getDictSyndromeVos(list);
        return collect;
    }

    private List<DictSyndromeVo> getDictSyndromeVos(List<DictSyndrome> list) {
        List<DictSyndromeVo> collect = list.stream().map(item -> {
            DictSyndromeVo dictSyndromeVo = new DictSyndromeVo();
            BeanUtils.copyProperties(item, dictSyndromeVo);
            return dictSyndromeVo;
        }).collect(Collectors.toList());
        return collect;
    }
}
