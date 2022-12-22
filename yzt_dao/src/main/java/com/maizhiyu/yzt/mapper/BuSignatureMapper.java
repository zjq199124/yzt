package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuSignature;
import com.maizhiyu.yzt.ro.WaitTreatmentRo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 治疗签到表(BuSignature)表数据库访问层
 *
 * @author liuyzh
 * @since 2022-12-19 19:03:08
 */
@Mapper
@Repository
public interface BuSignatureMapper extends BaseMapper<BuSignature>{

    Page<BuSignature> selectWaitTreatmentList(@Param("page") Page<BuSignature> page, @Param("waitTreatmentRo") WaitTreatmentRo waitTreatmentRo);
}
