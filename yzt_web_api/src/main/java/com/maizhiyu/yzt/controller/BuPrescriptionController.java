package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.maizhiyu.yzt.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.*;
import com.maizhiyu.yzt.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Api(tags = "处方接口")
@RestController
@RequestMapping("/prescription")
public class BuPrescriptionController {

    @Autowired
    private IBuPrescriptionService service;

    @Autowired
    private IHsUserService hsUserService;

    @Autowired
    private IBuPatientService buPatientService;

    @Autowired
    private IBuOutpatientService buOutpatientService;

    @Autowired
    private ITsSytechService sytechService;

    @Resource
    private IBuDiagnoseService diagnoseService;


    @ApiOperation(value = "新增处方(中药)", notes = "新增处方(中药)")
    @PostMapping("/addPrescriptionZhongyao")
    public Result<Integer> addPrescriptionZhongyao(HttpServletRequest request, @RequestBody BuPrescriptionRO.AddPrescriptionZhongyao ro) {
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        // 获取医生信息
        HsUser hsUser = hsUserService.getUserByHisId(customerId, Long.valueOf(ro.getDoctorId()));
        if (hsUser == null) return Result.failure(10002, "医生信息错误");
        // 获取患者信息
        BuPatient buPatient = buPatientService.getPatientByHisId(customerId, Long.valueOf(ro.getPatientId()));
        if (buPatient == null) return Result.failure(10003, "患者信息错误");
        // 获取预约信息
        BuOutpatient buOutpatient = buOutpatientService.getOutpatientByHisId(customerId,Long.valueOf(ro.getOutpatientId()));
        if (buOutpatient == null) return Result.failure(10004, "预约信息错误");
        // 整理处方数据
        List<BuPrescriptionItem> itemList = new ArrayList<>();
        BuPrescription prescription = new BuPrescription();
        prescription.setId(ro.getId());
        prescription.setType(2);
        prescription.setStatus(1);
        prescription.setCustomerId(customerId);
        prescription.setDoctorId(hsUser.getId());
        prescription.setPatientId(buPatient.getId());
        prescription.setOutpatientId(buOutpatient.getId());
        prescription.setAttention(ro.getAttention());
        prescription.setDayCount(ro.getDayCount());
        prescription.setDoseCount(ro.getDoseCount());
        prescription.setDoseTimes(ro.getDoseTimes());
        prescription.setCreateTime(new Date());
        prescription.setUpdateTime(prescription.getCreateTime());
        prescription.setHisId(ro.getHisId());
        prescription.setItemList(itemList);
        for (BuPrescriptionRO.BuPrescriptionItemZhongyao it : ro.getItemList()) {
            BuPrescriptionItem item = new BuPrescriptionItem();
            item.setType(1);
            item.setCustomerId(customerId);
            item.setDoctorId(hsUser.getId());
            item.setPatientId(buPatient.getId());
            item.setOutpatientId(buOutpatient.getId());
            item.setName(it.getName());
            item.setUnit(it.getUnit());
            item.setDosage(it.getDosage());
            item.setUsage(it.getUsage());
            item.setNote(it.getNote());
            itemList.add(item);
        }
        // 保存药材
        //Integer res = service.addPrescription(prescription);
        Integer res = service.saveOrUpdate(prescription);
        // 返回结果
        return Result.success(res);
    }

    @ApiOperation(value = "新增处方(成药)", notes = "新增处方(成药)")
    @PostMapping("/addPrescriptionChengyao")
    public Result<Integer> addPrescriptionChengyao(HttpServletRequest request, @RequestBody BuPrescriptionRO.AddPrescriptionChengyao ro) {
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        // 获取医生信息
        HsUser hsUser = hsUserService.getUserByHisId(customerId, Long.valueOf(ro.getDoctorId()));
        if (hsUser == null) return Result.failure(10002, "医生信息错误");
        // 获取患者信息
        BuPatient buPatient = buPatientService.getPatientByHisId(customerId, Long.valueOf(ro.getPatientId()));
        if (buPatient == null) return Result.failure(10003, "患者信息错误");
        // 获取预约信息
        BuOutpatient buOutpatient = buOutpatientService.getOutpatientByHisId(customerId, Long.valueOf(ro.getOutpatientId()));
        if (buOutpatient == null) return Result.failure(10004, "预约信息错误");
        // 整理处方数据
        List<BuPrescriptionItem> itemList = new ArrayList<>();
        BuPrescription prescription = new BuPrescription();
        prescription.setType(1);
        prescription.setStatus(1);
        prescription.setCustomerId(customerId);
        prescription.setDoctorId(hsUser.getId());
        prescription.setPatientId(buPatient.getId());
        prescription.setOutpatientId(buOutpatient.getId());
        prescription.setAttention(ro.getAttention());
        prescription.setCreateTime(new Date());
        prescription.setUpdateTime(prescription.getCreateTime());
        prescription.setHisId(ro.getId());
        prescription.setItemList(itemList);
        for (BuPrescriptionRO.BuPrescriptionItemChengyao it : ro.getItemList()) {
            BuPrescriptionItem item = new BuPrescriptionItem();
            item.setType(1);
            item.setCustomerId(customerId);
            item.setDoctorId(hsUser.getId());
            item.setPatientId(buPatient.getId());
            item.setOutpatientId(buOutpatient.getId());
            item.setName(it.getName());
            item.setDosage(it.getDosage());
            item.setFrequency(it.getFrequency());
            item.setDays(it.getDays());
            item.setQuantity(it.getQuantity());
            item.setUsage(it.getUsage());
            item.setNote(it.getNote());
            itemList.add(item);
        }
        // 保存药材
        Integer res = service.addPrescription(prescription);
        // 返回结果
        return Result.success(res);
    }

