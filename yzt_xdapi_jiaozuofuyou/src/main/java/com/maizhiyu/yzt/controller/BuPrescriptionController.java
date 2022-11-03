package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.bean.aci.HisDoctorCI;
import com.maizhiyu.yzt.bean.aci.HisOutpatientCI;
import com.maizhiyu.yzt.bean.aci.HisPatientCI;
import com.maizhiyu.yzt.bean.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.bean.axo.BuOutpatientXO;
import com.maizhiyu.yzt.bean.axo.BuPatientXO;
import com.maizhiyu.yzt.bean.axo.HsUserXO;
import com.maizhiyu.yzt.entity.HisDoctor;
import com.maizhiyu.yzt.entity.HisOutpatient;
import com.maizhiyu.yzt.entity.HisPatient;
import com.maizhiyu.yzt.entity.YptTreatment;
import com.maizhiyu.yzt.exception.HisException;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.mapperhis.HisDoctorMapper;
import com.maizhiyu.yzt.mapperhis.HisOutpatientMapper;
import com.maizhiyu.yzt.mapperhis.HisPatientMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.serviceimpl.YptTreatmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


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


    @ApiOperation(value = "新增处方(中药)", notes = "新增处方(中药)")
    @PostMapping("/addPrescriptionZhongyao")
    public Result addPrescriptionZhongyao(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionZhongyao ro) {
        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
        processDoctor(ro.getDoctorId());
        processPatient(ro.getPatientId());
        processOutpatient(ro.getOutpatientId());
        Result result = yptClient.addPrescriptionZhongyao(ro);
        return result;
    }

    @ApiOperation(value = "新增处方(成药)", notes = "新增处方(成药)")
    @PostMapping("/addPrescriptionChengyao")
    public Result addPrescriptionChengyao(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionChengyao ro) {
        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
        processDoctor(ro.getDoctorId());
        processPatient(ro.getPatientId());
        processOutpatient(ro.getOutpatientId());
        Result<Integer> result = yptClient.addPrescriptionChengyao(ro);
        return result;
    }

    @ApiOperation(value = "新增处方(协定)", notes = "新增处方(协定)")
    @PostMapping("/addPrescriptionXieding")
    public Result addPrescriptionXieding(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionXieding ro) {
        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
        processDoctor(ro.getDoctorId());
        processPatient(ro.getPatientId());
        processOutpatient(ro.getOutpatientId());
        Result<Integer> result = yptClient.addPrescriptionXieding(ro);
        return result;
    }

    @ApiOperation(value = "新增处方(适宜)", notes = "新增处方(适宜)")
    @PostMapping("/addPrescriptionShiyi")
    public Result addPrescriptionShiyi(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionShiyi ro) {
        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
        processDoctor(ro.getDoctorId());
        processPatient(ro.getPatientId());
        processOutpatient(ro.getOutpatientId());
        // 适宜技术映射(name code 修改前是his内数据，修改后是ypt内数据)
        for (BuPrescriptionRO.BuPrescriptionItemShiyi item : ro.getItemList()) {
            try {
                YptTreatment treatment = null;
                // 先按code映射
                if (item.getCode() != null && item.getCode().length() > 0) {
                    treatment = treatmentService.getTreatmentByHisCode(item.getCode());
                }
                // 再按name映射
                if (treatment == null) {
                    treatment = treatmentService.getTreatmentByHisName(item.getName());
                }
                // 修改为映射后的数据
                item.setCode(treatment.getCode());
                item.setName(treatment.getName());
                log.info("映射后适宜技术信息: " + item);
            } catch (Exception e) {
                log.warn("获取适宜技术映射异常 " + item + " - " + e.getMessage());
            }
        }
        Result<Integer> result = yptClient.addPrescriptionShiyi(ro);
        return result;
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
