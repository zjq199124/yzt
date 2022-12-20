package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TsAssess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface TsAssessMapper extends BaseMapper<TsAssess> {

    IPage<Map<String, Object>> selectAssessList(
            @Param("page") Page page,
            @Param("customerId") Long customerId,
            @Param("sytechId") Long sytechId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("term") String term);
}
