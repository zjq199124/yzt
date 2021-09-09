package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuPatientScan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuPatientScanMapper extends BaseMapper<BuPatientScan> {

    List<Map<String,Object>> selectPatientScanList(
            @Param("customerId") Long customerId,
            @Param("term") String term);

}
