package com.maizhiyu.yzt.feign;

import com.maizhiyu.yzt.aro.BuDiagnoseRO;
import com.maizhiyu.yzt.avo.BuDiagnoseVO;
import com.maizhiyu.yzt.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "ypt", url = "${api.ypt.domain}")
public interface FeignYptClient {

    @PostMapping(value = "/diagnose/getRecommend")
    Result<BuDiagnoseVO.GetRecommendVO> getRecommend(@RequestBody BuDiagnoseRO.GetRecommendRO ro);
}
