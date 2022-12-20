package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.SchChengyao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface SchChengyaoMapper extends BaseMapper<SchChengyao> {

    IPage<Map<String,Object>> selectChengyaoList(
            @Param("page") Page page,
            @Param("diseaseId") Long diseaseId,
            @Param("status") Integer status,
            @Param("term") String term);

}
