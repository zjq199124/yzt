package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TeWarn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface TeWarnMapper extends BaseMapper<TeWarn> {

    List<Map<String,Object>> selectWarnList(
            @Param("date") String datetime,
            @Param("agencyId") Long agencyId,
            @Param("customerId") Long customerId,
            @Param("type") Integer type,
            @Param("modelId") Long modelId,
            @Param("equipId") Long equipId);

    List<Map<String,Object>> selectWarnListOfRun(
            @Param("code") String code,
            @Param("runid") String runid);

}
