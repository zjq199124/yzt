package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.ro.ItemTaskRo;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.ro.WaitTreatmentRo;
import com.maizhiyu.yzt.vo.BuPrescriptionItemTaskVo;
import com.maizhiyu.yzt.vo.WaitSignatureVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * (BuPrescriptionItemTask)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-01 15:03:03
 */
@Mapper
@Repository
public interface BuPrescriptionItemTaskMapper extends BaseMapper<BuPrescriptionItemTask> {

    Page<BuPrescriptionItemTaskVo> selectWaitSignatureList(@Param("page") Page<BuPrescriptionItemTaskVo> page, @Param("waitSignatureRo") WaitSignatureRo waitSignatureRo);

    Page<BuPrescriptionItemTaskVo> selectWaitTreatmentList(@Param("page") Page<BuPrescriptionItemTaskVo> page, @Param("waitTreatmentRo") WaitTreatmentRo waitTreatmentRo);

    Page<BuPrescriptionItemTaskVo> selectTreatmentList(@Param("page") Page<BuPrescriptionItemTaskVo> page, @Param("itemTaskRo") ItemTaskRo itemTaskRo);

    BuPrescriptionItemTaskVo treatmentRecordDetail(@Param("id") Long id);
}

