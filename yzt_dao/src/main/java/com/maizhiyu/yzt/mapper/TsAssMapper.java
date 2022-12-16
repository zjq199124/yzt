package com.maizhiyu.yzt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TsAss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TsAssMapper extends BaseMapper<TsAss> {

    List<Map<String,Object>> selectAsslist(
            @Param("therapistId") Long customerId,
            @Param("sytechId") Long sytechId,
            @Param("creatTime") String startDate,
            @Param("endTime") String endDate,
            @Param("term") String term);

}
