package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.entity.BuMedicant;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuMedicantService;
import com.maizhiyu.yzt.service.IBuRecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


    @ApiOperation(value = "获取诊断方案推荐", notes = "获取诊断方案推荐")
    @PostMapping("/getRecommend")
    public Result<BuDiagnoseVO.GetRecommendVO> getRecommend(@RequestBody BuDiagnoseRO.GetRecommendRO ro) {

        BuDiagnose diagnose = new BuDiagnose();
        diagnose.setDisease(ro.getDisease());
        Map<String, Object> mapRecommend = recommendService.getRecommend(diagnose);

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
                vo.setName(((String) map.getOrDefault("name", "")).trim());
                vo.setSymptoms(((String) map.getOrDefault("symptoms", "")).trim());
                vo.setComponent(((String) map.getOrDefault("component", "")).trim());
                vo.setContrain(((String) map.getOrDefault("contrain", "")).trim());
                vo.setAttention(((String) map.getOrDefault("attention", "")).trim());
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
                vo.setName(((String) map.getOrDefault("sytechName", "")).trim());
                vo.setSymptoms(((String) map.getOrDefault("symptoms", "")).trim());
                vo.setDetail(((String) map.getOrDefault("detail", "")).trim());
                vo.setOperation(((String) map.getOrDefault("operation", "")).trim());
                shiyiVOList.add(vo);
            }
        }

        // 整合数据
        BuDiagnoseVO.GetRecommendVO vo = new BuDiagnoseVO.GetRecommendVO();
        vo.setZhongyaoList(zhongyaoVOList);
        vo.setChengyaoList(chengyaoVOList);
        vo.setXiedingList(xiedingVOList);
        vo.setShiyiList(shiyiVOList);

        return Result.success(vo);
    }

}
