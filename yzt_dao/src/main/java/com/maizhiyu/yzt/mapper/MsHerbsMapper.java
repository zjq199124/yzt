package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.MsHerbs;
import com.maizhiyu.yzt.entity.MsResource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * className:MsHerbsMapper
 * Package:com.maizhiyu.yzt.mapper
 * Description:
 *
 * @DATE:2021/12/16 9:26 上午
 * @Author:2101825180@qq.com
 */

@Mapper
@Repository
public interface MsHerbsMapper extends BaseMapper<MsHerbs> {
}
