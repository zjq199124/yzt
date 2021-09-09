package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.MsAgency;
import com.maizhiyu.yzt.entity.MsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface MsAgencyMapper extends BaseMapper<MsAgency> {

    List<Map<String,Object>> selectAgencyList(
            @Param("status") Integer status,
            @Param("term") String term);

}
