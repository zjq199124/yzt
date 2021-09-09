package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.MsResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface MsResourceMapper extends BaseMapper<MsResource> {

    List<MsResource> selectRoleResourceList(
            @Param("roleId") Long roleId);

    List<MsResource> selectUserResourceList(
            @Param("userId") Long userId);
}
