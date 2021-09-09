package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuPatient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuPatientMapper extends BaseMapper<BuPatient> {

    List<BuPatient> selectPatientListByPsuser(
            @Param("userId") Long userId);

    List<Map<String,Object>> selectPatientListByDoctor(
            @Param("doctorId") Long doctorId,
            @Param("term") String term);

    List<Map<String,Object>> selectPatientListByTherapist(
            @Param("therapistId") Long therapistId,
            @Param("type") Integer type,
            @Param("term") String term);

    List<Map<String,Object>> selectPatientPrescriptionList(
            @Param("patientId") Long patientId,
            @Param("type") Integer type);

}
