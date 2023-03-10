package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.vo.BuDiagnoseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface BuRecommendMapper extends BaseMapper<Object> {

    List<Map<String, Object>> selectSyndromeByDisease(
            @Param("disease") String disease,
            @Param("syndrome") String syndrome);

    List<Map<String, Object>> selectSyndromeBySymptom(
            @Param("symptoms") String[] symptoms);

    List<Map<String, Object>> selectRecommendZhongyao(
            @Param("ids") Long[] ids);

    List<Map<String, Object>> selectRecommendChengyao(
            @Param("ids") Long[] ids);

    List<Map<String, Object>> selectRecommendXieding(
            @Param("ids") Long[] ids);

    List<Map<String, Object>> selectRecommendSytech(
            @Param("ids") Long[] ids);


    List<Map<String, Object>> getRecommendZhongyao(@Param("ids") List<Long> ids, @Param("diseaseId") Long diseaseId);

    List<Map<String, Object>> getRecommendChengyao(@Param("ids") List<Long> ids, @Param("diseaseId") Long diseaseId);

    List<Map<String, Object>> getRecommendXieding(@Param("ids") List<Long> ids, @Param("diseaseId") Long diseaseId);

    List<BuDiagnoseVO.ShiyiVO> getRecommendSytech(@Param("ids") List<Long> ids, @Param("diseaseId") Long diseaseId,
                                          @Param("sytechId") Long sytechId, @Param("customerId") Long customerId);
}
