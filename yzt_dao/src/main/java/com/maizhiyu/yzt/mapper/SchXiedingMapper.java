package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.SchXieding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface SchXiedingMapper extends BaseMapper<SchXieding> {

    List<Map<String,Object>> selectXiedingList(
            @Param("status") Integer status,
            @Param("diseaseId") Long diseaseId,
            @Param("term") String term);

}
