package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TeMaintain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface TeMaintainMapper extends BaseMapper<TeMaintain> {

    public IPage<Map<String, Object>> selectMaintainList(
            @Param("page") Page page,
            @Param("customerId") Long customerId,
            @Param("equipId") Long equipId,
            @Param("type") Integer type,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);
}
