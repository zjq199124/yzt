package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuCure;
import com.maizhiyu.yzt.ro.BuCureSearchRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 治疗表(BuCure)表数据库访问层
 *
 * @author liuyzh
 * @since 2022-12-19 18:34:06
 */
@Mapper
@Repository
public interface BuCureMapper extends BaseMapper<BuCure>{

    Page<BuCure> selectTreatmentList(@Param("page") Page<BuCure> page, @Param("buCureSearchRO") BuCureSearchRO buCureSearchRO);
}
