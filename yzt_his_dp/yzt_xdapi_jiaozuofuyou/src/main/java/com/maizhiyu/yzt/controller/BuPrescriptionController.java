package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.maizhiyu.yzt.bean.aci.HisDoctorCI;
import com.maizhiyu.yzt.bean.aci.HisOutpatientCI;
import com.maizhiyu.yzt.bean.aci.HisPatientCI;
import com.maizhiyu.yzt.bean.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.bean.axo.BuOutpatientXO;
import com.maizhiyu.yzt.bean.axo.BuPatientXO;
import com.maizhiyu.yzt.bean.axo.HsUserXO;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.exception.HisException;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.mapperhis.HisDoctorMapper;
import com.maizhiyu.yzt.mapperhis.HisOutpatientMapper;
import com.maizhiyu.yzt.mapperhis.HisPatientMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.JzfyTreatmentMappingService;
import com.maizhiyu.yzt.serviceimpl.YptTreatmentService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;


@Slf4j
@Api(tags = "HIS处方接口")
@RestController
@RequestMapping("/prescription")
public class BuPrescriptionController {

    @Autowired
    private FeignYptClient yptClient;

    @Autowired
    private HisDoctorMapper doctorMapper;

    @Autowired
    private HisPatientMapper patientMapper;

    @Autowired
    private HisOutpatientMapper outpatientMapper;

    @Autowired
    private YptTreatmentService treatmentService;

    @Value("${customer.name}")
    private String customerName;

    @Resource
    private JzfyTreatmentMappingService jzfyTreatmentMappingService;

    private ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 100L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(Integer.MAX_VALUE), new ThreadPoolExecutor.AbortPolicy());


    @ApiOperation(value = "新增处方(中药)", notes = "新增处方(中药)")
    @PostMapping("/addPrescriptionZhongyao")
    public Result addPrescriptionZhongyao(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionZhongyao ro) {
        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
       /* processDoctor(ro.getDoctorId());
        processPatient(ro.getPatientId());
        processOutpatient(ro.getOutpatientId());*/
        Result result = yptClient.addPrescriptionZhongyao(ro);
        return result;
    }

    @ApiOperation(value = "新增处方(成药)", notes = "新增处方(成药)")
    @PostMapping("/addPrescriptionChengyao")
    public Result addPrescriptionChengyao(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionChengyao ro) {
        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
       /* processDoctor(ro.getDoctorId());
        processPatient(ro.getPatientId());
        processOutpatient(ro.getOutpatientId());*/
        Result<Integer> result = yptClient.addPrescriptionChengyao(ro);
        return result;
    }

    @ApiOperation(value = "新增处方(协定)", notes = "新增处方(协定)")
    @PostMapping("/addPrescriptionXieding")
    public Result addPrescriptionXieding(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionXieding ro) {
        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
       /* processDoctor(ro.getDoctorId());
        processPatient(ro.getPatientId());
        processOutpatient(ro.getOutpatientId());*/
        Result<Integer> result = yptClient.addPrescriptionXieding(ro);
        return result;
    }

    @ApiOperation(value = "新增处方(适宜)", notes = "新增处方(适宜)")
    @PostMapping("/addPrescriptionShiyi")
    public Result addPrescriptionShiyi(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionShiyi ro) {

        //TODO 先要调用his那边的接口然后拿到prescriptionId等信息

        Assert.notNull(ro, "处方数据不能为空!");
        Assert.notNull(ro.getBaseInfo(), "基础信息不能为空!");
        BuPrescriptionRO.AddPrescriptionShiyi.BaseInfo baseInfo = ro.getBaseInfo();

        /*baseInfo.setPatientId(baseInfo.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
        processDoctor(baseInfo.getDoctorId().toString());
        processPatient(baseInfo.getPatientId().toString());
        processOutpatient(baseInfo.getOutpatientId().toString());*/

        if (Objects.nonNull(ro.getBaseInfo())) {
            ro.getDiagnoseInfo().setCustomerName(customerName);
            yptClient.addDiagnose(ro);
        }

        if(CollectionUtils.isEmpty(ro.getItemList()))
            return Result.success();

        Result<Integer> result = yptClient.addPrescriptionShiyi(ro);
        return Result.success(result.getData());
    }

    private void processDoctor(String doctorId) {
        HisDoctor doctor = doctorMapper.selectById(doctorId);
        if (doctor == null) {
            throw new HisException("获取医生信息失败:" + doctorId);
        } else {
            HsUserXO.AddUserXO xo = HisDoctorCI.INSTANCE.toAddUserXO(doctor);
            Result<Object> result = yptClient.addDoctor(xo);
            if (result.getCode() == 0) {
                log.info("添加医生成功：" + result);
            } else {
                log.warn("添加医生失败：" + result);
                throw new HisException("添加医生失败: " + result);
            }
        }
    }

    private void processPatient(String patientId) {
        HisPatient patient = patientMapper.selectById(patientId);
        if (patient == null) {
            throw new HisException("获取患者信息失败:" + patientId);
        } else {
            BuPatientXO.AddPatientXO xo = HisPatientCI.INSTANCE.toAddPatientXO(patient);
            xo.setIdcard(patientId);    // 没有身份证信息使用挂号ID作为身份证（每次挂号都会产生新的患者）
            Result<Object> result = yptClient.addPatient(xo);
            if (result.getCode() == 0) {
                log.info("添加患者成功：" + result);
            } else {
                log.warn("添加患者失败：" + result);
                throw new HisException("添加患者失败: " + result);
            }
        }
    }

    private void processOutpatient(String outpatientId) {
        HisOutpatient outpatient = outpatientMapper.selectById(outpatientId);
        if (outpatient == null) {
            throw new HisException("获取预约信息失败:" + outpatientId);
        } else {
            BuOutpatientXO.AddOutpatientXO xo = HisOutpatientCI.INSTANCE.toAddOutpatientXO(outpatient);
            Result<Object> result = yptClient.addOutpatient(xo);
            if (result.getCode() == 0) {
                log.info("添加预约成功：" + result);
            } else {
                log.warn("添加预约失败：" + result + " - " + xo);
                throw new HisException("添加预约失败: " + result);
            }
        }
    }

}
