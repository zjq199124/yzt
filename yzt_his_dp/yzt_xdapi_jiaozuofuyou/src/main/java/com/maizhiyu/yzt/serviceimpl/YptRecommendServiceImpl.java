package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.map.MapUtil;
import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
import com.maizhiyu.yzt.bean.avo.RelSyndromeSymptomVo;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IYptCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class YptRecommendServiceImpl implements IYptCommonService {

    @Resource
    private FeignYptClient feignYptClient;

    @Override
    public Result getRecommend(Long diseaseId,Long syndromeId,String term) {
        // 调用开放接口获取诊断推荐
        Result result = feignYptClient.getSytechRecommend(diseaseId,syndromeId,term);
        return result;
    }

    @Override
    public Result<List<DictSyndromeVo>> selectDictSyndromeBySymptomIdList(List<Long> symptomIdList) {
        //1:通过Feign远程调用云平台中根据症状获取疾病所有分型的接口
        Result<List<DictSyndromeVo>> dictSyndromeResult = feignYptClient.selectDictSyndromeBySymptomIdList(symptomIdList);
        if(CollectionUtils.isEmpty(dictSyndromeResult.getData()))
            return Result.success(Collections.emptyList());

        List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeResult.getData();

        List<Long> syndromeIds = dictSyndromeVoList.stream().map(item -> item.getId()).collect(Collectors.toList());

        //2:通过Feign远程调用云平台中根据分型列表查询该分型下所有症状关系数据的接口
        Result<List<RelSyndromeSymptomVo>> result = feignYptClient.selectDictSymptomBySyndromeIdList(syndromeIds);
        if(CollectionUtils.isEmpty(result.getData()))
            return dictSyndromeResult;

        //3：计算出各个分型的得分
        Map<Long, List<RelSyndromeSymptomVo>> syndromeIdRelListMap = result.getData().stream().collect(Collectors.groupingBy(RelSyndromeSymptomVo::getSyndromeId));
        if(MapUtil.isEmpty(syndromeIdRelListMap))
            return dictSyndromeResult;

        dictSyndromeVoList.forEach(item -> {
            //获取每一个分型的所有症状关联数据
            List<RelSyndromeSymptomVo> relSyndromeSymptomVos = syndromeIdRelListMap.get(item.getId());
            if (CollectionUtils.isEmpty(relSyndromeSymptomVos)) {
                item.setScore(0);
                return;
            }

            //获取每一个分型对应的所以症状的id列表
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
            item.setScore(intersectionIdList.size() / resultSymptomIdList.size());
        });

        //根据辩证分型得分倒序排列
        dictSyndromeVoList = dictSyndromeVoList.stream().sorted(Comparator.comparing(DictSyndromeVo::getScore).reversed()).collect(Collectors.toList());

        return Result.success(dictSyndromeVoList);
    }
}














