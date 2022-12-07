package com.maizhiyu.yzt.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.maizhiyu.yzt.bean.aci.HisDoctorCI;
import com.maizhiyu.yzt.bean.aci.HisOutpatientCI;
import com.maizhiyu.yzt.bean.aci.HisPatientCI;
import com.maizhiyu.yzt.bean.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.bean.aro.TreatmentItemsRo;
import com.maizhiyu.yzt.bean.aro.TreatmentRo;
import com.maizhiyu.yzt.bean.axo.BuOutpatientXO;
import com.maizhiyu.yzt.bean.axo.BuPatientXO;
import com.maizhiyu.yzt.bean.axo.HsUserXO;
import com.maizhiyu.yzt.entity.HisDoctor;
import com.maizhiyu.yzt.entity.HisOutpatient;
import com.maizhiyu.yzt.entity.HisPatient;
import com.maizhiyu.yzt.entity.TreatmentMapping;
import com.maizhiyu.yzt.exception.HisException;
import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.his.HisApi;
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

    @Autowired
    private YptTreatmentService treatmentService;

    @Resource
    private TreatmentMappingService treatmentMappingService;

    @Value("${customer.name}")
    private String customerName;

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
    public Result addPrescriptionShiyi(@RequestBody @Valid BuPrescriptionRO.AddPrescriptionShiyi ro) throws IOException {
        Assert.notNull(ro, "处方数据不能为空!");
        Assert.notNull(ro.getBaseInfo(), "基础信息不能为空!");
        BuPrescriptionRO.AddPrescriptionShiyi.BaseInfo baseInfo = ro.getBaseInfo();

        //判断医生，患者，患者门诊信息
        processDoctor(baseInfo.getDoctorId().toString());
        processPatient(baseInfo.getPatientId().toString());
        processOutpatient(baseInfo.getOutpatientId().toString());

        if (Objects.nonNull(ro) && !CollectionUtils.isEmpty(ro.getItemList())) {
            savePrescriptionShiyiToHis(ro);
        }

        if (Objects.nonNull(ro.getBaseInfo())) {
            ro.getDiagnoseInfo().setCustomerName(customerName);
            yptClient.addDiagnose(ro);
        }

        if(CollectionUtils.isEmpty(ro.getItemList()))
            return Result.success();

        Result<Integer> result = yptClient.addPrescriptionShiyi(ro);
        return Result.success(result.getData());
    }

    private void savePrescriptionShiyiToHis(BuPrescriptionRO.AddPrescriptionShiyi ro) throws IOException {
        //克隆出一个对象用来进行翻译操作
        BuPrescriptionRO.AddPrescriptionShiyi clone = ObjectUtil.cloneIfPossible(ro);
        for (BuPrescriptionRO.BuPrescriptionItemShiyi vo : clone.getItemList()) {
            try {
                // 按code映射
                if (StringUtils.isNotBlank(vo.getCode())) {
                    TreatmentMapping treatmentMapping = treatmentMappingService.getTreatmentByCode(vo.getCode());
                    if (Objects.nonNull(treatmentMapping)) {
                        vo.setEntityId(Long.parseLong(treatmentMapping.getHiscode()));
                        continue;
                    }
                }else if (StringUtils.isNotBlank(vo.getName())) {
                    // 按名称映射
                    TreatmentMapping treatmentMapping = treatmentMappingService.getTreatmentByName(vo.getName());
                    if (Objects.nonNull(treatmentMapping)) {
                        vo.setEntityId(Long.parseLong(treatmentMapping.getHiscode()));
                        continue;
                    }
                }
            } catch (Exception e) {
                log.warn("适宜处方ID映射异常：" + e.getMessage());
            }
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.95:8088/hoso/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        HisApi hisApi = retrofit.create(HisApi.class);
        Gson gson=new Gson();

        //his中处置id不为空的话那么先删除his中的处置
        if (Objects.nonNull(clone.getHisId())) {
            try {
                hisApi.cancelTreatmentById(Integer.parseInt(clone.getHisId()));
            } catch (Exception e) {
                log.warn("处治保存到his失败!");
                e.printStackTrace();
            }
        }

        TreatmentRo treatmentRo = new TreatmentRo();
        treatmentRo.setDoctorId(clone.getBaseInfo().getDoctorId().intValue());
        treatmentRo.setMedicalRecordId(clone.getBaseInfo().getOutpatientId().intValue());

        List<TreatmentItemsRo> treatmentItemsRoList = clone.getItemList().stream().map(item -> {
            TreatmentItemsRo treatmentItemsRo = new TreatmentItemsRo();
            treatmentItemsRo.setFmedicalItemsId(item.getEntityId().intValue());
            treatmentItemsRo.setQuantity(item.getQuantity());
            return treatmentItemsRo;
        }).collect(Collectors.toList());

        treatmentRo.setTreatmentItemsList(treatmentItemsRoList);

        String treatmentRoJstr = gson.toJson(treatmentRo);

        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("application/json; charset=utf-8"),treatmentRoJstr);
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

    private Long processOutpatient(String outpatientId) {
        HisOutpatient outpatient = outpatientMapper.selectById(outpatientId);
        if (outpatient == null) {
            throw new HisException("获取预约信息失败:" + outpatientId);
        } else {
            BuOutpatientXO.AddOutpatientXO xo = HisOutpatientCI.INSTANCE.toAddOutpatientXO(outpatient);
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
}
