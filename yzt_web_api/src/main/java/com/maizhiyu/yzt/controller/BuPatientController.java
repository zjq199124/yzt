package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuPatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Api(tags = "患者接口")
@RestController
@RequestMapping("/patient")
public class BuPatientController {

    @Autowired
    private IBuPatientService service;


    @ApiOperation(value = "增加患者(存在则更新)", notes = "增加患者(存在则更新)")
    @PostMapping("/addPatientOrUpdate")
    public Result<BuPatient> addPatientOrUpdate (@RequestBody BuPatient patient) {
        patient.setStatus(1);
        patient.setCreateTime(new Date());
        patient.setUpdateTime(patient.getCreateTime());
        // 查询患者是否存在
        BuPatient oldPatient = service.getPatient(patient.getName(), patient.getPhone(), patient.getIdcard());
        // 不存在则插入
        if (oldPatient == null) {
            service.addPatient(patient);
        }
        // 存在则更新
        else {
            service.setPatient(patient);
        }
        // 获取最新数据
        BuPatient newPatient = service.getPatient(patient.getName(), patient.getPhone(), patient.getIdcard());
        return Result.success(newPatient);
    }

}
