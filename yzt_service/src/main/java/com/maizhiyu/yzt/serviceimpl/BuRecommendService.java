package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.entity.DictDisease;
import com.maizhiyu.yzt.mapper.BuRecommendMapper;
import com.maizhiyu.yzt.mapper.DictDiseaseMapper;
import com.maizhiyu.yzt.ro.BuDiagnoseRO;
import com.maizhiyu.yzt.service.IBuRecommendService;
import com.maizhiyu.yzt.service.IDictSyndromeService;
import com.maizhiyu.yzt.vo.BuDiagnoseVO;
import com.maizhiyu.yzt.vo.DictSymptomVo;
import com.maizhiyu.yzt.vo.DictSyndromeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BuRecommendService implements IBuRecommendService {

    @Autowired
    private BuRecommendMapper mapper;

    @Resource
    private IDictSyndromeService dictSyndromeService;

    @Resource
    private DictSymptomService dictSymptomService;

    @Resource
    private DictDiseaseMapper dictDiseaseMapper;

    @Resource
    private BuDiagnoseService buDiagnoseService;


    @Override
    public Map<String, Object> getRecommend(BuDiagnose diagnose) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> syndromeListA = null;
        List<Map<String, Object>> syndromeListB = null;
        List<Map<String, Object>> syndromeList = null;
        // 通过疾病获取辨证列表
        if ((diagnose.getDisease() != null && diagnose.getDisease().length() > 0) || (diagnose.getSyndrome() != null && diagnose.getSyndrome().length() > 0)) {
            syndromeListA = mapper.selectSyndromeByDisease(diagnose.getDisease(), diagnose.getSyndrome());
        }
        // 通过症状获取辨证列表
        if (diagnose.getSymptoms() != null && diagnose.getSymptoms().length() > 0) {
            String[] symptoms = diagnose.getSymptoms().split(",|，");
            syndromeListB = mapper.selectSyndromeBySymptom(symptoms);
        }
        // 处理辨证结果
        if (syndromeListA != null && syndromeListB != null) {
            syndromeList = new ArrayList<>();
            for (Map<String, Object> it1 : syndromeListA) {
                for (Map<String, Object> it2 : syndromeListB) {
                    if ((long) it1.get("id") == (long) it2.get("id")) {
                        syndromeList.add(it2);
                    }
                }
            }
        } else if (syndromeListA != null) {
            syndromeList = syndromeListA;
        } else if (syndromeListB != null) {
            syndromeList = syndromeListB;
        } else {
            return result;
        }
        // 计算辨证得分
        for (Map<String, Object> syndrome : syndromeList) {
            //症状列表
            String[] arr = ((String) syndrome.get("symptoms")).split(",|，");
            long count = (long) syndrome.get("count");
            if (StringUtils.isNotBlank(diagnose.getSymptoms())) {
                String[] arr2 = diagnose.getSymptoms().split(",|，");
                List<String> list1 = new ArrayList<>(Arrays.asList(arr));
                List<String> list2 = new ArrayList<>(Arrays.asList(arr2));
                List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
                count = intersection.size();
            }
            int total = arr.length;
            double score = (double) count / total;
            syndrome.put("total", total);
            syndrome.put("score", score);
        }
        // 按照得分排序
        syndromeList.sort((a, b) -> Double.compare((double) b.get("score"), (double) a.get("score")));
        int limit = syndromeList.size() < 10 ? syndromeList.size() : 10;
        syndromeList = syndromeList.subList(0, limit);
        // 输出辨证列表
        System.out.println("------------------------------------");
        for (Map<String, Object> s : syndromeList) {
            System.out.println("" + s.get("diseaseName") + "-" + s.get("name") + " : " + s);
        }
        // 补充推荐数据
        getRecommend(syndromeList, result);
        // 返回数据
        return result;
    }

    @Override
    public Map<String, Object> getRecommendByDisease(BuDiagnose diagnose) {
        Map<String, Object> result = new HashMap<>();
        // 获取辨证分型
        List<Map<String, Object>> syndromeList = mapper.selectSyndromeByDisease(diagnose.getDisease(), diagnose.getSyndrome());
        // 获取推荐方案
        getRecommend(syndromeList, result);
        // 返回数据
        return result;
    }

    @Override
    public Map<String, Object> getRecommendBySymptom(BuDiagnose diagnose) {
        Map<String, Object> result = new HashMap<>();
        // 获取症状列表
        String[] symptoms = diagnose.getSymptoms().split(",|，");
        // 获取辨证列表
        List<Map<String, Object>> syndromeList = mapper.selectSyndromeBySymptom(symptoms);
        // 计算辨证得分
        for (Map<String, Object> syndrome : syndromeList) {
            System.out.println(syndrome);
            String[] arr = ((String) syndrome.get("symptoms")).split(",|，");
            int total = arr.length;
            long count = (long) syndrome.get("count");
            double score = (double) count * count / total;
            syndrome.put("total", total);
            syndrome.put("score", score);
        }
        // 按照得分排序
        syndromeList.sort((a, b) -> Double.compare((double) b.get("score"), (double) a.get("score")));
        // 保留得分最高
        int limit = syndromeList.size() < 10 ? syndromeList.size() : 10;
        syndromeList = syndromeList.subList(0, limit);
        // 获取推荐方案
        getRecommend(syndromeList, result);
        // 返回数据
        return result;
    }

    // 根据辨证分型获取推荐方案
    @Override
    public Map<String, Object> selectRecommend(BuDiagnoseRO.GetRecommendRO ro) {
        //推荐方案必须要有病名称或id
        DictDisease disease = null;
        if (ro.getDisease() != null) {
            LambdaQueryWrapper<DictDisease> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(DictDisease::getName, ro.getDisease());
            disease = dictDiseaseMapper.selectOne(wrapper);
        } else {
            disease = dictDiseaseMapper.selectById(ro.getDiseaseId());
        }
        Assert.notNull(disease.getId(), "疾病名称或id不存在!");
        ro.setDiseaseId(disease.getId());
        Map<String, Object> resultMap = new HashMap<>();
        //在没有分型syndromeIdList以及没有症状集合symptomIdList先查询下这次挂号看病是否已经有保存诊断信息和治疗处方
        if (CollectionUtils.isEmpty(ro.getSymptomIdList()) && CollectionUtils.isEmpty(ro.getSyndromeIdList())) {
            //
            Map<String, Object> result = buDiagnoseService.getDetails(ro);
            if (Objects.nonNull(result))
                return result;
        }

        //2.没有syndromeIdList的情况下，判断是否有传症状集合symptomIdList，没有的话通过Feign远程调用云平台中获取疾病所有症状的接口
        if (CollectionUtils.isEmpty(ro.getSyndromeIdList()) && CollectionUtils.isEmpty(ro.getSymptomIdList())) {
            //疾病症状数据集合
            List<DictSymptomVo> dictSymptomVoList = dictSymptomService.selectByDiseaseId(ro.getDiseaseId());
            Assert.notEmpty(dictSymptomVoList, "该疾病下没有对应的症状，分型等基础数据!");
            resultMap.put("dictSymptomList", dictSymptomVoList);

            if (!CollectionUtils.isEmpty(dictSymptomVoList)) {
                List<Long> symptomIdList = dictSymptomVoList.stream().map(DictSymptomVo::getId).collect(Collectors.toList());
                ro.setSymptomIdList(symptomIdList);
            }
        }

        //3.判断是否有传分型集合syndromeIdList，没有的话使用symptomIdList通过Feign远程调用云平台中获取疾病所有分型的接口
        if (CollectionUtils.isEmpty(ro.getSyndromeIdList())) {
            List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeService.selectDictSyndromeBySymptomIdList(disease.getId(), ro.getSymptomIdList());
            //疾病分型数据集合
            resultMap.put("dictSyndromeList", dictSyndromeVoList);
            if (!CollectionUtils.isEmpty(dictSyndromeVoList)) {
                dictSyndromeVoList.forEach(item -> item.setIsShow(1));
                List<Long> syndromeIdList = dictSyndromeVoList.stream().map(DictSyndromeVo::getId).collect(Collectors.toList());
                ro.setSyndromeIdList(syndromeIdList);
            }
        }

        List<BuDiagnoseVO.ShiyiVO> sytechList = mapper.getRecommendSytech(ro.getSyndromeIdList(), ro.getDiseaseId(), ro.getSytechId(), ro.getCustomerName());
        // 整合数据
        BuDiagnoseVO.GetRecommendVO vo = new BuDiagnoseVO.GetRecommendVO();
        vo.setZhongyaoList(Collections.emptyList());
        vo.setChengyaoList(Collections.emptyList());
        vo.setXiedingList(Collections.emptyList());
        vo.setShiyiList(sytechList);
        resultMap.put("shiyiList", vo.getShiyiList());
        resultMap.put("yptDiagnoseId", null);
        resultMap.put("yptPrescriptionId", null);
        resultMap.put("yptPrescription", null);
        resultMap.put("prescriptionItemList", Collections.emptyList());
        //云平台中医诊断名称
        resultMap.put("yptDiseaseName", disease.getName());
        //云平台中医诊断Id
        resultMap.put("yptDiseaseId", disease.getId());
        // 返回数据
        return resultMap;
    }

    // 根据辨证分型获取推荐方案
    private void getRecommend(List<Map<String, Object>> syndromeList, Map<String, Object> result) {
        // 为空直接返回
        if (syndromeList.size() == 0) {
            return;
        }
        // 整理Id列表
        Long[] ids = syndromeList.stream().map(it -> it.get("id")).toArray(Long[]::new);
        // 获取推荐方案
//        List<Map<String, Object>> zhongyaoList = mapper.selectRecommendZhongyao(ids);
//        List<Map<String, Object>> chengyaoList = mapper.selectRecommendChengyao(ids);
//        List<Map<String, Object>> xiedingList = mapper.selectRecommendXieding(ids);
        List<Map<String, Object>> sytechList = mapper.selectRecommendSytech(ids);
        // 整理返回数据
        result.put("syndromeList", syndromeList);
//        result.put("zhongyaoList", zhongyaoList);
//        result.put("chengyaoList", chengyaoList);
//        result.put("xiedingList", xiedingList);
        result.put("sytechList", sytechList);
    }
}
