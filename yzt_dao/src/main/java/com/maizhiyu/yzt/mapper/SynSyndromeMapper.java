package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.SynSyndrome;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface SynSyndromeMapper extends BaseMapper<SynSyndrome> {

    List<Map<String,Object>> selectSyndromeList(
            @Param("diseaseId") Long diseaseId,
            @Param("diseaseName") String diseaseName,
            @Param("status") Integer status,
            @Param("term") String term);

}
