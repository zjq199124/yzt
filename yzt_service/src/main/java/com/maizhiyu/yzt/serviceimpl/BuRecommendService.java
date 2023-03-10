package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.entity.DictDisease;
import com.maizhiyu.yzt.entity.TreatmentMapping;
import com.maizhiyu.yzt.mapper.BuRecommendMapper;
import com.maizhiyu.yzt.mapper.DictDiseaseMapper;
import com.maizhiyu.yzt.ro.BuDiagnoseRO;
import com.maizhiyu.yzt.service.IBuRecommendService;
import com.maizhiyu.yzt.service.IDictSyndromeService;
import com.maizhiyu.yzt.service.ITreatmentMappingService;
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
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BuRecommendService extends ServiceImpl<BuRecommendMapper, Object> implements IBuRecommendService {

    @Autowired
    private BuRecommendMapper buRecommendMapper;

    @Resource
    private IDictSyndromeService dictSyndromeService;

    @Resource
    private DictSymptomService dictSymptomService;

    @Resource
    private DictDiseaseMapper dictDiseaseMapper;

    @Resource
    private BuDiagnoseService buDiagnoseService;

    @Resource
    private BuPrescriptionService buPrescriptionService;

    @Resource
    private ITreatmentMappingService treatmentMappingService;


    @Override
    public Map<String, Object> getRecommend(BuDiagnose diagnose) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> syndromeListA = null;
        List<Map<String, Object>> syndromeListB = null;
        List<Map<String, Object>> syndromeList = null;
        // ??????????????????????????????
        if ((diagnose.getDisease() != null && diagnose.getDisease().length() > 0) || (diagnose.getSyndrome() != null && diagnose.getSyndrome().length() > 0)) {
            syndromeListA = buRecommendMapper.selectSyndromeByDisease(diagnose.getDisease(), diagnose.getSyndrome());
        }
        // ??????????????????????????????
        if (diagnose.getSymptoms() != null && diagnose.getSymptoms().length() > 0) {
            String[] symptoms = diagnose.getSymptoms().split(",|???");
            syndromeListB = buRecommendMapper.selectSyndromeBySymptom(symptoms);
        }
        // ??????????????????
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
        // ??????????????????
        for (Map<String, Object> syndrome : syndromeList) {
            //????????????
            String[] arr = ((String) syndrome.get("symptoms")).split(",|???");
            long count = (long) syndrome.get("count");
            if (StringUtils.isNotBlank(diagnose.getSymptoms())) {
                String[] arr2 = diagnose.getSymptoms().split(",|???");
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
        // ??????????????????
        syndromeList.sort((a, b) -> Double.compare((double) b.get("score"), (double) a.get("score")));
        int limit = syndromeList.size() < 10 ? syndromeList.size() : 10;
        syndromeList = syndromeList.subList(0, limit);
        // ??????????????????
        System.out.println("------------------------------------");
        for (Map<String, Object> s : syndromeList) {
            System.out.println("" + s.get("diseaseName") + "-" + s.get("name") + " : " + s);
        }
        // ??????????????????
        getRecommend(syndromeList, result);
        // ????????????
        return result;
    }

    @Override
    public Map<String, Object> getRecommendByDisease(BuDiagnose diagnose) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        List<Map<String, Object>> syndromeList = buRecommendMapper.selectSyndromeByDisease(diagnose.getDisease(), diagnose.getSyndrome());
        // ??????????????????
        getRecommend(syndromeList, result);
        // ????????????
        return result;
    }

    @Override
    public Map<String, Object> getRecommendBySymptom(BuDiagnose diagnose) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        String[] symptoms = diagnose.getSymptoms().split(",|???");
        // ??????????????????
        List<Map<String, Object>> syndromeList = buRecommendMapper.selectSyndromeBySymptom(symptoms);
        // ??????????????????
        for (Map<String, Object> syndrome : syndromeList) {
            System.out.println(syndrome);
            String[] arr = ((String) syndrome.get("symptoms")).split(",|???");
            int total = arr.length;
            long count = (long) syndrome.get("count");
            double score = (double) count * count / total;
            syndrome.put("total", total);
            syndrome.put("score", score);
        }
        // ??????????????????
        syndromeList.sort((a, b) -> Double.compare((double) b.get("score"), (double) a.get("score")));
        // ??????????????????
        int limit = syndromeList.size() < 10 ? syndromeList.size() : 10;
        syndromeList = syndromeList.subList(0, limit);
        // ??????????????????
        getRecommend(syndromeList, result);
        // ????????????
        return result;
    }

    // ????????????????????????????????????
    @Override
    public Map<String, Object> selectRecommend(BuDiagnoseRO.GetRecommendRO ro) {
        //????????????????????????????????????id
        if (Objects.isNull(ro.getDisease())) {
            DictDisease dictDisease = dictDiseaseMapper.selectById(ro.getDiseaseId());
            Assert.notNull(dictDisease, "??????id??????!");
            ro.setDisease(dictDisease.getName());
        }

        Map<String, Object> resultMap = new HashMap<>();
        //???????????????syndromeIdList????????????????????????symptomIdList??????????????????????????????????????????????????????????????????????????????
        if (CollectionUtils.isEmpty(ro.getSymptomIdList()) && CollectionUtils.isEmpty(ro.getSyndromeIdList())) {
            Map<String, Object> result = buDiagnoseService.getDetails(ro);
            if (Objects.nonNull(result))
                return result;
        }

        //2.??????syndromeIdList?????????????????????????????????????????????symptomIdList?????????????????????Feign?????????????????????????????????????????????????????????
        if (CollectionUtils.isEmpty(ro.getSyndromeIdList()) && CollectionUtils.isEmpty(ro.getSymptomIdList())) {
            //????????????????????????
            List<DictSymptomVo> dictSymptomVoList = dictSymptomService.selectByDiseaseId(ro.getDiseaseId());
            Assert.notEmpty(dictSymptomVoList, "?????????????????????????????????????????????????????????!");
            resultMap.put("dictSymptomList", dictSymptomVoList);

            if (!CollectionUtils.isEmpty(dictSymptomVoList)) {
                List<Long> symptomIdList = dictSymptomVoList.stream().map(DictSymptomVo::getId).collect(Collectors.toList());
                ro.setSymptomIdList(symptomIdList);
            }
        }

        //3.??????????????????????????????syndromeIdList?????????????????????symptomIdList??????Feign?????????????????????????????????????????????????????????
        if (CollectionUtils.isEmpty(ro.getSyndromeIdList())) {
            List<DictSyndromeVo> dictSyndromeVoList = dictSyndromeService.selectDictSyndromeBySymptomIdList(ro.getDiseaseId(), ro.getSymptomIdList());
            //????????????????????????
            resultMap.put("dictSyndromeList", dictSyndromeVoList);
            if (!CollectionUtils.isEmpty(dictSyndromeVoList)) {
                dictSyndromeVoList.forEach(item -> item.setIsShow(1));
                List<Long> syndromeIdList = dictSyndromeVoList.stream().map(DictSyndromeVo::getId).collect(Collectors.toList());
                ro.setSyndromeIdList(syndromeIdList);
            }
        }
        List<BuDiagnoseVO.ShiyiVO> sytechList = buRecommendMapper.getRecommendSytech(ro.getSyndromeIdList(), ro.getDiseaseId(), ro.getSytechId(), ro.getCustomerId());
        translateSytechToHis(ro.getCustomerId(), sytechList);

        //????????????????????????
       /* LambdaQueryWrapper<BuPrescription> prescriptionQueryWrapper = new LambdaQueryWrapper<>();
        prescriptionQueryWrapper.eq(BuPrescription::getPatientId, ro.getPatientId())
                .eq(BuPrescription::getOutpatientId, ro.getOutpatientId())
                .orderByDesc(BuPrescription::getUpdateTime)
                .last("limit 1");
        BuPrescription buPrescription = buPrescriptionService.getOne(prescriptionQueryWrapper);
        if (buPrescription != null) {
            resultMap.put("yptPrescriptionId", buPrescription.getId());
            resultMap.put("yptPrescription", buPrescription);
        }*/
        // ????????????
        BuDiagnoseVO.GetRecommendVO vo = new BuDiagnoseVO.GetRecommendVO();
        vo.setZhongyaoList(Collections.emptyList());
        vo.setChengyaoList(Collections.emptyList());
        vo.setXiedingList(Collections.emptyList());
        vo.setShiyiList(sytechList);
        resultMap.put("shiyiList", vo.getShiyiList());
        resultMap.put("yptDiagnoseId", null);

        resultMap.put("prescriptionItemList", Collections.emptyList());
        //???????????????????????????
        resultMap.put("yptDiseaseName", ro.getDisease());
        //?????????????????????Id
        resultMap.put("yptDiseaseId", ro.getDiseaseId());
        // ????????????
        return resultMap;
    }

    private void translateSytechToHis(Long customerId, List<BuDiagnoseVO.ShiyiVO> sytechList) {
        List<Long> entityIdList = sytechList.stream().map(BuDiagnoseVO.ShiyiVO::getEntityId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        List<TreatmentMapping> treatmentMappingList = treatmentMappingService.selectByCodeList(customerId, entityIdList);
        if(CollectionUtils.isEmpty(treatmentMappingList))
            return;

        Map<Long, TreatmentMapping> codeMap = treatmentMappingList.stream().collect(Collectors.toMap(TreatmentMapping::getCode, Function.identity(), (k1, k2) -> k1));
        sytechList.forEach(item -> {
            TreatmentMapping treatmentMapping = codeMap.get(item.getEntityId());
            if(Objects.isNull(treatmentMapping))
                return;

            item.setEntityId(treatmentMapping.getHiscode());
            item.setName(treatmentMapping.getHisname());
        });
    }

    // ????????????????????????????????????
    private void getRecommend(List<Map<String, Object>> syndromeList, Map<String, Object> result) {
        // ??????????????????
        if (syndromeList.size() == 0) {
            return;
        }
        // ??????Id??????
        Long[] ids = syndromeList.stream().map(it -> it.get("id")).toArray(Long[]::new);
        // ??????????????????
//        List<Map<String, Object>> zhongyaoList = mapper.selectRecommendZhongyao(ids);
//        List<Map<String, Object>> chengyaoList = mapper.selectRecommendChengyao(ids);
//        List<Map<String, Object>> xiedingList = mapper.selectRecommendXieding(ids);
        List<Map<String, Object>> sytechList = buRecommendMapper.selectRecommendSytech(ids);
        // ??????????????????
        result.put("syndromeList", syndromeList);
//        result.put("zhongyaoList", zhongyaoList);
//        result.put("chengyaoList", chengyaoList);
//        result.put("xiedingList", xiedingList);
        result.put("sytechList", sytechList);
    }
}
