package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.maizhiyu.yzt.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuOutpatientService;
import com.maizhiyu.yzt.service.IBuPatientService;
import com.maizhiyu.yzt.service.IBuPrescriptionService;
import com.maizhiyu.yzt.service.IHsUserService;
import com.maizhiyu.yzt.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "新增处方(中药)", notes = "新增处方(中药)")
    @PostMapping("/addPrescriptionZhongyao")
    public Result<Boolean> addPrescriptionZhongyao(HttpServletRequest request, @RequestBody BuPrescriptionRO.AddPrescriptionZhongyao ro) {
        // 获取token字段
        Long customerId = (Integer) JwtTokenUtils.getField(request, "id") + 0L;
        if (customerId == null) return Result.failure(10001, "token错误");
        checkYptDataExist(Long.valueOf(ro.getDoctorId()), Long.valueOf(ro.getPatientId()), Long.valueOf(ro.getOutpatientId()));
        // 整理处方数据
        List<BuPrescriptionItem> itemList = new ArrayList<>();
        BuPrescription prescription = new BuPrescription();
        prescription.setId(ro.getId());
        prescription.setType(2);
        prescription.setStatus(1);
        prescription.setCustomerId(customerId);
        prescription.setDoctorId(Long.valueOf(ro.getDoctorId()));
        prescription.setPatientId(Long.valueOf(ro.getPatientId()));
        prescription.setOutpatientId(Long.valueOf(ro.getOutpatientId()));
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
            item.setDoctorId(Long.valueOf(ro.getDoctorId()));
            item.setPatientId(Long.valueOf(ro.getPatientId()));
            item.setOutpatientId(Long.valueOf(ro.getOutpatientId()));
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
        checkYptDataExist(Long.valueOf(ro.getDoctorId()), Long.valueOf(ro.getPatientId()), Long.valueOf(ro.getOutpatientId()));
        // 整理处方数据
        List<BuPrescriptionItem> itemList = new ArrayList<>();
        BuPrescription prescription = new BuPrescription();
        prescription.setType(1);
        prescription.setStatus(1);
        prescription.setCustomerId(customerId);
        prescription.setDoctorId(Long.valueOf(ro.getDoctorId()));
        prescription.setPatientId(Long.valueOf(ro.getPatientId()));
        prescription.setOutpatientId(Long.valueOf(ro.getOutpatientId()));
        prescription.setAttention(ro.getAttention());
        prescription.setCreateTime(new Date());
        prescription.setUpdateTime(prescription.getCreateTime());
        prescription.setHisId(ro.getId());
        prescription.setItemList(itemList);
        for (BuPrescriptionRO.BuPrescriptionItemChengyao it : ro.getItemList()) {
            BuPrescriptionItem item = new BuPrescriptionItem();
            item.setType(1);
            item.setCustomerId(customerId);
            item.setDoctorId(Long.valueOf(ro.getDoctorId()));
            item.setPatientId(Long.valueOf(ro.getPatientId()));
            item.setOutpatientId(Long.valueOf(ro.getOutpatientId()));
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
        checkYptDataExist(Long.valueOf(ro.getDoctorId()), Long.valueOf(ro.getPatientId()), Long.valueOf(ro.getOutpatientId()));
        // 整理处方数据
        List<BuPrescriptionItem> itemList = new ArrayList<>();
        BuPrescription prescription = new BuPrescription();
        prescription.setType(4);
        prescription.setStatus(1);
        prescription.setCustomerId(customerId);
        prescription.setDoctorId(Long.valueOf(ro.getDoctorId()));
        prescription.setPatientId(Long.valueOf(ro.getPatientId()));
        prescription.setOutpatientId(Long.valueOf(ro.getOutpatientId()));
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
            item.setDoctorId(Long.valueOf(ro.getDoctorId()));
            item.setPatientId(Long.valueOf(ro.getPatientId()));
            item.setOutpatientId(Long.valueOf(ro.getOutpatientId()));
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
        checkYptDataExist(ro.getBaseInfo().getDoctorId(), ro.getBaseInfo().getPatientId(), ro.getBaseInfo().getOutpatientId());
        // 整理处方数据
        List<BuPrescriptionItem> itemList = new ArrayList<>();
        BuPrescription prescription = new BuPrescription();
        prescription.setId(ro.getId());
        prescription.setType(5);
        prescription.setStatus(1);
        prescription.setCustomerId(customerId);
        prescription.setDoctorId(ro.getBaseInfo().getDoctorId());
        prescription.setPatientId(ro.getBaseInfo().getPatientId());
        prescription.setOutpatientId(ro.getBaseInfo().getOutpatientId());
        prescription.setAttention(ro.getAttention());
        prescription.setCreateTime(new Date());
        prescription.setUpdateTime(prescription.getCreateTime());
        prescription.setHisId(ro.getHisId());
        prescription.setItemList(itemList);
        for (BuPrescriptionRO.BuPrescriptionItemShiyi it : ro.getItemList()) {
            BuPrescriptionItem item = new BuPrescriptionItem();
            item.setType(1);
            item.setCustomerId(it.getCustomerId());
            item.setDoctorId(ro.getBaseInfo().getDoctorId());
            item.setPatientId(ro.getBaseInfo().getPatientId());
            item.setOutpatientId(ro.getBaseInfo().getOutpatientId());
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

    /**
     * 判断云平台是否存在，医生，患者，门诊信息
     *
     * @return
     */
    public void checkYptDataExist(Long doctorId, Long patientId, Long outPatientId) {
        // 获取医生信息
        HsUser hsUser = hsUserService.getById(doctorId);
        Assert.notNull(hsUser, "医生信息错误!");
        // 获取患者信息
        BuPatient buPatient = buPatientService.getById(patientId);
        Assert.notNull(buPatient, "患者信息错误!");
        // 获取预约信息
        BuOutpatient buOutpatient = buOutpatientService.getById(outPatientId);
        Assert.notNull(buOutpatient, "预约信息错误!");
    }

}
