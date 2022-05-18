package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuDiagnoseService;
import com.maizhiyu.yzt.service.IBuPrescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@Api(tags = "处方接口")
@RestController
@RequestMapping("/prescription")
public class BuPrescriptionController {

    @Autowired
    private IBuPrescriptionService prescriptionService;

    @Autowired
    private IBuDiagnoseService diagnoseService;


    @ApiOperation(value = "获取处方列表", notes = "获取处方列表")
    @PostMapping("/getPrescriptionList")
    public Result<BuPrescription> getPrescriptionList (
            @RequestParam @NotNull Long customerId,
            @RequestParam @NotNull String start,
            @RequestParam @NotNull String end) {
        // 查询处方列表
        List<Map<String, Object>> list = prescriptionService.getPrescriptionList(customerId, start, end);
        // 补充诊断ID
        for (Map<String, Object> map : list) {
            Long outpatientId = (Long) map.get("outpatientId");
            BuDiagnose diagnose = diagnoseService.getDiagnoseOfOutpatient(outpatientId);
            map.put("diagnoseId", diagnose.getId());
        }
        return Result.success(list);
    }

}
