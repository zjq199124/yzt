package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TeFault;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface TeFaultMapper extends BaseMapper<TeFault> {

    List<Map<String, Object>> selectFaultList(
            @Param("customerId") Long customerId,
            @Param("status") Integer status,
            @Param("code") String code);
}
