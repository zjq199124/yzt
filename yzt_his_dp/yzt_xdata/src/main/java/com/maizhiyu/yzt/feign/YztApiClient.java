package com.maizhiyu.yzt.feign;

import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.intercept.YztApiInterceptor;
import com.maizhiyu.yzt.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;


@FeignClient(name = "yzt", url = "${xdata.api.url}", configuration = YztApiInterceptor.class)
public interface YztApiClient {

    @PostMapping(value = "/patient/addPatientOrUpdate")
    Result<BuPatient> addPatientOrUpdate(
            @RequestBody BuPatient patient);

    @PostMapping(value = "/outpatient/addOutpatient")
    Result<BuOutpatient> addOutpatient(
            @RequestBody BuOutpatient outpatient);

    @PostMapping(value = "/diagnose/getDiagnoseList")
    Result<List<Map<String, Object>>> getDiagnoseList(
            @RequestParam(value = "customerId") Long customerId,
            @RequestParam(value = "start") String start,
            @RequestParam(value = "end") String end);

    @PostMapping(value = "/prescription/getPrescriptionList")
    Result<List<Map<String, Object>>> getPrescriptionList(
            @RequestParam(value = "customerId") Long customerId,
            @RequestParam(value = "start") String start,
            @RequestParam(value = "end") String end);
}
