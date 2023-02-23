package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuPrescription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuPrescriptionMapper extends BaseMapper<BuPrescription> {

    List<Map<String, Object>> selectPrescriptionList(
            @Param("prescriptionId") Long prescriptionId);

    List<Map<String, Object>> selectPrescriptionItemSummary(
            @Param("prescriptionId") Long prescriptionId);

    List<Map<String, Object>> selectPatientPrescriptionItemSummary(
            @Param("patientId") Long patientId);

    List<BuPrescription> selectByHisIdList(@Param("hisIdList") List<Long> hisIdList);
}
