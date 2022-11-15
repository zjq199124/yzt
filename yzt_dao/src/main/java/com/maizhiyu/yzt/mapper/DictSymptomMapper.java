package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.DictSymptom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface DictSymptomMapper extends BaseMapper<DictSymptom> {

    List<Map<String,Object>> selectSymptomList(
            @Param("status") Integer status,
            @Param("term") String term);

    List<DictSymptom> selectByDiseaseId(@Param("diseaseId") Long diseaseId);
}
