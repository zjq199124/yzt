package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.SysMultimedia;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (SysMultimedia)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-21 15:07:00
 */
@Mapper
@Repository
public interface SysMultimediaMapper extends BaseMapper<SysMultimedia> {

}

