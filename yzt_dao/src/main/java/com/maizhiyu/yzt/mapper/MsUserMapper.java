package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.MsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface MsUserMapper extends BaseMapper<MsUser> {

    List<Map<String,Object>> selectUserList(
            @Param("departmentId") Long departmentId,
            @Param("roleId") Long roleId,
            @Param("status") Integer status,
            @Param("term") String term);

}
