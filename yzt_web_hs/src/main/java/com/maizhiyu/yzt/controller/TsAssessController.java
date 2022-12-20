package com.maizhiyu.yzt.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.MsExaminationPaper;
import com.maizhiyu.yzt.entity.TsAssess;
import com.maizhiyu.yzt.entity.TsProblemRecord;
import com.maizhiyu.yzt.entity.TsSytechItem;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.request.ProblemRquest;
import com.maizhiyu.yzt.request.TsProblemRecordRequest;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IMsExaminationPaperService;
import com.maizhiyu.yzt.service.ITsAssessService;
import com.maizhiyu.yzt.service.ITsProblemRecordService;
import com.maizhiyu.yzt.serviceimpl.TsSytechItemService;
import com.maizhiyu.yzt.utils.MyDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Api(tags = "考核接口")
@RestController
@RequestMapping("/assess")
public class TsAssessController {

    @Autowired
    private ITsAssessService service;

    @Autowired
    private IMsExaminationPaperService msExaminationPaperService;

    @Autowired
    private ITsAssessService tsAssessService;

    @Autowired
    private ITsProblemRecordService tsProblemRecordService;

    @Autowired
    private TsSytechItemService tsSytechItemService;


    @ApiOperation(value = "增加考核", notes = "增加考核")
    @PostMapping("/addAssess")
    public Result addAssess(@RequestBody TsAssess assess) {
        if (assess.getExaminationPaperId() == null) {
            throw new BusinessException("试卷id不能为空");
        }
        assess.setStatus(0);
        assess.setTime(new Date());
        Integer res = service.addAssess(assess);
        return Result.success(assess);
    }

    @ApiOperation(value = "根据适应技术id获取试卷列表", notes = "根据适应技术id获取试卷列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sytechId", value = "适宜技术ID", required = true)
    })
    @PostMapping("/getExaminationPaperList")
    public Result addAssess(@RequestParam Long sytechId) {
        List<MsExaminationPaper> msExaminationPaperList = msExaminationPaperService.getMsExaminationPaperListBySytechId(sytechId);
        return Result.success(msExaminationPaperList);
    }


    @ApiOperation(value = "根据试卷id获取题目", notes = "根据试卷id获取题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msExaminationPaperId", value = "试卷id", required = true)
    })
    @PostMapping("/getTsSytechItems")
    public Result getTsSytechItems(@RequestParam Long msExaminationPaperId) {
        IPage<TsSytechItem> sytechItemList = tsSytechItemService.getSytechItemList(msExaminationPaperId, -1L, 0L);
        return Result.success(sytechItemList.getRecords());
    }


    @ApiOperation(value = "答题提交", notes = "答题提交")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "problemList", value = "题目列表", required = true),
//            @ApiImplicitParam(name = "assessId", value = "技术考核id", required = true)
//    })
    @PostMapping("/addTsProblemRecord")
    public Result addTsProblemRecord(@RequestBody TsProblemRecordRequest tsProblemRecordRequest) {
        String s = JSON.toJSONString(tsProblemRecordRequest.getProblemList());

        TsProblemRecord tsProblemRecord = new TsProblemRecord();

        tsProblemRecord.setProblemJson(s);
        tsProblemRecord.setAssessId(tsProblemRecordRequest.getAssessId());
        tsProblemRecord.setProblemList(tsProblemRecordRequest.getProblemList());
        TsAssess assess = tsAssessService.getAssess(tsProblemRecord.getAssessId());
        if (assess == null) {
            throw new BusinessException("技术考核记录找不到");
        }
        MsExaminationPaper msExaminationPaper = msExaminationPaperService.getMsExaminationPaper(assess.getExaminationPaperId());

        if (msExaminationPaper == null) {
            throw new BusinessException("考核试卷找不到");
        }
        tsProblemRecord.setPassConditionJson(JSON.toJSONString(msExaminationPaper));
        getResults(msExaminationPaper, tsProblemRecord);
        tsProblemRecord.setTime(new Date());
        tsProblemRecordService.addTsProblemRecord(tsProblemRecord);

        return Result.success();
    }


    @ApiOperation(value = "查看答题记录", notes = "查看答题记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tsProblemRecordId", value = "答题记录id", required = true)
    })
    @PostMapping("/getTsProblemRecord")
    public Result getTsProblemRecord(@RequestParam Long tsProblemRecordId) {

        TsProblemRecord tsProblemRecord = tsProblemRecordService.getTsProblemRecordById(tsProblemRecordId);

        JSONArray array = JSONObject.parseArray(tsProblemRecord.getProblemJson());

        List<ProblemRquest> problemList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            ProblemRquest problemRquest = new ProblemRquest();
            JSONObject jo = array.getJSONObject(i);
            problemRquest.setTsSytechItemId(((Number) jo.get("tsSytechItemId")).longValue());
            problemRquest.setType((Integer) jo.get("type"));
            problemRquest.setTitle((String) jo.get("title"));
            problemList.add(problemRquest);
        }

        tsProblemRecord.setProblemList(problemList);

        MsExaminationPaper msExaminationPaper = JSONObject.parseObject(tsProblemRecord.getPassConditionJson(), MsExaminationPaper.class);
        tsProblemRecord.setMsExaminationPaper(msExaminationPaper);


        return Result.success(tsProblemRecord);
    }


//    @ApiOperation(value = "修改考核信息", notes = "修改考核信息")
//    @PostMapping("/setAssess")
//    public Result setUser(@RequestBody TsAssess assess) {
//        Integer res = service.setAssess(assess);
//        return Result.success(assess);
//    }
//
//
//    @ApiOperation(value = "获取考核信息", notes = "获取考核信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "考核id", required = true)
//    })
//    @GetMapping("/getAssess")
//    public Result getAssess(Long id) {
//        TsAssess fault = service.getAssess(id);
//        return Result.success(fault);
//    }
//

    @ApiOperation(value = "获取考核列表", notes = "获取考核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户id", required = true),
            @ApiImplicitParam(name = "sytechId", value = "技术id", required = true),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = false),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getAssessList")
    public Result getAssessList(Long customerId, Long sytechId, String startDate, String endDate, String term,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        if (startDate == null || startDate.length() == 0) {
            startDate = MyDate.getTodayStr();
        }
        if (endDate == null || endDate.length() == 0) {
            endDate = MyDate.getTomorrowStr();
        }
        IPage<Map<String, Object>> list = service.getAssessList(new Page(pageNum, pageSize), customerId, sytechId, startDate, endDate, term);
        return Result.success(list);
    }


    public void getResults(MsExaminationPaper msExaminationPaper, TsProblemRecord tsProblemRecord) {
        int a = msExaminationPaper.getA() == null ? 0 : msExaminationPaper.getA();
        int b = msExaminationPaper.getB() == null ? 0 : msExaminationPaper.getB();
        int c = msExaminationPaper.getC() == null ? 0 : msExaminationPaper.getC();

        List<ProblemRquest> problemList = tsProblemRecord.getProblemList();

        int results = 0;

        for (ProblemRquest problem : problemList) {
            switch (problem.getType()) {
                case 1:
                    results += 5;
                    break;
                case 2:
                    results += 4;
                    break;
                case 3:
                    results += 2;
                    break;
                case 4:
                    results += 1;
                    break;
            }
        }

        tsProblemRecord.setResults(results);

        if (results >= a) {
            tsProblemRecord.setEvaluation("a");
        } else if (results >= b) {
            tsProblemRecord.setEvaluation("b");
        } else if (results >= c) {
            tsProblemRecord.setEvaluation("c");
        } else {
            tsProblemRecord.setEvaluation("d");
        }

    }

}
