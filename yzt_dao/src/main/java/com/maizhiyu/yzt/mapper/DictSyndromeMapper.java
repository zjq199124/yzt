package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.DictSymptom;
import com.maizhiyu.yzt.entity.DictSyndrome;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface DictSyndromeMapper extends BaseMapper<DictSymptom> {

    List<DictSyndrome> selectByDiseaseId(@Param("diseaseId") Long diseaseId, @Param("search") String search);

    List<DictSyndrome> selectDictSyndromeBySymptomIdList(@Param("symptomIdList") List<Long> symptomIdList);
}
