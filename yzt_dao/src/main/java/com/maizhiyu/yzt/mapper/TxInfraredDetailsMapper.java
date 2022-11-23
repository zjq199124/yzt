package com.maizhiyu.yzt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TxInfraredDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (TxInfraredDetails)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-21 20:26:25
 */
@Mapper
@Repository
public interface TxInfraredDetailsMapper extends BaseMapper<TxInfraredDetails> {


}

