package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.HsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface HsUserMapper extends BaseMapper<HsUser> {

    HsUser selectAdmin(
            @Param("customerId") Long customerId);

    Map<String, Object> selectUser(
            @Param("id") Long id);

    IPage<Map<String,Object>> selectUserList(
            @Param("page") Page page,
            @Param("customerId") Long customerId,
            @Param("departmentId") Long departmentId,
            @Param("roleId") Long roleId,
            @Param("status") Integer status,
            @Param("term") String term,
            @Param("isdoctor") Integer isdoctor,
            @Param("istherapist") Integer istherapist);



}
