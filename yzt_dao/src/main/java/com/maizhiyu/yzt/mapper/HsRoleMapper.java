package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface HsRoleMapper extends BaseMapper<HsRole> {

    List<Map<String, Object>> selectRoleList(
            @Param("customerId") Long customerId,
            @Param("status") Integer status,
            @Param("term") String term);
}
