package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.MsDepartment;
import com.maizhiyu.yzt.entity.MsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface MsDepartmentMapper extends BaseMapper<MsDepartment> {

    List<Map<String,Object>> selectDepartment(
            @Param("status") Integer status,
            @Param("term") String term);

}
