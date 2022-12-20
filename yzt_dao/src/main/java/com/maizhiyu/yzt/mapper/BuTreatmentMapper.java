package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuTreatment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuTreatmentMapper extends BaseMapper<BuTreatment> {

    IPage<Map<String, Object>> selectTreatmentList(
            @Param("page") Page page,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("customerId") Long customerId,
            @Param("departmentId") Long departmentId,
            @Param("therapistId") Long therapistId,
            @Param("patientId") Long patientId,
            @Param("prescriptionId") Long prescriptionId,
            @Param("projectId") Long projectId,
            @Param("type") Integer type,
            @Param("status") Integer status,
            @Param("term") String term);

    IPage<Map<String, Object>> selectTreatmentWaitingList(
            @Param("page") Page page,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("customerId") Long customerId,
            @Param("departmentId") Long departmentId,
            @Param("therapistId") Long therapistId);

    IPage<Map<String, Object>> selectPsUserTreatmentList(
            @Param("page") Page page,
            @Param("userId") Long userId,
            @Param("patientId") Long patientId,
            @Param("type") Integer type,
            @Param("status") Integer status);

    IPage<Map<String, Object>> selectTreatmentStatistics(
            @Param("page") Page page,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("customerId") Long customerId,
            @Param("departmentId") Long departmentId,
            @Param("therapistId") Long therapistId,
            @Param("projects") List<Long> projects);

}
