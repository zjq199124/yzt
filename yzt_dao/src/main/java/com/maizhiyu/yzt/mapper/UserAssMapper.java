package com.maizhiyu.yzt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.UserAss;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserAssMapper extends BaseMapper<UserAss> {

}
