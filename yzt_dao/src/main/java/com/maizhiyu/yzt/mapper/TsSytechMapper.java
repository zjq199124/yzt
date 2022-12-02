package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TsSytech;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface TsSytechMapper extends BaseMapper<TsSytech> {
    List<TsSytech> selectSytechList(@Param("diseaseId") Long diseaseId, @Param("syndromeId") Long syndromeId, @Param("search") String search);

}
