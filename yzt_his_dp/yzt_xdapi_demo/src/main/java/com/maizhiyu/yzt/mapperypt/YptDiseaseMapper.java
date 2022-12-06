package com.maizhiyu.yzt.mapperypt;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.YptDisease;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface YptDiseaseMapper extends BaseMapper<YptDisease> {
}
