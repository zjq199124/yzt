package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.entity.BuMedicant;
import com.maizhiyu.yzt.entity.MsCustomer;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.BuDiagnoseRO;
import com.maizhiyu.yzt.service.IBuDiagnoseService;
import com.maizhiyu.yzt.service.IBuMedicantService;
import com.maizhiyu.yzt.service.IBuRecommendService;
import com.maizhiyu.yzt.service.IMsCustomerService;
import com.maizhiyu.yzt.serviceimpl.MsCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Api(tags = "诊断接口")
@RestController
@RequestMapping("/diagnose")
public class BuDiagnoseController {

    @Autowired
    private IBuMedicantService medicantService;

    @Autowired
    private IBuRecommendService recommendService;

    @Resource
    private IMsCustomerService msCustomerService;

    @Resource
    private IBuDiagnoseService diagnoseService;

    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result<BuDiagnoseVO.GetRecommendVO> getRecommend(@RequestBody BuDiagnoseRO.GetRecommendRO ro) {

/*        Map<String, Object> mapRecommend = recommendService.selectRecommend(ro);

        // 整理中药数据
        List<Map<String, Object>> zhongyaoList = (List<Map<String, Object>>) mapRecommend.get("zhongyaoList");
        List<BuDiagnoseVO.ZhongyaoVO> zhongyaoVOList = new ArrayList<>();
        Pattern patternZhongyao = Pattern.compile("(\\d+)(\\S*)");
        if (zhongyaoList != null) {
            for (Map<String, Object> map : zhongyaoList) {
                BuDiagnoseVO.ZhongyaoVO vo = new BuDiagnoseVO.ZhongyaoVO();
                List<BuDiagnoseVO.ZhongyaoComponentVO> component = new ArrayList<>();
                List<String> namelist = new ArrayList<>();
                String componentString = ((String) map.getOrDefault("component", "")).replace(" ", "");
                // 处理每味药材
                for (String item : componentString.split(",|，|;|；|、")) {
                    BuDiagnoseVO.ZhongyaoComponentVO x = new BuDiagnoseVO.ZhongyaoComponentVO();
                    // 字段解析
                    String[] arr = item.split(":|：");
                    if (arr.length == 1) {
                        String name = arr[0].trim();
                        String dosage = "1";
                        String unit = "g";
                        x.setName(name);
                        x.setDosage(Integer.valueOf(dosage));
                        x.setUnit(unit);
                        component.add(x);
                    }
                    if (arr.length == 2) {
                        Matcher matcher = patternZhongyao.matcher(arr[1]);
                        matcher.find();
                        String name = arr[0].trim();
                        String dosage = matcher.group(1).trim();
                        String unit = matcher.group(2).trim();
                        x.setName(name);
                        x.setDosage(Integer.valueOf(dosage));
                        x.setUnit(unit);
                        component.add(x);
                    }
                    namelist.add(x.getName());
                }
                // 查询药材编码
                try {
                    List<BuMedicant> medicantList = medicantService.getMedicantListByNameList(namelist);
                    // 对每一味药材补充编码字段
                    for (BuMedicant medicant : medicantList) {
                        for (BuDiagnoseVO.ZhongyaoComponentVO v : component) {
                            if (v.getName().equals(medicant.getName())) {
                                v.setCode(medicant.getCode());
                            }
                        }
                    }
                } catch (Exception e) {
                    log.warn("获取药材编码异常：" + e.getMessage());
                }
                // 设置其他字段
                vo.setName((String) map.getOrDefault("name", ""));
                vo.setSymptoms((String) map.getOrDefault("symptoms", ""));
                vo.setComponent(component);
                zhongyaoVOList.add(vo);
            }
        }

        // 整理成药数据
        List<Map<String, Object>> chengyaoList = (List<Map<String, Object>>) mapRecommend.get("chengyaoList");
        List<BuDiagnoseVO.ChengyaoVO> chengyaoVOList = new ArrayList<>();
        if (chengyaoList != null) {
            for (Map<String, Object> map : chengyaoList) {
                BuDiagnoseVO.ChengyaoVO vo = new BuDiagnoseVO.ChengyaoVO();
                vo.setName(((String) map.getOrDefault("name", "")));
                vo.setSymptoms(((String) map.getOrDefault("symptoms", "")));
                vo.setComponent(((String) map.getOrDefault("component", "")));
                vo.setContrain(((String) map.getOrDefault("contrain", "")));
                vo.setAttention(((String) map.getOrDefault("attention", "")));
                chengyaoVOList.add(vo);
            }
        }

        // 整理协定数据
        List<Map<String, Object>> xiedingList = (List<Map<String, Object>>) mapRecommend.get("xiedingList");
        List<BuDiagnoseVO.XiedingVO> xiedingVOList = new ArrayList<>();
        Pattern patternXieding = Pattern.compile("(\\d+)(\\S*)");
        if (xiedingList != null) {
            for (Map<String, Object> map : xiedingList) {
                BuDiagnoseVO.XiedingVO vo = new BuDiagnoseVO.XiedingVO();
                List<BuDiagnoseVO.XiedingComponentVO> component = new ArrayList<>();
                List<String> namelist = new ArrayList<>();
                String componentString = ((String) map.getOrDefault("component", "")).replace(" ", "");
                // 处理每味药材
                for (String item : componentString.split(",|，")) {
                    BuDiagnoseVO.XiedingComponentVO x = new BuDiagnoseVO.XiedingComponentVO();
                    String[] arr = item.split(":|：");
                    if (arr.length == 1) {
                        String name = arr[0].trim();
                        String dosage = "1";
                        String unit = "g";
                        x.setName(name);
                        x.setDosage(Integer.valueOf(dosage));
                        x.setUnit(unit);
                        component.add(x);
                    }
                    if (arr.length == 2) {
                        Matcher matcher = patternXieding.matcher(arr[1]);
                        matcher.find();
                        String name = arr[0].trim();
                        String dosage = matcher.group(1).trim();
                        String unit = matcher.group(2).trim();
                        x.setName(name);
                        x.setDosage(Integer.valueOf(dosage));
                        x.setUnit(unit);
                        component.add(x);
                    }
                }
                // 查询药材编码
                try {
                    List<BuMedicant> medicantList = medicantService.getMedicantListByNameList(namelist);
                    // 对每一味药材补充编码字段
                    for (BuMedicant medicant : medicantList) {
                        for (BuDiagnoseVO.XiedingComponentVO v : component) {
                            if (v.getName().equals(medicant.getName())) {
                                v.setCode(medicant.getCode());
                            }
                        }
                    }
                } catch (Exception e) {
                    log.warn("获取药材编码异常：" + e.getMessage());
                }
                // 设置其他字段
                vo.setName((String) map.getOrDefault("name", ""));
                vo.setSymptoms((String) map.getOrDefault("symptoms", ""));
                vo.setComponent(component);
                xiedingVOList.add(vo);
            }
        }

        // 整理适宜数据
        List<Map<String, Object>> shiyiList = (List<Map<String, Object>>) mapRecommend.get("sytechList");
        List<BuDiagnoseVO.ShiyiVO> shiyiVOList = new ArrayList<>();
        if (shiyiList != null) {
            for (Map<String, Object> map : shiyiList) {
                BuDiagnoseVO.ShiyiVO vo = new BuDiagnoseVO.ShiyiVO();
                vo.setCode(("" + map.getOrDefault("sytechId", "")));
                vo.setName(((String) map.getOrDefault("sytech_name", "")));
                vo.setSymptoms(((String) map.getOrDefault("symptoms", "")));
                vo.setDetail(((String) map.getOrDefault("detail", "")));
                vo.setOperation(((String) map.getOrDefault("operation", "")));
                shiyiVOList.add(vo);
            }
        }

        // 整合数据
        BuDiagnoseVO.GetRecommendVO vo = new BuDiagnoseVO.GetRecommendVO();
        vo.setZhongyaoList(zhongyaoVOList);
        vo.setChengyaoList(chengyaoVOList);
        vo.setXiedingList(xiedingVOList);
        vo.setShiyiList(shiyiVOList);

        return Result.success(vo);*/

        Map<String, Object> mapRecommend = recommendService.selectRecommend(ro);

        // 整理适宜数据
        List<Map<String, Object>> shiyiList = (List<Map<String, Object>>) mapRecommend.get("sytechList");
        List<BuDiagnoseVO.ShiyiVO> shiyiVOList = new ArrayList<>();
        if (shiyiList != null) {
            for (Map<String, Object> map : shiyiList) {
                BuDiagnoseVO.ShiyiVO vo = new BuDiagnoseVO.ShiyiVO();
                vo.setSytechId(Objects.isNull(map.get("sytech_id")) ? null : Long.valueOf(map.get("sytech_id").toString()));
                vo.setName(((String) map.getOrDefault("sytech_name", "")));
                vo.setSymptoms(((String) map.getOrDefault("symptoms", "")));
                vo.setDetail(((String) map.getOrDefault("detail", "")));
                vo.setOperation(((String) map.getOrDefault("operation", "")));
                vo.setCustomerId(Objects.isNull(map.get("customer_id")) ? null : Long.valueOf(map.get("customer_id").toString()));
                vo.setRecommend(Objects.isNull(map.get("recommend")) ? null : Integer.valueOf(map.get("recommend").toString()));
                shiyiVOList.add(vo);
            }
        }

        // 整合数据
        BuDiagnoseVO.GetRecommendVO vo = new BuDiagnoseVO.GetRecommendVO();
        vo.setZhongyaoList(Collections.emptyList());
        vo.setChengyaoList(Collections.emptyList());
        vo.setXiedingList(Collections.emptyList());
        vo.setShiyiList(shiyiVOList);

        return Result.success(vo);
    }

