package com.maizhiyu.yzt.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface HsUserDetailsMapper extends BaseMapper<HsUserDetails> {

}
