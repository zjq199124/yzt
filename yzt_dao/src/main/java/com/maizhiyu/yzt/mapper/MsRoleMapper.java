package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.MsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface MsRoleMapper extends BaseMapper<MsRole> {

    List<Map<String, Object>> selectRoleList(
            @Param("status") Integer status,
            @Param("term") String term);

}
