package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.SchSytech;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface SchSytechMapper extends BaseMapper<SchSytech> {

    List<Map<String,Object>> selectSytechList(
            @Param("sytechId") Long sytechId,
            @Param("diseaseId") Long diseaseId,
            @Param("status") Integer status,
            @Param("term") String term);

    List<Map<String, Object>> getSytechBySytechId(@Param("sytechId") Long sytechId, @Param("diseaseId") Long diseaseId, @Param("syndromeId") Long syndromeId);
}
