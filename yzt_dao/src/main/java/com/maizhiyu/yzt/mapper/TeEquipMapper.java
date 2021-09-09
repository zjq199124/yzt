package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TeEquip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface TeEquipMapper extends BaseMapper<TeEquip> {

    List<Map<String,Object>> selectEquipList(
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

    List<Map<String,Object>> selectEquipListWithMaintain(
            @Param("agencyId") Long agencyId,
            @Param("customerId") Long customerId,
            @Param("type") Integer type,
            @Param("modelId") Long modelId,
            @Param("status") Integer status,
            @Param("term") String term);
}
