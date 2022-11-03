package com.maizhiyu.yzt.mapperhis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HisPatient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface HisPatientMapper extends BaseMapper<HisPatient> {
}
