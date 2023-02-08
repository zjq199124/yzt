package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuDiagnose;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuDiagnoseMapper extends BaseMapper<BuDiagnose> {
    List<BuDiagnose> selectDiagnoseList(Long customerId, String term);
//
//    List<Map<String, Object>> selectDiagnoseItemSummary(
//            @Param("prescriptionId") Long prescriptionId);
//
//    List<Map<String, Object>> selectPatientDiagnoseItemSummary(
//            @Param("patientId") Long patientId);
}
