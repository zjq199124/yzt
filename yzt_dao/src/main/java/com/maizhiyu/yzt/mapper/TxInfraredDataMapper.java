package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TxInfraredData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (TxInfraredData)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-21 19:24:53
 */
@Mapper
@Repository
public interface TxInfraredDataMapper extends BaseMapper<TxInfraredData> {


}

