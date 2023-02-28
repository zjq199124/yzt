package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface BuOutpatientAppointmentMapper extends BaseMapper<BuOutpatientAppointment> {
    Page<BuOutpatientAppointment> list(@Param("page") Page<BuOutpatientAppointment> page,
                                       @Param("outpatientAppointmentRo") OutpatientAppointmentRo outpatientAppointmentRo);
}
