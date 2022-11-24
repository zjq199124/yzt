package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuOutpatient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuOutpatientMapper extends BaseMapper<BuOutpatient> {

    List<Map<String, Object>> selectOutpatientList(
            @Param("createStartDate") String createStartDate,
            @Param("createEndDate") String createEndDate,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("customerId") Long customerId,
            @Param("departmentId") Long departmentId,
            @Param("doctorId") Long doctorId,
            @Param("patientId") Long patientId,
            @Param("type") Integer type,
            @Param("status") Integer status,
            @Param("term") String term);

    List<Map<String, Object>> selectPsUserOutpatientList(
            @Param("userId") Long userId,
            @Param("patientId") Long patientId,
            @Param("type") Integer type,
            @Param("status") Integer status);

    List<Map<String, Object>> selectOutpatientByPatientInfo(
            @Param("idCard") String idCard,
            @Param("phone") String phone,
            @Param("timeStart") String timeStart,
            @Param("timeEnd") String timeEnd);
}
