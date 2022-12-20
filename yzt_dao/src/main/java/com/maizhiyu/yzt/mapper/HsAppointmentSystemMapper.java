package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HsAppointmentSystem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface HsAppointmentSystemMapper extends BaseMapper<HsAppointmentSystem> {

    HsAppointmentSystem selectTobeEffective(@Param("customerId") Long customerId, @Param("date") Date date);

    HsAppointmentSystem selectNow(@Param("customerId") Long customerId, @Param("date") Date date);
}
