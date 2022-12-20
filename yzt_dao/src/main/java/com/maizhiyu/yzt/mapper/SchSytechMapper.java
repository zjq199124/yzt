package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.SchSytech;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface SchSytechMapper extends BaseMapper<SchSytech> {

    IPage<Map<String,Object>> selectSytechList(
            @Param("page") Page page,
            @Param("sytechId") Long sytechId,
            @Param("diseaseId") Long diseaseId,
            @Param("status") Integer status,
            @Param("term") String term);

    Map<String, Object> getSytechBySytechId(@Param("sytechId") Long sytechId, @Param("diseaseId") Long diseaseId, @Param("syndromeId") Long syndromeId);
}
