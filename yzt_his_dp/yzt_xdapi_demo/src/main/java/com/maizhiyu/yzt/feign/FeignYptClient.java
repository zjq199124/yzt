package com.maizhiyu.yzt.feign;

import com.maizhiyu.yzt.bean.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.bean.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.bean.avo.DictSymptomVo;
import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
import com.maizhiyu.yzt.bean.avo.RelSyndromeSymptomVo;
import com.maizhiyu.yzt.bean.axo.BuOutpatientXO;
import com.maizhiyu.yzt.bean.axo.BuPatientXO;
import com.maizhiyu.yzt.bean.axo.HsDepartmentXO;
import com.maizhiyu.yzt.bean.axo.HsUserXO;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "yptapi", url = "${api.ypt.domain}")
public interface FeignYptClient {

    @PostMapping(value = "/login")
    feign.Response login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password);

    @PostMapping(value = "/diagnose/getRecommend")
    Result getRecommend(@RequestBody BuDiagnoseRO.GetRecommendRO ro);

    @PostMapping(value = "/user/addUser")
    Result<Long> addDoctor(@RequestBody HsUserXO.AddUserXO xo);

    @PostMapping(value = "/patient/addPatientOrUpdate")
    Result<Long> addPatient(@RequestBody BuPatientXO.AddPatientXO xo);

    @PostMapping(value = "/outpatient/addOutpatientHis")
    Result<Long> addOutpatient(@RequestBody BuOutpatientXO.AddOutpatientXO xo);

    @PostMapping(value = "/prescription/addPrescriptionZhongyao")
    Result<Integer> addPrescriptionZhongyao(@RequestBody BuPrescriptionRO.AddPrescriptionZhongyao ro);

    @PostMapping(value = "/prescription/addPrescriptionChengyao")
    Result<Integer> addPrescriptionChengyao(@RequestBody BuPrescriptionRO.AddPrescriptionChengyao ro);

    @PostMapping(value = "/prescription/addPrescriptionXieding")
    Result<Integer> addPrescriptionXieding(@RequestBody BuPrescriptionRO.AddPrescriptionXieding ro);

    @PostMapping(value = "/prescription/addPrescriptionShiyi")
    Result<Boolean> addPrescriptionShiyi(@RequestBody BuPrescriptionRO.AddPrescriptionShiyi ro);

    @GetMapping(value = "/dictSymptom/list")
    Result<List<DictSymptomVo>> selectDictSymptomList(@RequestParam(value = "diseaseId") Long diseaseId);

    @GetMapping(value = "/dictSyndrome/list")
    Result<List<DictSyndromeVo>> selectDictSyndromeListByDiseaseId(@RequestParam(value = "diseaseId") Long diseaseId,
                                                                   @RequestParam(value = "search") String search);

    @PostMapping(value = "/dictSyndrome/selectBySymptom")
    Result<List<DictSyndromeVo>> selectDictSyndromeBySymptomIdList(@RequestParam(value = "diseaseId") Long diseaseId, @RequestBody List<Long> symptomIdList);

    @PostMapping(value = "/sytech/getRecommend")
    Result getSytechRecommend(@RequestParam(value = "diseaseId") Long diseaseId, @RequestParam(value = "syndromeId", required = false) Long syndromeId, @RequestParam(value = "term", required = false) String term);

    @PostMapping(value = "/relSyndromeSymptom/selectBySyndromeIds")
    Result<List<RelSyndromeSymptomVo>> selectDictSymptomBySyndromeIdList(@RequestBody List<Long> syndromeIds);

    @PostMapping(value = "/diagnose/addDiagnoseInfo")
    Result<BuDiagnose> addDiagnose(@RequestBody BuPrescriptionRO.AddPrescriptionShiyi ro);

    @PostMapping(value = "/diagnose/getDetail")
    Result getDetail(@RequestBody BuDiagnoseRO.GetRecommendRO ro);

    @GetMapping(value = "/diagnose/getYptOutpatient")
    Result<Long> getYptOutpatientByHisId(@RequestParam(value = "outpatientId") Long outpatientId);

    @GetMapping("/schtech/getSytechList")
    Result getSytechList(@RequestParam(value = "diseaseId") Long diseaseId,
                         @RequestParam(value = "syndromeId") Long syndromeId,
                         @RequestParam(value = "search") String search);

    @GetMapping("/schtech/getSytechBySytechId")
    Result getSytechBySytechId(@RequestParam(value = "diseaseId") Long diseaseId,
                               @RequestParam(value = "syndromeId") Long syndromeId,
                               @RequestParam(value = "sytechId") Long sytechId);

    @PostMapping(value = "/outpatient/getYptOutpatientById")
    Result<Object> getYptOutpatientById(@RequestParam(value = "outpatientId") Long outpatientId);

    /**
     * 新增科室
     *
     * @param xo
     * @return
     */
    @PostMapping(value = "/department/addDepartmentHis")
    Result<Boolean> addDepartmentHis(@RequestBody HsDepartmentXO.AddDepartmentXO xo);
}






























