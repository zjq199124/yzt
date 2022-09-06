package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.aci.BuPatientCI;
import com.maizhiyu.yzt.aro.BuPatientRO;
import com.maizhiyu.yzt.avo.BuPatientVO;
import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuPatientService;
import com.maizhiyu.yzt.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;


@Api(tags = "患者接口")
@RestController
@RequestMapping("/patient")
public class BuPatientController {

    @Autowired
    private IBuPatientService service;


    @ApiOperation(value = "增加患者(存在则更新)", notes = "增加患者(存在则更新)")
    @PostMapping("/addPatientOrUpdate")
    public Result<BuPatientVO.AddPatientVO> addPatientOrUpdate (HttpServletRequest request, @RequestBody @Valid BuPatientRO.AddPatientRO ro) {
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        // 数据转换
        BuPatient patient = BuPatientCI.INSTANCE.convert(ro);
        patient.setCustomerId(customerId);
        patient.setStatus(1);
        // 查询患者是否存在
        BuPatient oldPatient = service.getPatient(ro.getName(), ro.getPhone(), ro.getIdcard());
        // 不存在则插入
        if (oldPatient == null) {
            patient.setCreateTime(new Date());
            patient.setUpdateTime(patient.getCreateTime());
            service.addPatient(patient);
        }
        // 存在则更新
        else {
            patient.setId(oldPatient.getId());
            patient.setUpdateTime(new Date());
            service.setPatient(patient);
        }
        // 获取最新数据
        BuPatient newPatient = service.getPatient(patient.getName(), patient.getPhone(), patient.getIdcard());
        BuPatientVO.AddPatientVO vo = BuPatientCI.INSTANCE.invertAddPatientVO(newPatient);
        // 返回结果
        return Result.success(vo);
    }

}
