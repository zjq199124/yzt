package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuOutpatient;

import java.util.List;
import java.util.Map;

public interface IBuOutpatientService extends IService<BuOutpatient> {
    /**
     * 新增门诊挂号
     *
     * @param outpatient
     * @return
     */
    Integer addOutpatient(BuOutpatient outpatient);

    /**
     * 门诊信息设置
     *
     * @param outpatient
     * @return
     */
    Integer setOutpatient(BuOutpatient outpatient);

    /**
     * 以id获取门诊信息
     *
     * @param id
     * @return
     */
    BuOutpatient getOutpatient(Long id);

    /**
     * 以his门诊id获取云平台门诊信息
     *
     * @param customerId
     * @param hisId
     * @return
     */
    BuOutpatient getOutpatientByHisId(Long customerId, Long hisId);

    /**
     * 查询门诊信息列表
     *
     * @param createStartDate
     * @param createEndDate
     * @param startDate
     * @param endDate
     * @param customerId
     * @param departmentId
     * @param doctorId
     * @param patientId
     * @param type
     * @param status
     * @param term
     * @return
     */
    List<Map<String, Object>> getOutpatientList(
            String createStartDate, String createEndDate,
            String startDate, String endDate,
            Long customerId, Long departmentId, Long doctorId, Long patientId,
            Integer type, Integer status, String term);

    /**
     * 以患者id获取门诊列表
     *
     * @param userId
     * @param patientId
     * @param type
     * @param status
     * @return
     */
    List<Map<String, Object>> getPsUserOutpatientList(Long userId, Long patientId, Integer type, Integer status);
}
