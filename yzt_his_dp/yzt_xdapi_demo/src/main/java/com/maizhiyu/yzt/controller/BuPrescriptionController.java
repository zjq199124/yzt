package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import com.maizhiyu.yzt.bean.aci.HisDepartmentCI;
import com.maizhiyu.yzt.bean.aci.HisDoctorCI;
import com.maizhiyu.yzt.bean.aci.HisOutpatientCI;
import com.maizhiyu.yzt.bean.aci.HisPatientCI;
import com.maizhiyu.yzt.bean.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.bean.aro.TreatmentItemsRo;
import com.maizhiyu.yzt.bean.aro.TreatmentRo;
import com.maizhiyu.yzt.bean.axo.BuOutpatientXO;
import com.maizhiyu.yzt.bean.axo.BuPatientXO;
import com.maizhiyu.yzt.bean.axo.HsDepartmentXO;
import com.maizhiyu.yzt.bean.axo.HsUserXO;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.exception.HisException;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.his.HisApi;
import com.maizhiyu.yzt.mapperhis.HisDepartmentMapper;
import com.maizhiyu.yzt.mapperhis.HisDoctorMapper;
import com.maizhiyu.yzt.mapperhis.HisOutpatientMapper;
import com.maizhiyu.yzt.mapperhis.HisPatientMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.TreatmentMappingService;
import com.maizhiyu.yzt.serviceimpl.YptTreatmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Api(tags = "云平台处方接口")
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

    @Resource
    private HisDepartmentMapper departmentMapper;

    @Autowired
    private YptTreatmentService treatmentService;

    @Resource
    private TreatmentMappingService treatmentMappingService;

    @Value("${customer.name}")
    private String customerName;

    @Value("${customer.id}")
    private Long customerId;

    @Value("${his.url}")
    private String hisUrl;

    @ApiOperation(value = "新增处方(中药)", notes = "新增处方(中药)")
    @PostMapping("/addPrescriptionZhongyao")
    public Result addPrescriptionZhongyao(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionZhongyao ro) {
        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
        Long yptDoctorId = processDoctor(ro.getDoctorId());
        Long yptPatientId = processPatient(ro.getPatientId());
        Long yptOutpatientId = processOutpatient(ro.getOutpatientId(), yptDoctorId, yptPatientId);
        Result result = yptClient.addPrescriptionZhongyao(ro);
        return result;
    }

    @ApiOperation(value = "新增处方(成药)", notes = "新增处方(成药)")
    @PostMapping("/addPrescriptionChengyao")
    public Result addPrescriptionChengyao(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionChengyao ro) {
        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
        Long yptDoctorId = processDoctor(ro.getDoctorId());
        Long yptPatientId = processPatient(ro.getPatientId());
        Long yptOutpatientId = processOutpatient(ro.getOutpatientId(), yptDoctorId, yptPatientId);
        Result<Integer> result = yptClient.addPrescriptionChengyao(ro);
        return result;
    }

    @ApiOperation(value = "新增处方(协定)", notes = "新增处方(协定)")
    @PostMapping("/addPrescriptionXieding")
    public Result addPrescriptionXieding(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionXieding ro) {
        //ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
        Long yptDoctorId = processDoctor(ro.getDoctorId());
        Long yptPatientId = processPatient(ro.getPatientId());
        Long yptOutpatientId = processOutpatient(ro.getOutpatientId(), yptDoctorId, yptPatientId);
        Result<Integer> result = yptClient.addPrescriptionXieding(ro);
        return result;
    }

    @ApiOperation(value = "新增处方(适宜)", notes = "新增处方(适宜)")
    @PostMapping("/addPrescriptionToShiYi")
    public Result addPrescriptionToShiYi(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionShiyi ro) {
//        ro.setPatientId(ro.getOutpatientId());  // 使用outpatientId作为患者ID（HIS就这么给的，每次挂号都会新增患者）
        Long yptDoctorId = processDoctor(String.valueOf(ro.getBaseInfo().getDoctorId()));
        Long yptPatientId = processPatient(String.valueOf(ro.getBaseInfo().getPatientId()));
        Long yptOutpatientId = processOutpatient(String.valueOf(ro.getBaseInfo().getOutpatientId()), yptDoctorId, yptPatientId);

        ro.getBaseInfo().setDoctorId(yptDoctorId);
        ro.getBaseInfo().setPatientId(yptPatientId);
        ro.getBaseInfo().setOutpatientId(yptOutpatientId);
        // 适宜技术映射(name code 修改前是his内数据，修改后是ypt内数据)
        for (BuPrescriptionRO.BuPrescriptionItemShiyi item : ro.getItemList()) {
            try {
                TreatmentMapping treatment = null;
                // 先按code映射
                if (item.getCode() != null && item.getCode().length() > 0) {
                    treatment = treatmentMappingService.getTreatmentByHisCode(item.getCode());
                }
                // 再按name映射
                if (treatment == null) {
                    treatment = treatmentMappingService.getTreatmentByHisName(item.getName());
                }
                // 修改为映射后的数据
                item.setCode(treatment.getCode());
                item.setName(treatment.getName());
                log.info("映射后适宜技术信息: " + item);
            } catch (Exception e) {
                log.warn("获取适宜技术映射异常 " + item + " - " + e.getMessage());
            }
        }
        Result<Boolean> result = yptClient.addPrescriptionShiyi(ro);
        return result;
    }


    //TODO 这里是对接his的数据保存到云平台，内嵌页面的保存应当按照我们的处方保存接口接收数据保存数据
    @ApiOperation(value = "嵌入页面诊断与处置保存", notes = "嵌入页面诊断与处置保存")
    @PostMapping("/addPrescriptionShiyi")
    public Result addPrescriptionShiyi(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionShiyi ro) throws IOException {
        /*Assert.notNull(ro, "处方数据不能为空!");
        Assert.notNull(ro.getBaseInfo(), "基础信息不能为空!");
        Assert.notNull(ro.getDiagnoseInfo(), "诊断信息不能为空!");
        BuPrescriptionRO.AddPrescriptionShiyi.BaseInfo baseInfo = ro.getBaseInfo();
        //判断医生，患者，患者门诊信息
        Long yptDoctorId = processDoctor(baseInfo.getDoctorId().toString());
        Long yptPatientId = processPatient(baseInfo.getPatientId().toString());
        Long yptOutpatientId = processOutpatient(baseInfo.getOutpatientId().toString(), yptDoctorId, yptPatientId);


        //将处置推送给his
        if (Objects.nonNull(ro) && !CollectionUtils.isEmpty(ro.getItemList())) {
            savePrescriptionShiyiToHis(ro);
        }

        //ro中的outpatientId是视图中的registration_id,要换成code才是我们这边所说的his中medical_record_id对应云平台的his中的outpatientId
        YptOutpatient yptOutpatient = getYptOutpatientById(yptOutpatientId);
        ro.getBaseInfo().setOutpatientId(yptOutpatient.getHisId());
        ro.getDiagnoseInfo().setCustomerName(customerName);
        //将patientId,outPatientId,doctorId替换成云平台对应的数据
        ro.getBaseInfo().setDoctorId(yptDoctorId);
        ro.getBaseInfo().setPatientId(yptPatientId);
        ro.getBaseInfo().setOutpatientId(yptOutpatientId);

        Result<BuDiagnose> result = yptClient.addDiagnose(ro);
        if (result.getCode().equals(0) && !CollectionUtils.isEmpty(ro.getItemList())) {
            if (Objects.isNull(ro.getDiagnoseInfo().getId())) {
                ro.getDiagnoseInfo().setId(result.getData().getId());
            }
            Result<Boolean> res = yptClient.addPrescriptionShiyi(ro);
            return Result.success(res.getData());
        } else {
            return Result.success(result.getData());
        }*/

        /*Assert.notNull(ro, "处方数据不能为空!");
        Assert.notNull(ro.getBaseInfo(), "基础信息不能为空!");
        Assert.notNull(ro.getDiagnoseInfo(), "诊断信息不能为空!");
        BuPrescriptionRO.AddPrescriptionShiyi.BaseInfo baseInfo = ro.getBaseInfo();
        //判断医生，患者，患者门诊信息
        Long yptDoctorId = processDoctor(baseInfo.getDoctorId().toString());
        Long yptPatientId = processPatient(baseInfo.getPatientId().toString());
        Long yptOutpatientId = processOutpatient(baseInfo.getOutpatientId().toString(), yptDoctorId, yptPatientId);*/


       /* //将处置推送给his
        if (Objects.nonNull(ro) && !CollectionUtils.isEmpty(ro.getItemList())) {
            savePrescriptionShiyiToHis(ro);
        }
*/
        //ro中的outpatientId是视图中的registration_id,要换成code才是我们这边所说的his中medical_record_id对应云平台的his中的outpatientId
        /*YptOutpatient yptOutpatient = getYptOutpatientById(yptOutpatientId);
        ro.getBaseInfo().setOutpatientId(yptOutpatient.getHisId());*/
        ro.getDiagnoseInfo().setCustomerName(customerName);
        //将patientId,outPatientId,doctorId替换成云平台对应的数据
        /*ro.getBaseInfo().setDoctorId(yptDoctorId);
        ro.getBaseInfo().setPatientId(yptPatientId);
        ro.getBaseInfo().setOutpatientId(yptOutpatientId);*/

        Result<BuDiagnose> result = yptClient.addDiagnose(ro);
        if (!(result.getCode().equals(0) && !CollectionUtils.isEmpty(ro.getItemList())))
            return Result.failure();

        if (Objects.isNull(ro.getDiagnoseInfo().getId())) {
            ro.getDiagnoseInfo().setId(result.getData().getId());
        }
        //将处置推送给his
        if (!(Objects.nonNull(ro) && !CollectionUtils.isEmpty(ro.getItemList())))
            return Result.failure();

        boolean hisResult = savePrescriptionShiyiToHis(ro);

        if (!hisResult)
            return Result.failure();

        //保存一份回显数据到云平台中
        Result<Boolean> res = yptClient.addPrescriptionShiyi(ro);
        return Result.success(res.getData());
    }

    private boolean savePrescriptionShiyiToHis(BuPrescriptionRO.AddPrescriptionShiyi ro) throws IOException {
        Gson gson = new Gson();
        //克隆出一个对象用来进行翻译操作
        Object cloneObj = ObjectUtil.clone(ro);
        JSONObject json = JSON.parseObject(gson.toJson(cloneObj));
        BuPrescriptionRO.AddPrescriptionShiyi clone = JSON.toJavaObject(json, BuPrescriptionRO.AddPrescriptionShiyi.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(hisUrl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        HisApi hisApi = retrofit.create(HisApi.class);

        //his中处置id不为空的话那么先删除his中的处置
        /*if (Objects.nonNull(clone.getHisId())) {
            try {
                Call<Object> repos = hisApi.cancelTreatmentById(Integer.parseInt(clone.getHisId()));
                repos.execute().body();
            } catch (Exception e) {
                log.warn("处治保存到his失败!");
                e.printStackTrace();
            }
        }*/

        TreatmentRo treatmentRo = new TreatmentRo();
        treatmentRo.setDoctorId(clone.getBaseInfo().getDoctorId().intValue());

        treatmentRo.setMedicalRecordId(ro.getBaseInfo().getOutpatientId().intValue());

        List<TreatmentItemsRo> treatmentItemsRoList = clone.getItemList().stream().map(item -> {
            TreatmentItemsRo treatmentItemsRo = new TreatmentItemsRo();
            treatmentItemsRo.setFmedicalItemsId(item.getEntityId().intValue());
            treatmentItemsRo.setQuantity(item.getQuantity());
            return treatmentItemsRo;
        }).collect(Collectors.toList());

        treatmentRo.setTreatmentItemsList(treatmentItemsRoList);

        String treatmentRoJstr = gson.toJson(treatmentRo);

        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("application/json; charset=utf-8"), treatmentRoJstr);
        Call<Object> repos = hisApi.insertTreatment(body);
        Object result = repos.execute().body();
        try {
            if (Objects.nonNull(result)) {
                JSONObject jsonObject = JSON.parseObject(gson.toJson(result));
                if ("OK".equals(jsonObject.get("status"))) {
                    Long hisId = new BigDecimal(jsonObject.get("data").toString()).longValue();
                    ro.setHisId(hisId.toString());
                }
            }
        } catch (Exception e) {
            log.warn("处治保存到his失败!");
            e.printStackTrace();
        }
        return true;
    }

    private Long processDoctor(String doctorId) {
        HisDoctor doctor = doctorMapper.selectById(doctorId);
        if (doctor == null) {
            throw new HisException("获取医生信息失败:" + doctorId);
        } else {
            HsUserXO.AddUserXO xo = HisDoctorCI.INSTANCE.toAddUserXO(doctor);
            Result<Long> result = yptClient.addDoctor(xo);
            if (result.getCode() == 0) {
                log.info("添加医生成功：" + result);
                return result.getData();
            } else {
                log.warn("添加医生失败：" + result);
                throw new HisException("添加医生失败: " + result);
            }
        }
    }

    private Long processPatient(String patientId) {
        HisPatient patient = patientMapper.selectById(patientId);
        if (patient == null) {
            throw new HisException("获取患者信息失败:" + patientId);
        } else {
            BuPatientXO.AddPatientXO xo = HisPatientCI.INSTANCE.toAddPatientXO(patient);
            xo.setIdcard(patientId);    // 没有身份证信息使用挂号ID作为身份证（每次挂号都会产生新的患者）
            Result<Long> result = yptClient.addPatient(xo);
            if (result.getCode() == 0) {
                log.info("添加患者成功：" + result);
                return result.getData();
            } else {
                log.warn("添加患者失败：" + result);
                throw new HisException("添加患者失败: " + result);
            }
        }
    }

    private Long processOutpatient(String outpatientId, Long yptDoctorId, Long yptPatientId) {
        LambdaQueryWrapper<HisOutpatient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HisOutpatient::getId, outpatientId)
                .last("limit 1");
        HisOutpatient outpatient = outpatientMapper.selectOne(queryWrapper);
        if (outpatient == null) {
            throw new HisException("获取预约信息失败:" + outpatientId);
        } else {
            BuOutpatientXO.AddOutpatientXO xo = HisOutpatientCI.INSTANCE.toAddOutpatientXO(outpatient);
            xo.setDoctorId(yptDoctorId.toString());
            xo.setPatientId(yptPatientId.toString());
            Result<Long> result = yptClient.addOutpatient(xo);
            if (result.getCode() == 0) {
                log.info("添加预约成功：" + result);
                return result.getData();
            } else {
                log.warn("添加预约失败：" + result + " - " + xo);
                throw new HisException("添加预约失败: " + result);
            }
        }
    }

    private Boolean processDepartment(String customerId, Long departmentId) {

        HisDepartment department = departmentMapper.selectById(departmentId);
        if (department == null) {
            throw new HisException("获取科室信息失败:" + department);
        } else {
            HsDepartmentXO.AddDepartmentXO xo = HisDepartmentCI.INSTANCE.toAddDepartmentXO(department);
            Result<Boolean> result = yptClient.addDepartmentHis(xo);
            if (result.getData().equals("true")) {
                log.info("添加预约成功：" + result);
                return result.getData();
            } else {
                log.warn("添加预约失败：" + result + " - " + xo);
                throw new HisException("添加预约失败: " + result);
            }
        }
    }

    private YptOutpatient getYptOutpatientById(Long outpatientId) {
        Result<Object> result = yptClient.getYptOutpatientById(outpatientId);
        String jsonStr = JSONObject.toJSONString(result.getData());
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        YptOutpatient yptOutpatient = JSON.toJavaObject(jsonObject, YptOutpatient.class);
        return yptOutpatient;
    }
}
