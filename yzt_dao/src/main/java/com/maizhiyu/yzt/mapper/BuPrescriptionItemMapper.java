package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuPrescriptionItemMapper extends BaseMapper<BuPrescriptionItem> {

    List<Map<String,Object>> selectPatientPrescriptionItemList(
            @Param("patientId") Long patientId,
            @Param("type") Integer type);
}
