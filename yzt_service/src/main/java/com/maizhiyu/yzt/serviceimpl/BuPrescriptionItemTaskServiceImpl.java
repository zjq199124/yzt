package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemTaskMapper;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.service.BuPrescriptionItemTaskService;
import com.maizhiyu.yzt.vo.WaitSignatureVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * (BuPrescriptionItemTask)表服务实现类
 *
 * @author makejava
 * @since 2023-02-01 15:16:12
 */
@Service("buPrescriptionItemTaskService")
@Transactional(rollbackFor = Exception.class)
public class BuPrescriptionItemTaskServiceImpl extends ServiceImpl<BuPrescriptionItemTaskMapper, BuPrescriptionItemTask> implements BuPrescriptionItemTaskService {

    @Resource
    private BuPrescriptionItemTaskMapper buPrescriptionItemTaskMapper;

    @Override
    public Page<WaitSignatureVo> selectWaitSignatureList(WaitSignatureRo waitSignatureRo) {
        Page<WaitSignatureVo> page = new Page<>(waitSignatureRo.getCurrentPage(), waitSignatureRo.getPageSize());
        Page<WaitSignatureVo> pageResult = buPrescriptionItemTaskMapper.selectWaitSignatureList(page,waitSignatureRo);
        return pageResult;
    }

    @Override
    public Boolean signature(Long prescriptionItemTaskId) {
        LambdaUpdateWrapper<BuPrescriptionItemTask> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BuPrescriptionItemTask::getId, prescriptionItemTaskId)
                .set(BuPrescriptionItemTask::getAppointmentStatus, 1)
                .set(BuPrescriptionItemTask::getAppointmentDate, new Date())
                .set(BuPrescriptionItemTask::getUpdateTime, new Date());
        int result = buPrescriptionItemTaskMapper.update(null, updateWrapper);
        return result > 0;
    }
}

