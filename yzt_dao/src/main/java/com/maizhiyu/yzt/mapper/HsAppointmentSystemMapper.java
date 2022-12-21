package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HsAppointmentSystem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface HsAppointmentSystemMapper extends BaseMapper<HsAppointmentSystem> {

    /**
     * 查询某一客户在制定时间是否设置了预约时段
     * @param customerId
     * @param effective
     * @return
     */
    HsAppointmentSystem selectTobeEffective(@Param("customerId") Long customerId, @Param("effective") Date effective);

    HsAppointmentSystem selectNow(@Param("customerId") Long customerId, @Param("date") Date date);
}
