package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.mapper.*;
import com.maizhiyu.yzt.ro.BuDiagnoseRO;
import com.maizhiyu.yzt.service.IBuCheckService;
import com.maizhiyu.yzt.service.IBuDiagnoseService;
import com.maizhiyu.yzt.service.IBuRecommendService;
import com.maizhiyu.yzt.service.IDictSyndromeService;
import com.maizhiyu.yzt.vo.DictSymptomVo;
import com.maizhiyu.yzt.vo.DictSyndromeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
public class BuDiagnoseService extends ServiceImpl<BuDiagnoseMapper, BuDiagnose> implements IBuDiagnoseService {

    @Autowired
    private BuDiagnoseMapper mapper;

    @Autowired
    private BuPatientMapper patientMapper;

    @Autowired
    private BuOutpatientMapper outpatientMapper;

    @Resource
    private DictSymptomMapper dictSymptomMapper;

    @Resource
    private DictSyndromeMapper dictSyndromeMapper;

    @Resource
    private BuPrescriptionMapper buPrescriptionMapper;

    @Resource
    private BuPrescriptionItemMapper buPrescriptionItemMapper;

    @Resource
    private MsCustomerMapper msCustomerMapper;

    @Resource
    private HsUserMapper hsUserMapper;

    @Resource
    private IBuCheckService buCheckService;

    @Resource
    private IDictSyndromeService dictSyndromeService;

    @Resource
    private IBuRecommendService buRecommendService;

    @Override
    public Integer addDiagnose(BuDiagnose diagnose) {
        return mapper.insert(diagnose);
    }

    @Override
    public Integer setDiagnose(BuDiagnose diagnose) {
        return mapper.updateById(diagnose);
    }

