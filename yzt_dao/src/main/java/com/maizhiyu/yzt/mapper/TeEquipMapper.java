package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TeEquip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface TeEquipMapper extends BaseMapper<TeEquip> {

    IPage<Map<String,Object>> selectEquipList(
            @Param("page") Page page,
            @Param("agencyId") Long agencyId,
            @Param("customerId") Long customerId,
            @Param("type") Integer type,
            @Param("modelId") Long modelId,
            @Param("status") Integer status,
            @Param("term") String term);

    List<Map<String,Object>> selectEquipListWithRunData(
            @Param("agencyId") Long agencyId,
            @Param("customerId") Long customerId,
            @Param("type") Integer type,
            @Param("modelId") Long modelId,
            @Param("status") Integer status,
            @Param("term") String term);

    IPage<Map<String,Object>> selectEquipListWithMaintain(
            @Param("page") Page page,
            @Param("agencyId") Long agencyId,
            @Param("customerId") Long customerId,
            @Param("type") Integer type,
            @Param("modelId") Long modelId,
            @Param("status") Integer status,
            @Param("term") String term);
}
