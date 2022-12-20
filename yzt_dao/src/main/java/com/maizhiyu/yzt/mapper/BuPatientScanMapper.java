package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuPatientScan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface BuPatientScanMapper extends BaseMapper<BuPatientScan> {

    IPage<Map<String,Object>> selectPatientScanList(@Param("page") Page page,
            @Param("customerId") Long customerId,
            @Param("term") String term);

}
