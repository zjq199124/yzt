package com.maizhiyu.yzt.serviceimpl;

import com.maizhiyu.yzt.entity.DictSyndrome;
import com.maizhiyu.yzt.mapper.DictSyndromeMapper;
import com.maizhiyu.yzt.service.IDictSyndromeService;
import com.maizhiyu.yzt.service.IRelSyndromeSymptomService;
import com.maizhiyu.yzt.vo.DictSyndromeVo;
import com.maizhiyu.yzt.vo.RelSyndromeSymptomVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor=Exception.class)
public class DictSyndromeServiceImpl implements IDictSyndromeService {

    @Resource
    private DictSyndromeMapper dictSyndromeMapper;

    @Resource
    private IRelSyndromeSymptomService relSyndromeSymptomService;

    @Override
    public List<DictSyndromeVo> selectByDiseaseId(Long diseaseId,String search) {
        List<DictSyndrome> list = dictSyndromeMapper.selectByDiseaseId(diseaseId,search);
        if(CollectionUtils.isEmpty(list))
            return Collections.emptyList();
        List<DictSyndromeVo> collect = getDictSyndromeVos(list);
        return collect;
    }

    @Override
    public List<DictSyndromeVo> selectDictSyndromeBySymptomIdList(Long diseaseId,List<Long> symptomIdList) {
        //1:查询该包含所列症状的分型
        List<DictSyndrome> list = dictSyndromeMapper.selectDictSyndromeBySymptomIdList(diseaseId,symptomIdList);
        if(CollectionUtils.isEmpty(list))
            return Collections.emptyList();
        List<DictSyndromeVo> dictSyndromeVoList = getDictSyndromeVos(list);

        List<Long> syndromeIds = dictSyndromeVoList.stream().map(item -> item.getId()).collect(Collectors.toList());

        //2:根据分型列表查询该分型下所有症状关系数据的接口
        List<RelSyndromeSymptomVo> relSyndromeSymptomVoList = relSyndromeSymptomService.selectDictSymptomBySyndromeIdList(syndromeIds);
        if(CollectionUtils.isEmpty(relSyndromeSymptomVoList))
            return dictSyndromeVoList;

        //3：计算出各个分型的得分
        Map<Long, List<RelSyndromeSymptomVo>> syndromeIdRelListMap = relSyndromeSymptomVoList.stream().collect(Collectors.groupingBy(RelSyndromeSymptomVo::getSyndromeId));
        if(syndromeIdRelListMap.size() <= 0)
            return dictSyndromeVoList;

        dictSyndromeVoList.forEach(item -> {
            //获取每一个分型的所有症状关联数据
            List<RelSyndromeSymptomVo> relSyndromeSymptomVos = syndromeIdRelListMap.get(item.getId());
            if (CollectionUtils.isEmpty(relSyndromeSymptomVos)) {
                item.setScore(0);
                return;
            }

            //获取每一个分型对应的所有症状的id列表
            List<Long> resultSymptomIdList = relSyndromeSymptomVos.stream().map(RelSyndromeSymptomVo::getSymptomId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(resultSymptomIdList)) {
                item.setScore(0);
                return;
            }

            //求选中的症状中有几个命中该分型下的对应症状（求id交集）
            List<Long> intersectionIdList = symptomIdList.stream().filter(symptomId -> resultSymptomIdList.contains(symptomId)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(intersectionIdList)) {
                item.setScore(0);
                return;
            }

            //交集个数 / 所有症状数量 = 辩证分型的具体得分
            item.setScore(new BigDecimal(intersectionIdList.size()).divide(new BigDecimal(resultSymptomIdList.size()),3,BigDecimal.ROUND_HALF_UP).doubleValue());
        });

        //根据辩证分型得分倒序排列
        dictSyndromeVoList = dictSyndromeVoList.stream().sorted(Comparator.comparing(DictSyndromeVo::getScore).reversed()).collect(Collectors.toList());


        return dictSyndromeVoList;
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
