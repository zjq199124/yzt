package com.maizhiyu.yzt.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuRecommendMapper {

    List<Map<String,Object>> selectSyndromeByDisease(
            @Param("disease") String disease,
            @Param("syndrome") String syndrome);

    List<Map<String,Object>> selectSyndromeBySymptom(
            @Param("symptoms") String[] symptoms);

    List<Map<String,Object>> selectRecommendZhongyao(
            @Param("ids") Long[] ids);

    List<Map<String,Object>> selectRecommendChengyao(
            @Param("ids") Long[] ids);

    List<Map<String,Object>> selectRecommendXieding(
            @Param("ids") Long[] ids);

    List<Map<String,Object>> selectRecommendSytech(
            @Param("ids") Long[] ids);
}
