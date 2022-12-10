package com.maizhiyu.yzt.controller;


import com.maizhiyu.yzt.aci.BuOutpatientCI;
import com.maizhiyu.yzt.aro.BuOutpatientRO;
import com.maizhiyu.yzt.avo.BuOutpatientVO;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuOutpatientService;
import com.maizhiyu.yzt.service.IBuPatientService;
import com.maizhiyu.yzt.service.IHsUserService;
import com.maizhiyu.yzt.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;


@Api(tags = "门诊预约接口")
@RestController
@RequestMapping("/outpatient")
public class BuOutpatientController {

    @Autowired
    private IBuOutpatientService service;

    @Autowired
    private IHsUserService hsUserService;

    @Autowired
    private IBuPatientService buPatientService;


    @ApiOperation(value = "新增门诊预约His", notes = "新增门诊预约His")
    @PostMapping("/addOutpatientHis")
    public Result<Long> addOutpatient(HttpServletRequest request, @RequestBody @Valid BuOutpatientRO.AddOutpatientRO ro) {
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        // 获取医生信息
        HsUser hsUser = hsUserService.getUserByHisId(customerId, Long.valueOf(ro.getDoctorId()));
        if (hsUser == null) return Result.failure(10002, "医生信息错误");
        // 获取患者信息
        BuPatient buPatient = buPatientService.getPatientByHisId(customerId, Long.valueOf(ro.getPatientId()));
        if (buPatient == null) return Result.failure(10003, "患者信息错误");
        // 数据转换
        BuOutpatient outpatient = new BuOutpatient();
        outpatient.setCustomerId(customerId);
        outpatient.setStatus(1);
        outpatient.setTime(ro.getTime());
        outpatient.setDoctorId(hsUser.getId());
        outpatient.setPatientId(buPatient.getId());
        outpatient.setHisId(ro.getHisId());
        outpatient.setExtra(ro.getExtra());
        // 查询预约是否存储
        BuOutpatient oldOutpatient = service.getOutpatientByHisId(customerId, Long.valueOf(ro.getHisId()));
        // 不存在则插入
        if (oldOutpatient == null) {
            outpatient.setCreateTime(new Date());
            outpatient.setUpdateTime(outpatient.getCreateTime());
            service.addOutpatient(outpatient);
        }
        // 存在则更新
        else {
            outpatient.setId(oldOutpatient.getId());
            outpatient.setCreateTime(new Date());
            service.setOutpatient(outpatient);
        }
        // 获取最新数据
        BuOutpatient newOutpatient = service.getOutpatientByHisId(customerId, Long.valueOf(ro.getHisId()));
        BuOutpatientVO.AddOutpatientVO vo = BuOutpatientCI.INSTANCE.invertAddOutpatientVO(newOutpatient);
        // 返回结果
        return Result.success(vo.getId());
    }

    @PostMapping(value = "/getYptOutpatientById")
    Result<Object> getYptOutpatientById(@RequestParam(value = "outpatientId") Long outpatientId) {
        BuOutpatient outpatient = service.getOutpatient(outpatientId);
        return Result.success(outpatient);
    }
}
