package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.vo.WaitSignatureVo;
import org.apache.ibatis.annotations.Mapper;
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

    Page<WaitSignatureVo> selectWaitSignatureList(Page<WaitSignatureVo> page, WaitSignatureRo waitSignatureRo);
}

