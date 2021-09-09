package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HsResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface HsResourceMapper extends BaseMapper<HsResource> {

    List<HsResource> selectRoleResourceList(
            @Param("roleId") Long roleId);

    List<HsResource> selectUserResourceList(
            @Param("userId") Long userId);
}
