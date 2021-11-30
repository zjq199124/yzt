package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface TeModelMapper extends BaseMapper<TeModel> {

    List<Map<String,Object>> selectModelList(
            @Param("status") Integer status,
            @Param("type") Integer type,
            @Param("term") String term);
}
