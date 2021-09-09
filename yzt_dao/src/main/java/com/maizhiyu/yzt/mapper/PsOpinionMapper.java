package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.PsOpinion;
import com.maizhiyu.yzt.entity.TeModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface PsOpinionMapper extends BaseMapper<PsOpinion> {
}
