package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
public interface BuOutpatientAppointmentMapper extends BaseMapper<BuOutpatientAppointment> {
    Page<BuOutpatientAppointment> list(@Param("page") Page<BuOutpatientAppointment> page, @Param("outpatientAppointmentRo") OutpatientAppointmentRo outpatientAppointmentRo);
}
