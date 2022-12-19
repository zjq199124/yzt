package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.ro.BuDiagnoseRO;

import java.util.List;
import java.util.Map;

public interface IBuDiagnoseService extends IService<BuDiagnose> {

    /**
     * 添加诊断
     *
     * @param diagnose
     * @return
     */
    Integer addDiagnose(BuDiagnose diagnose);

    /**
     * 设置诊断数据
     *
     * @param diagnose
     * @return
     */
    Integer setDiagnose(BuDiagnose diagnose);

    /**
     * 以id获取诊断数据
     *
     * @param id
     * @return
     */
    BuDiagnose getDiagnose(Long id);

    /**
     * 以门诊id获取诊断数据
     *
     * @param outpatientId
     * @return
     */
    BuDiagnose getDiagnoseOfOutpatient(Long outpatientId);

    /**
     * 以医院id，时间范围查找诊断列表
     *
     * @param customerId
     * @param start
     * @param end
     * @return
     */
    List<Map<String, Object>> getDiagnoseList(Long customerId, String start, String end);

//    Integer saveOrUpdate(BuDiagnose buDiagnose);

    /**
     * 获取诊断详情
     *
     * @param ro
     * @return
     * @throws Exception
     */
    Map<String, Object> getDetails(BuDiagnoseRO.GetRecommendRO ro) throws Exception;
}
