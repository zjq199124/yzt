package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuTreatment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuTreatmentMapper extends BaseMapper<BuTreatment> {

    List<Map<String, Object>> selectTreatmentList(
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

    List<Map<String, Object>> selectPsUserTreatmentList(
            @Param("userId") Long userId,
            @Param("patientId") Long patientId,
            @Param("type") Integer type,
            @Param("status") Integer status);

    List<Map<String, Object>> selectTreatmentStatistics(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("customerId") Long customerId,
            @Param("departmentId") Long departmentId,
            @Param("therapistId") Long therapistId,
            @Param("projectId") Long projectId);

}
