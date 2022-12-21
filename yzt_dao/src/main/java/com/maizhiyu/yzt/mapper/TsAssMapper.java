package com.maizhiyu.yzt.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TsAss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TsAssMapper extends BaseMapper<TsAss> {

    List<Map<String,Object>> selectAssItem(
            @Param("id") Long id
    );

    List<Map<String,Object>> selectAsslist(
            @Param("therapistId") Long therapistId,
            @Param("sytechId") Long sytechId,
            @Param("createTime") Date createTime,
            @Param("updateTime") Date updateTime,
            @Param("term") String term);



}
