package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.*;
import com.maizhiyu.yzt.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Api(tags = "处方接口")
@RestController
@RequestMapping("/prescription")
public class BuPrescriptionController {

    @Autowired
    private IBuPrescriptionService iBuPrescriptionService;

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

    @Resource
    private IBuPrescriptionItemService buPrescriptionItemService;

    @ApiOperation(value = "新增处方(中药)", notes = "新增处方(中药)")
    @PostMapping("/addPrescriptionZhongyao")
    public Result<Boolean> addPrescriptionZhongyao(HttpServletRequest request, @RequestBody BuPrescriptionRO.AddPrescriptionZhongyao ro) {
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
        Boolean res = iBuPrescriptionService.saveOrUpdate(prescription);
        // 返回结果
        return Result.success(res);
    }

    @ApiOperation(value = "新增处方(成药)", notes = "新增处方(成药)")
    @PostMapping("/addPrescriptionChengyao")
    public Result<Boolean> addPrescriptionChengyao(HttpServletRequest request, @RequestBody BuPrescriptionRO.AddPrescriptionChengyao ro) {
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
        Boolean res = iBuPrescriptionService.addPrescription(prescription);
        // 返回结果
        return Result.success(res);
    }

    @ApiOperation(value = "新增处方(协定)", notes = "新增处方(协定)")
    @PostMapping("/addPrescriptionXieding")
    public Result<Boolean> addPrescriptionXieding(HttpServletRequest request, @RequestBody BuPrescriptionRO.AddPrescriptionXieding ro) {
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
        Boolean res = iBuPrescriptionService.addPrescription(prescription);
        // 返回结果
        return Result.success(res);
    }

    @ApiOperation(value = "新增处方(适宜技术)", notes = "新增处方(适宜技术)")
    @PostMapping("/addPrescriptionShiyi")
    public Result<Boolean> addPrescriptionShiyi(HttpServletRequest request, @RequestBody BuPrescriptionRO.AddPrescriptionShiyi ro) {
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        // 获取医生信息
        HsUser hsUser = hsUserService.getById(ro.getBaseInfo().getDoctorId());
        if (hsUser == null) return Result.failure(10002, "医生信息错误");
        // 获取患者信息
        BuPatient buPatient = buPatientService.getById(ro.getBaseInfo().getPatientId());
        if (buPatient == null) return Result.failure(10003, "患者信息错误");
        // 获取预约信息
        BuOutpatient buOutpatient = buOutpatientService.getById(ro.getBaseInfo().getOutpatientId());
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
            item.setCustomerId(it.getCustomerId());
            item.setDoctorId(hsUser.getId());
            item.setPatientId(buPatient.getId());
            item.setOutpatientId(buOutpatient.getId());
            item.setId(it.getId());
            item.setName(it.getName());
            item.setDetail(it.getDetail());
            item.setOperation(it.getOperation());
            item.setQuantity(new BigDecimal(it.getQuantity()));
            item.setNote(it.getNote());
            item.setEntityId(it.getEntityId());
            // 添加到列表
            itemList.add(item);
        }
        // 保存药材
        Boolean res = iBuPrescriptionService.setPrescriptionByDiff(prescription, ro.getPreItemIdList());
        // 返回结果
        return Result.success(res);
    }

}
