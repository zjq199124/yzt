package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuPrescription;

import java.util.List;
import java.util.Map;

public interface IBuPrescriptionService extends IService<BuPrescription> {

    /**
     * 新增处方
     *
     * @param prescription
     * @return
     */
    boolean addPrescription(BuPrescription prescription);

    /**
     * 以处方id删除处方 与处方项
     *
     * @param id 处方id
     * @return
     */
    boolean delPrescription(Long id);

    /**
     * 修改设置处方
     *
     * @param prescription
     * @return
     */
    Boolean setPrescription(BuPrescription prescription);

    /**
     * 按照item 差集修改处方
     *
     * @param prescription 当前新增处方数据
     * @param preItemIdList  编辑前的处方条目表id
     * @return
     */
    Boolean setPrescriptionByDiff(BuPrescription prescription, List<Long> preItemIdList);

    /**
     * 修改处方状态
     *
     * @param prescription
     * @return
     */
    Boolean setPrescriptionStatus(BuPrescription prescription);

    /**
     * 以处方id获取处方信息
     *
     * @param id
     * @return
     */
    BuPrescription getPrescription(Long id);

    /**
     * 以门诊id获取处方列表
     *
     * @param outpatientId 门诊id
     * @return
     */
    List<Map<String, Object>> getPrescriptionList(Long outpatientId);

    /**
     * 以时间段查询医院下的处方列表
     *
     * @param customerId 医院id
     * @param start      开始时间
     * @param end        结束时间
     * @return
     */
    List<Map<String, Object>> getPrescriptionList(Long customerId, String start, String end);

    /**
     * @param prescriptionId
     * @return
     */
    List<Map<String, Object>> getPrescriptionItemSummary(Long prescriptionId);

    /**
     * @param prescriptionId
     * @return
     */
    List<Map<String, Object>> getPatientPrescriptionItemSummary(Long prescriptionId);

    /**
     * 结算处方
     *
     * @param id
     * @param userId
     */
    void setPaymentStatus(Long id, Long userId);

    /**
     * 根据id查询出处治列表
     * @param prescriptionIdList
     * @return
     */
    List<BuPrescription> selectByIdList(List<Long> prescriptionIdList);

    /**
     * 根据hisId查询处治列表
     * @param prescriptionIdList
     * @return
     */
    List<BuPrescription> selectByHisIdList(List<Long> prescriptionIdList);
}
