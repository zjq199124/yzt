package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TsAssess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface TsAssessMapper extends BaseMapper<TsAssess> {

    List<Map<String, Object>> selectAssessList(
            @Param("customerId") Long customerId,
            @Param("sytechId") Long sytechId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("term") String term);
}
