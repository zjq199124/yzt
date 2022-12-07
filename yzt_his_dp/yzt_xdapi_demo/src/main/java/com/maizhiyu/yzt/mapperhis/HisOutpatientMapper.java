package com.maizhiyu.yzt.mapperhis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HisOutpatient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface HisOutpatientMapper extends BaseMapper<HisOutpatient> {
}