    @ApiOperation(value = "新增处方(协定)", notes = "新增处方(协定)")
    @PostMapping("/addPrescriptionXieding")
    public Result<Integer> addPrescriptionXieding(HttpServletRequest request, @RequestBody BuPrescriptionRO.AddPrescriptionXieding ro) {
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        // 获取医生信息
        HsUser hsUser = hsUserService.getUserByHisId(customerId, Long.valueOf(ro.getDoctorId()));
        if (hsUser == null) return Result.failure(10002, "医生信息错误");
        // 获取患者信息
        BuPatient buPatient = buPatientService.getPatientByHisId(customerId, Long.valueOf(ro.getPatientId()));
        if (buPatient == null) return Result.failure(10003, "患者信息错误");
        // 获取预约信息
        BuOutpatient buOutpatient = buOutpatientService.getOutpatientByHisId(customerId, Long.valueOf(ro.getOutpatientId()));
        if (buOutpatient == null) return Result.failure(10004, "预约信息错误");
        // 整理处方数据
        List<BuPrescriptionItem> itemList = new ArrayList<>();
        BuPrescription prescription = new BuPrescription();
        prescription.setType(4);
        prescription.setStatus(1);
        prescription.setCustomerId(customerId);
        prescription.setDoctorId(hsUser.getId());
        prescription.setPatientId(buPatient.getId());
        prescription.setOutpatientId(buOutpatient.getId());
        prescription.setDayCount(ro.getCount());
        prescription.setAttention(ro.getAttention());
        prescription.setCreateTime(new Date());
        prescription.setUpdateTime(prescription.getCreateTime());
        prescription.setHisId(ro.getId());
        prescription.setItemList(itemList);
        for (BuPrescriptionRO.BuPrescriptionItemXieding it : ro.getItemList()) {
            BuPrescriptionItem item = new BuPrescriptionItem();
            item.setType(1);
            item.setCustomerId(customerId);
            item.setDoctorId(hsUser.getId());
            item.setPatientId(buPatient.getId());
            item.setOutpatientId(buOutpatient.getId());
            item.setName(it.getName());
            item.setUnit(it.getUnit());
            item.setDosage(it.getDosage());
            item.setUsage(it.getUsage());
            item.setNote(it.getNote());
            itemList.add(item);
        }
        // 保存药材
        Integer res = service.addPrescription(prescription);
        // 返回结果
        return Result.success(res);
    }

    @ApiOperation(value = "新增处方(适宜技术)", notes = "新增处方(适宜技术)")
    @PostMapping("/addPrescriptionShiyi")
    public Result<Long> addPrescriptionShiyi(HttpServletRequest request, @RequestBody BuPrescriptionRO.AddPrescriptionShiyi ro) {
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        // 获取医生信息
        HsUser hsUser = hsUserService.getUserByHisId(customerId, ro.getBaseInfo().getDoctorId());
        if (hsUser == null) return Result.failure(10002, "医生信息错误");
        // 获取患者信息
        BuPatient buPatient = buPatientService.getPatientByHisId(customerId, ro.getBaseInfo().getPatientId());
        if (buPatient == null) return Result.failure(10003, "患者信息错误");
        // 获取预约信息
        BuOutpatient buOutpatient = buOutpatientService.getOutpatientByHisId(customerId, ro.getBaseInfo().getOutpatientId());
        if (buOutpatient == null) return Result.failure(10004, "预约信息错误");

        // 整理处方数据
        List<BuPrescriptionItem> itemList = new ArrayList<>();
        BuPrescription prescription = new BuPrescription();
        prescription.setId(ro.getId());
        prescription.setType(5);
        prescription.setStatus(1);
        prescription.setCustomerId(customerId);
        prescription.setDoctorId(hsUser.getId());
        prescription.setPatientId(buPatient.getId());
        prescription.setOutpatientId(buOutpatient.getId());
        prescription.setAttention(ro.getAttention());
        prescription.setCreateTime(new Date());
        prescription.setUpdateTime(prescription.getCreateTime());
        prescription.setHisId(ro.getHisId());
        prescription.setItemList(itemList);
        for (BuPrescriptionRO.BuPrescriptionItemShiyi it : ro.getItemList()) {
            BuPrescriptionItem item = new BuPrescriptionItem();
            item.setType(1);
            item.setCustomerId(customerId);
            item.setDoctorId(hsUser.getId());
            item.setPatientId(buPatient.getId());
            item.setOutpatientId(buOutpatient.getId());
            item.setId(it.getId());
            item.setName(it.getName());
            item.setDetail(it.getDetail());
            item.setOperation(it.getOperation());
            item.setQuantity(new BigDecimal(it.getQuantity()));
            item.setNote(it.getNote());
//            // 实现一：传入参数中code就是适宜技术的id（string）数据库中存储是int格式，需要转格式
//            try {
//                Long entityId = Long.parseLong(it.getCode());
//                item.setEntityId(entityId);
//            } catch (Exception e) {
//                log.warn("转化适宜技术ID异常 " + it);
//            }
            // 实现二：根据名称查询entityId
            try {
                TsSytech sytech = sytechService.getSytechByName(it.getName());
                item.setEntityId(sytech.getId());
                log.info("查询适宜技术成功 " + sytech);
            } catch (Exception e) {
                log.warn("查询适宜技术异常 " + it);
            }
            // 添加到列表
            itemList.add(item);
        }
        // 保存药材
        Integer res = service.saveOrUpdate(prescription);
        // 返回结果
        return Result.success(res);
    }

}
