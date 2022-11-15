package com.maizhiyu.yzt.feign;

import com.maizhiyu.yzt.bean.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.bean.aro.BuPrescriptionRO;
import com.maizhiyu.yzt.bean.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.bean.avo.DictSymptomVo;
import com.maizhiyu.yzt.bean.avo.DictSyndromeVo;
import com.maizhiyu.yzt.bean.axo.BuOutpatientXO;
import com.maizhiyu.yzt.bean.axo.BuPatientXO;
import com.maizhiyu.yzt.bean.axo.HsUserXO;
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
    feign.Response login(@RequestParam("username") String username, @RequestParam("password") String password);

    @PostMapping(value = "/diagnose/getRecommend")
    Result<BuDiagnoseVO.GetRecommendVO> getRecommend(@RequestBody BuDiagnoseRO.GetRecommendRO ro);

    @PostMapping(value = "/user/addUser")
    Result<Object> addDoctor(@RequestBody HsUserXO.AddUserXO xo);

    @PostMapping(value = "/patient/addPatientOrUpdate")
    Result<Object> addPatient(@RequestBody BuPatientXO.AddPatientXO xo);

    @PostMapping(value = "/outpatient/addOutpatientHis")
    Result<Object> addOutpatient(@RequestBody BuOutpatientXO.AddOutpatientXO xo);

    @PostMapping(value = "/prescription/addPrescriptionZhongyao")
    Result<Integer> addPrescriptionZhongyao(@RequestBody BuPrescriptionRO.AddPrescriptionZhongyao ro);

    @PostMapping(value = "/prescription/addPrescriptionChengyao")
    Result<Integer> addPrescriptionChengyao(@RequestBody BuPrescriptionRO.AddPrescriptionChengyao ro);

    @PostMapping(value = "/prescription/addPrescriptionXieding")
    Result<Integer> addPrescriptionXieding(@RequestBody BuPrescriptionRO.AddPrescriptionXieding ro);

    @PostMapping(value = "/prescription/addPrescriptionShiyi")
    Result<Integer> addPrescriptionShiyi(@RequestBody BuPrescriptionRO.AddPrescriptionShiyi ro);

    @GetMapping(value = "/dictSymptom/list")
    Result<List<DictSymptomVo>> selectDictSymptomList(Long diseaseId);

    @GetMapping(value = "/dictSyndrome/list")
    Result<List<DictSyndromeVo>> selectDictSyndromeListByDiseaseId(Long diseaseId);

    @PostMapping(value = "/dictSyndrome/selectBySymptom")
    Result<List<DictSyndromeVo>> selectDictSyndromeBySymptomIdList(@RequestBody List<Long> symptomIdList);

    @PostMapping(value = "/sytech/getRecommend")
    Result getSytechRecommend(@RequestParam Long diseaseId,@RequestParam Long syndromeId,@RequestParam String term);
}






























