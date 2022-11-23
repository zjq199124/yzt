package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.RelSyndromeSymptom;
import com.maizhiyu.yzt.mapper.RelSyndromeSymptomMapper;
import com.maizhiyu.yzt.service.IRelSyndromeSymptomService;
import com.maizhiyu.yzt.vo.RelSyndromeSymptomVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelSyndromeSymptomServiceImpl extends ServiceImpl<RelSyndromeSymptomMapper, RelSyndromeSymptom> implements IRelSyndromeSymptomService {

    @Resource
    private RelSyndromeSymptomMapper relSyndromeSymptomMapper;

    @Override
    public List<RelSyndromeSymptomVo> selectDictSymptomBySyndromeIdList(List<Long> syndromeIds) {
        LambdaQueryWrapper<RelSyndromeSymptom> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelSyndromeSymptom::getIsDel, 0)
                .in(RelSyndromeSymptom::getSyndromeId, syndromeIds);
        List<RelSyndromeSymptom> relSyndromeSymptoms = relSyndromeSymptomMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(relSyndromeSymptoms))
            return Collections.emptyList();

        List<RelSyndromeSymptomVo> collect = relSyndromeSymptoms.stream().map(item -> {
            RelSyndromeSymptomVo relSyndromeSymptomVo = new RelSyndromeSymptomVo();
            BeanUtils.copyProperties(item, relSyndromeSymptomVo);
            return relSyndromeSymptomVo;
        }).collect(Collectors.toList());
        return collect;
    }
}






