    @ApiOperation(value = "保存诊断信息接口")
    @PostMapping(value = "/addDiagnoseInfo")
    public Result addDiagnose(@RequestBody BuPrescriptionRO.AddPrescriptionShiyi ro) throws Exception {
       MsCustomer msCustomer = msCustomerService.getCustomerByName(ro.getDiagnoseInfo().getCustomerName());
        if(Objects.isNull(msCustomer))
            throw new Exception("不存在名称为：" + ro.getDiagnoseInfo().getCustomerName() + " 的客户!");

        ro.getDiagnoseInfo().setCustomerId(msCustomer.getId());

        BuDiagnose buDiagnose = new BuDiagnose();
        buDiagnose.setId(ro.getDiagnoseInfo().getId());
        buDiagnose.setDoctorId(ro.getBaseInfo().getDoctorId());
        buDiagnose.setPatientId(ro.getBaseInfo().getPatientId());
        buDiagnose.setOutpatientId(ro.getBaseInfo().getOutpatientId());
        buDiagnose.setId(ro.getDiagnoseInfo().getId());
        buDiagnose.setCustomerId(ro.getDiagnoseInfo().getCustomerId());
        buDiagnose.setDepartmentId(ro.getDiagnoseInfo().getDepartmentId());
        buDiagnose.setDisease(ro.getDiagnoseInfo().getDisease());
        buDiagnose.setDiseaseId(ro.getDiagnoseInfo().getDiseaseId());
        buDiagnose.setSymptoms(ro.getDiagnoseInfo().getSymptoms());
        buDiagnose.setSymptomIds(ro.getDiagnoseInfo().getSymptomIds());
        buDiagnose.setSyndrome(ro.getDiagnoseInfo().getSyndrome());
        buDiagnose.setSyndromeId(ro.getDiagnoseInfo().getSyndromeId());
        buDiagnose.setStatus(1);
        buDiagnose.setUpdateTime(new Date());
        if (Objects.isNull(buDiagnose.getId())) {
            buDiagnose.setCreateTime(new Date());
        }

        Integer integer = diagnoseService.saveOrUpdate(buDiagnose);
        return Result.success(integer);
    }


    @ApiOperation(value = "获取诊断详情")
    @PostMapping(value = "/getDetail")
    Result getDetail(@RequestBody BuDiagnoseRO.GetRecommendRO ro) throws Exception {
        Assert.notNull(ro.getPatientId(), "his端患者id不能为空!");
        Assert.notNull(ro.getOutpatientId(), "his端患者门诊预约id不能为空!");
        Result result = diagnoseService.getDetails(ro);
        return result;
    }
}


























