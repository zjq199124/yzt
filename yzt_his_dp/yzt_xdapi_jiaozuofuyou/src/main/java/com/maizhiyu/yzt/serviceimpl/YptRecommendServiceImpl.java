package com.maizhiyu.yzt.serviceimpl;

import com.maizhiyu.yzt.feign.FeignYptClient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IYptRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class YptRecommendServiceImpl implements IYptRecommendService {

    @Resource
    private FeignYptClient feignYptClient;

    @Override
    public Result getRecommend(Long diseaseId,Long syndromeId,String term) {
        // 调用开放接口获取诊断推荐
        Result result = feignYptClient.getSytechRecommend(diseaseId,syndromeId,term);
        return result;
    }
}
