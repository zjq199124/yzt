package com.maizhiyu.yzt.controller;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.base.BaseController;
import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuPatientService;
import com.maizhiyu.yzt.vo.MzBrjbxxbVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "患者接口")
@RestController
@RequestMapping("/patient")
public class BuPatientController extends BaseController {

    @Autowired
    private IBuPatientService service;


    @ApiOperation(value = "增加患者", notes = "增加患者")
    @ApiImplicitParams({})
    @PostMapping("/addPatient")
    public Result addPatient (@RequestBody BuPatient patient) {
        Integer i = service.selectByRbId(patient.getRbId());
        if(i > 0) {
            throw new BusinessException("已经有了这条数据");
        }
        
        patient.setStatus(1);
        patient.setCreateTime(new Date());
        patient.setUpdateTime(patient.getCreateTime());
        Integer res = service.addPatient(patient);


        patient.setRbId(1L);
        return Result.success(patient);
    }




    @ApiOperation(value = "删除患者", notes = "删除患者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "患者id", required = true)
    })
    @GetMapping("/delPatient")
    public Result delPatient(Long id) {
        Integer res = service.delPatient(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改患者信息", notes = "修改患者信息")
    @PostMapping("/setPatient")
    public Result setPatient (@RequestBody BuPatient patient) {
        patient.setUpdateTime(new Date());
        Integer res = service.setPatient(patient);
        return Result.success(patient);
    }


    @ApiOperation(value = "获取患者信息", notes = "获取患者信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "患者ID", required = true),
    })
    @GetMapping("/getPatient")
    public Result getPatient(Long id) {
        BuPatient patient = service.getPatient(id);
        return Result.success(patient);
    }

    @ApiOperation(value = "获取患者信息-w", notes = "获取患者信息-w")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "q", value = "3134", required = true)
    })
    @ApiImplicitParam(value = "鉴权token",name = "token",paramType  = "header", dataType = "String", required=true)
    @GetMapping("/getPatient-w")
    public Result getPatient2(@RequestParam Long id) {
        String content = id + "";
        Long customerId = ((Number) getClaims().get("customerId")).longValue();
        if(customerId != 28) {
            throw new BusinessException("当前客户没有此权限");
        }
        //随机生成密钥
        byte[] key = "ohbtestohbtest11".getBytes();

        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

        //加密为16进制表示
        String encryptHex = aes.encryptHex(content);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("token", encryptHex);
        String result= HttpUtil.post("ebd6-36-27-126-199.ngrok.io/mzBrjbxxbDO/test", paramMap);
        MzBrjbxxbVO mzBrjbxxbVO = JSONObject.parseObject(result, MzBrjbxxbVO.class);

        return Result.success(mzBrjbxxbVO);
    }



    @ApiOperation(value = "获取患者列表", notes = "获取患者列表(搜索使用)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", required = true),
            @ApiImplicitParam(name = "term", value = "搜索词", required = true),
    })
    @GetMapping("/getPatientList")
    public Result getPatientList(Long customerId, String term) {
        List<BuPatient> list = service.getPatientList(customerId, term);
        return Result.success(list);
    }


    @ApiOperation(value = "获取患者处方单列表", notes = "获取患者处方单列表，已经包含处方单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientId", value = "患者ID", required = true),
            @ApiImplicitParam(name = "type", value = "处方类型", required = false),
    })
    @GetMapping("/getPatientPrescriptionList")
    public Result getPatientPrescriptionList(Long patientId, Integer type) {
        List<Map<String, Object>> list = service.getPatientPrescriptionList(patientId, type);
        return Result.success(list);
    }


    @ApiOperation(value = "获取医生诊断过的患者列表", notes = "获取医生诊断过的患者列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "doctorId", value = "医生ID", required = true),
            @ApiImplicitParam(name = "term", value = "搜索词", required = true),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getPatientListByDoctor")
    public Result getPatientListByDoctor(Long doctorId, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getPatientListByDoctor(doctorId, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }


    @ApiOperation(value = "获取治疗师治疗过的患者列表", notes = "获取治疗师治疗过的患者列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "therapistId", value = "治疗师ID", required = true),
            @ApiImplicitParam(name = "type", value = "处方类型", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = true),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getPatientListByTherapist")
    public Result getPatientListByTherapist(Long therapistId, Integer type, String term,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = service.getPatientListByTherapist(therapistId, type, term);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list, pageSize);
        return Result.success(pageInfo);
    }
}
