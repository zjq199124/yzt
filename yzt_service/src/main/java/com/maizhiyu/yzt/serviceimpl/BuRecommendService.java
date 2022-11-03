package com.maizhiyu.yzt.serviceimpl;

import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.mapper.BuRecommendMapper;
import com.maizhiyu.yzt.service.IBuRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class BuRecommendService implements IBuRecommendService {

    @Autowired
    private BuRecommendMapper mapper;

    @Override
    public Map<String, Object> getRecommend(BuDiagnose diagnose) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> syndromeListA = null;
        List<Map<String, Object>> syndromeListB = null;
        List<Map<String, Object>> syndromeList = null;
        // 通过疾病获取辨证列表
        if ((diagnose.getDisease()!=null && diagnose.getDisease().length() > 0) ||
                (diagnose.getSyndrome()!=null && diagnose.getSyndrome().length() > 0)) {
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
                    if ( (long)it1.get("id") == (long)it2.get("id") ) {
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
            String[] arr = ((String) syndrome.get("symptoms")).split(",|，");
            long count = (long) syndrome.get("count");
            if(StringUtils.isNotBlank(diagnose.getSymptoms())) {
                String[] arr2 = diagnose.getSymptoms().split(",|，");
                List<String> list1= new ArrayList<>(Arrays.asList(arr));
                List<String> list2= new ArrayList<>(Arrays.asList(arr2));
                List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
                count = intersection.size();
            }
            int total = arr.length;
            double score = (double) count / total;
            syndrome.put("total", total);
            syndrome.put("score", score);
        }
        // 按照得分排序
        syndromeList.sort((a,b) -> Double.compare((double)b.get("score"), (double)a.get("score")));
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
        syndromeList.sort((a,b) -> Double.compare((double)b.get("score"), (double)a.get("score")));
        // 保留得分最高
        int limit = syndromeList.size() < 10 ? syndromeList.size() : 10;
        syndromeList = syndromeList.subList(0, limit);
        // 获取推荐方案
        getRecommend(syndromeList, result);
        // 返回数据
        return result;
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
        List<Map<String, Object>> zhongyaoList = mapper.selectRecommendZhongyao(ids);
        List<Map<String, Object>> chengyaoList = mapper.selectRecommendChengyao(ids);
        List<Map<String, Object>> xiedingList = mapper.selectRecommendXieding(ids);
        List<Map<String, Object>> sytechList = mapper.selectRecommendSytech(ids);
        // 整理返回数据
        result.put("syndromeList", syndromeList);
        result.put("zhongyaoList", zhongyaoList);
        result.put("chengyaoList", chengyaoList);
        result.put("xiedingList", xiedingList);
        result.put("sytechList", sytechList);
    }
}