    @Override
    public BuDiagnose getDiagnose(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public BuDiagnose getDiagnoseOfOutpatient(Long outpatientId) {
        QueryWrapper<BuDiagnose> wrapper = new QueryWrapper<>();
        wrapper.eq("outpatient_id", outpatientId);
//        result.put("hisPatient", hisPatient);
//        result.put("hisDoctor", hisDoctor);
//        result.put("customerName", customerName);
        return mapper.selectOne(wrapper);
    }

    @Override
    public List<Map<String, Object>> getDiagnoseList(Long customerId, String start, String end) {
        // 定义返回变量
        List<Map<String, Object>> list = new ArrayList<>();
        // 查询处方列表
        QueryWrapper<BuDiagnose> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id", customerId)
                .ge("update_time", start)
                .lt("update_time", end)
                .last("limit 100");
        List<BuDiagnose> diagnoses = mapper.selectList(wrapper);
        // 查询挂号信息
        for (BuDiagnose diagnose : diagnoses) {
            // 查询患者信息
            BuPatient patient = patientMapper.selectById(diagnose.getPatientId());
            // 查询挂号信息
            BuOutpatient outpatient = outpatientMapper.selectById(diagnose.getOutpatientId());
            // 整理数据结构
            JSONObject jsonObj = (JSONObject) JSONObject.toJSON(diagnose);
            jsonObj.put("patient", patient);
            jsonObj.put("outpatient", outpatient);
            // 添加到结果集
            list.add(jsonObj);
        }
        // 返回数据
        return list;
    }

    @Override
    public Map<String, Object> getDetails(BuDiagnoseRO.GetRecommendRO ro) {

        Assert.notNull(ro, "入参信息为空!");
        BuOutpatient buOutpatient = outpatientMapper.selectById(ro.getOutpatientId());
        if(buOutpatient==null) return null;
//        Assert.notNull(buOutpatient, "门诊信息为空!");
        Map<String, Object> resultMap = new HashMap<>();
        //1：查询是否有诊断信息
        LambdaQueryWrapper<BuDiagnose> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuDiagnose::getPatientId, ro.getPatientId())
                .eq(BuDiagnose::getOutpatientId, ro.getOutpatientId())
                .eq(BuDiagnose::getDiseaseId, ro.getDiseaseId())
                .eq(BuDiagnose::getStatus, 1)
                .orderByDesc(BuDiagnose::getUpdateTime)
                .last("limit 1");
        BuDiagnose buDiagnose = mapper.selectOne(queryWrapper);
        if (Objects.isNull(buDiagnose)) {
            return null;
        } else {
            //云平台中医疾病名称
            resultMap.put("yptDiseaseName", ro.getDisease());
            //云平台中医疾病Id
            resultMap.put("yptDiseaseId", buDiagnose.getDiseaseId());
            //云平台中诊断信息Id
            resultMap.put("yptDiagnoseId", buDiagnose.getId());
        }

        //2.1:查询这个疾病下的所有的症状列表
        List<DictSymptomVo> dictSymptomVoList = Collections.emptyList();
        List<DictSymptom> list = dictSymptomMapper.selectByDiseaseId(ro.getDiseaseId());
        if (CollectionUtils.isEmpty(list)) {
            //疾病症状数据集合
            resultMap.put("dictSymptomList", Collections.emptyList());
        } else {
            dictSymptomVoList = list.stream().map(item -> {
                DictSymptomVo dictSymptomVo = new DictSymptomVo();
                BeanUtils.copyProperties(item, dictSymptomVo);
                return dictSymptomVo;
            }).collect(Collectors.toList());

            //2.2标记出之前的诊断保存时所选的症状
            List<Long> symptomIdList = getSymptomIdList(buDiagnose);

            if (!CollectionUtils.isEmpty(symptomIdList)) {
                dictSymptomVoList.forEach(item -> {
                    if (symptomIdList.contains(item.getId())) {
                        item.setIsCheck(1);
                    }
                });
            }
            //疾病症状数据集合
            resultMap.put("dictSymptomList", dictSymptomVoList);
        }

        //3.1:查询这个疾病下的所有的分型列表
        List<DictSyndromeVo> dictSyndromeVoList = Collections.emptyList();//保存所有的分型

        List<DictSyndrome> dictSyndromeList = dictSyndromeMapper.selectByDiseaseId(ro.getDiseaseId(), null);
        if (CollectionUtils.isEmpty(dictSyndromeList)) {
            //疾病分型数据集合
            resultMap.put("dictSyndromeList", Collections.emptyList());
        } else {
            dictSyndromeVoList = dictSyndromeList.stream().map(item -> {
                DictSyndromeVo dictSyndromeVo = new DictSyndromeVo();
                BeanUtils.copyProperties(item, dictSyndromeVo);
                return dictSyndromeVo;
            }).collect(Collectors.toList());

            if (Objects.nonNull(buDiagnose.getSyndromeId())) {
                dictSyndromeVoList.forEach(item -> {
                    if (buDiagnose.getSyndromeId().equals(item.getId())) {
                        item.setIsCheck(1);
                    }
                });
            }

            //3.2通过上面选中的症状推出选中的分型有哪些 标记出之前的诊断保存时所选的分型
            List<Long> symptomIdList = getSymptomIdList(buDiagnose);
            if (!CollectionUtils.isEmpty(symptomIdList)) {
                List<DictSyndromeVo> checkDictSyndromeVoList = dictSyndromeService.selectDictSyndromeBySymptomIdList(ro.getDiseaseId(), symptomIdList);
                if (!CollectionUtils.isEmpty(checkDictSyndromeVoList)) {
                    List<Long> checkDictSyndromeVoIdList = checkDictSyndromeVoList.stream().map(DictSyndromeVo::getId).collect(Collectors.toList());
                    dictSyndromeVoList.forEach(item -> {
                        if (checkDictSyndromeVoIdList.contains(item.getId())) {
                            item.setIsShow(1);
                        }
                    });
                }
            }
            //疾病分型数据集合
            resultMap.put("dictSyndromeList", dictSyndromeVoList);
        }

        //4：查询出已经保存的处方
        LambdaQueryWrapper<BuPrescription> prescriptionQueryWrapper = new LambdaQueryWrapper<>();
        prescriptionQueryWrapper.eq(BuPrescription::getPatientId, buOutpatient.getPatientId())
                .eq(BuPrescription::getOutpatientId, buOutpatient.getId())
                .eq(BuPrescription::getDoctorId, buOutpatient.getDoctorId())
                .eq(BuPrescription::getIsDel, 0)
                .orderByDesc(BuPrescription::getUpdateTime)
                .last("limit 1");
        BuPrescription buPrescription = buPrescriptionMapper.selectOne(prescriptionQueryWrapper);

        if (Objects.isNull(buPrescription)) {
            //处方中所包含的适宜技术的列表
            resultMap.put("prescriptionItemList", Collections.emptyList());
            //处方id
            resultMap.put("yptPrescriptionId", null);
            resultMap.put("yptPrescription", null);
        } else {
            resultMap.put("yptPrescriptionId", buPrescription.getId());
            resultMap.put("yptPrescription", buPrescription);
            //5:查询保存的处方所对应的具体适宜技术
            LambdaQueryWrapper<BuPrescriptionItem> buPrescriptionItemQueryWrapper = new LambdaQueryWrapper<>();
            buPrescriptionItemQueryWrapper.eq(BuPrescriptionItem::getPrescriptionId, buPrescription.getId())
                    .eq(BuPrescriptionItem::getDoctorId, buOutpatient.getDoctorId())
                    .eq(BuPrescriptionItem::getPatientId, buOutpatient.getPatientId())
                    .eq(BuPrescriptionItem::getOutpatientId, buOutpatient.getId())
                    .eq(BuPrescriptionItem::getType, 5)
                    .eq(BuPrescriptionItem::getIsDel, 0);

            List<BuPrescriptionItem> buPrescriptionItemList = buPrescriptionItemMapper.selectList(buPrescriptionItemQueryWrapper);
            resultMap.put("prescriptionItemList", buPrescriptionItemList);
        }

        //6：查询需要推荐的适宜技术
        List<Long> syndromeIdList = ro.getSyndromeIdList();
        if (CollectionUtils.isEmpty(syndromeIdList)) {
            syndromeIdList = dictSyndromeVoList.stream().filter(item -> item.getIsCheck() == 1).map(DictSyndromeVo::getId).collect(Collectors.toList());
        } else if (CollectionUtils.isEmpty(syndromeIdList)) {
            syndromeIdList = dictSyndromeVoList.stream().filter(item -> item.getIsShow() == 1).map(DictSyndromeVo::getId).collect(Collectors.toList());

        } else if (CollectionUtils.isEmpty(syndromeIdList)) {
            syndromeIdList = dictSyndromeVoList.stream().map(DictSyndromeVo::getId).collect(Collectors.toList());
        }

        BuDiagnoseRO.GetRecommendRO recommendRo = new BuDiagnoseRO.GetRecommendRO();
        recommendRo.setDiseaseId(buDiagnose.getDiseaseId());
        recommendRo.setCustomerName(ro.getCustomerName());
        recommendRo.setSyndromeIdList(syndromeIdList);
        Map<String, Object> stringObjectMap = buRecommendService.selectRecommend(recommendRo);
        resultMap.put("shiyiList", stringObjectMap.get("sytechList"));

        return resultMap;
    }

    private List<Long> getSymptomIdList(BuDiagnose buDiagnose) {
        if (StringUtils.isBlank(buDiagnose.getSymptomIds()))
            return Collections.emptyList();

        return Splitter.on(',').trimResults().splitToList(buDiagnose.getSymptomIds()).stream().map(str -> Long.valueOf(str)).collect(Collectors.toList());
    }
}
























