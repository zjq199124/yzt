package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.MsAgency;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface MsAgencyMapper extends BaseMapper<MsAgency> {

    IPage<Map<String,Object>> selectAgencyList(
            @Param("page") Page page,
            @Param("status") Integer status,
            @Param("term") String term);

}
