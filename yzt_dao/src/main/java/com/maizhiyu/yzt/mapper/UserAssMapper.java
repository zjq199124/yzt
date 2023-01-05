package com.maizhiyu.yzt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.UserAss;
import com.maizhiyu.yzt.vo.TssAssVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserAssMapper extends BaseMapper<UserAss> {

    List<TssAssVO.OperationDetail> selectUserGrade(
            @Param("assId") Long assId);


}
