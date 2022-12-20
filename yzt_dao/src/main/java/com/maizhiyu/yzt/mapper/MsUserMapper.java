package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.MsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface MsUserMapper extends BaseMapper<MsUser> {

    IPage<Map<String,Object>> selectUserList(
            @Param("page") Page page,
            @Param("departmentId") Long departmentId,
            @Param("roleId") Long roleId,
            @Param("status") Integer status,
            @Param("term") String term);

}
