package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuScheduleMapper extends BaseMapper<BuSchedule> {

    List<Map<String, Object>> selectScheduleList(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("doctorId") Long doctorId);

    List<Map<String, Object>> selectScheduleAppointmentList(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("customerId") Long customerId,
            @Param("departmentId") Long departmentId,
            @Param("doctorId") Long doctorId);
}
