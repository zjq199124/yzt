package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HsDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface HsDepartmentMapper extends BaseMapper<HsDepartment> {

    List<Map<String,Object>> selectDepartment(
            @Param("customerId") Long customerId,
            @Param("status") Integer status,
            @Param("term") String term);

}
