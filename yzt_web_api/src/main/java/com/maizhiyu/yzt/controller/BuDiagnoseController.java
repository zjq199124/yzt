package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuDiagnoseService;
import com.maizhiyu.yzt.service.IBuRecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Api(tags = "诊断接口")
@RestController
@RequestMapping("/diagnose")
public class BuDiagnoseController {

    @Autowired
    private IBuDiagnoseService service;

    @Autowired
    private IBuRecommendService recommendService;


    @ApiOperation(value = "获取诊断列表", notes = "获取诊断列表")
    @PostMapping("/getDiagnoseList")
    public Result<BuDiagnose> getDiagnoseList (
            @RequestParam @NotNull Long customerId,
            @RequestParam @NotNull String start,
            @RequestParam @NotNull String end) {
        List<Map<String, Object>> list = service.getDiagnoseList(customerId, start, end);
        return Result.success(list);
    }


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
        for (Map<String, Object> map : zhongyaoList) {
            BuDiagnoseVO.ZhongyaoVO vo = new BuDiagnoseVO.ZhongyaoVO();
            List<BuDiagnoseVO.ZhongyaoComponentVO> component = new ArrayList<>();
            String componentString = ((String) map.getOrDefault("component", "")).replace(" ", "");
            for (String item : componentString.split(",|，")) {
                BuDiagnoseVO.ZhongyaoComponentVO x = new BuDiagnoseVO.ZhongyaoComponentVO();
                String[] arr = item.split(":|：");
                if (arr.length == 1) {
                    String name = arr[0].trim();
                    String dosage = "0";
                    String unit = "";
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
            }
            vo.setName((String) map.getOrDefault("name", ""));
            vo.setSymptoms((String) map.getOrDefault("symptoms", ""));
            vo.setComponent(component);
            zhongyaoVOList.add(vo);
        }

        // 整理成药数据
        List<Map<String, Object>> chengyaoList = (List<Map<String, Object>>) mapRecommend.get("chengyaoList");
        List<BuDiagnoseVO.ChengyaoVO> chengyaoVOList = new ArrayList<>();
        for (Map<String, Object> map : chengyaoList) {
            BuDiagnoseVO.ChengyaoVO vo = new BuDiagnoseVO.ChengyaoVO();
            vo.setName(((String) map.getOrDefault("name", "")).trim());
            vo.setSymptoms(((String) map.getOrDefault("symptoms", "")).trim());
            vo.setComponent(((String) map.getOrDefault("component", "")).trim());
            vo.setContrain(((String) map.getOrDefault("contrain", "")).trim());
            vo.setAttention(((String) map.getOrDefault("attention", "")).trim());
            chengyaoVOList.add(vo);
        }

        // 整理协定数据
        List<Map<String, Object>> xiedingList = (List<Map<String, Object>>) mapRecommend.get("xiedingList");
        List<BuDiagnoseVO.XiedingVO> xiedingVOList = new ArrayList<>();
        Pattern patternXieding = Pattern.compile("(\\d+)(\\S*)");
        for (Map<String, Object> map : xiedingList) {
            BuDiagnoseVO.XiedingVO vo = new BuDiagnoseVO.XiedingVO();
            List<BuDiagnoseVO.XiedingComponentVO> component = new ArrayList<>();
            String componentString = ((String) map.getOrDefault("component", "")).replace(" ", "");
            for (String item : componentString.split(",|，")) {
                BuDiagnoseVO.XiedingComponentVO x = new BuDiagnoseVO.XiedingComponentVO();
                String[] arr = item.split(":|：");
                if (arr.length == 1) {
                    String name = arr[0].trim();
                    String dosage = "0";
                    String unit = "";
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
            vo.setName((String) map.getOrDefault("name", ""));
            vo.setSymptoms((String) map.getOrDefault("symptoms", ""));
            vo.setComponent(component);
            xiedingVOList.add(vo);
        }

        // 整理适宜数据
        List<Map<String, Object>> shiyiList = (List<Map<String, Object>>) mapRecommend.get("sytechList");
        List<BuDiagnoseVO.ShiyiVO> shiyiVOList = new ArrayList<>();
        for (Map<String, Object> map : shiyiList) {
            BuDiagnoseVO.ShiyiVO vo = new BuDiagnoseVO.ShiyiVO();
            vo.setName(((String) map.getOrDefault("name", "")).trim());
            vo.setSymptoms(((String) map.getOrDefault("symptoms", "")).trim());
            vo.setDetail(((String) map.getOrDefault("detail", "")).trim());
            vo.setOperation(((String) map.getOrDefault("operation", "")).trim());
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
