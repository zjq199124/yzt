package com.maizhiyu.yzt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.TxInfraredImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (TxInfraredImage)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-22 08:37:14
 */
@Mapper
@Repository
public interface TxInfraredImageMapper extends BaseMapper<TxInfraredImage> {

}

