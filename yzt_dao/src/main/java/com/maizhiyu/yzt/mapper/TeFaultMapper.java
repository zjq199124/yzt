package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TeFault;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface TeFaultMapper extends BaseMapper<TeFault> {

    IPage<Map<String, Object>> selectFaultList(
            @Param("page") Page page,
            @Param("customerId") Long customerId,
            @Param("status") Integer status,
            @Param("code") String code);
}
