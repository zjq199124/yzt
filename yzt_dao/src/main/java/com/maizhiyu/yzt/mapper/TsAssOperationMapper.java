package com.maizhiyu.yzt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TsAssOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TsAssOperationMapper extends BaseMapper<TsAssOperation> {
    List<TsAssOperation> selectAssDetail(
            @Param("sytechId")  Long sytechId);

}
