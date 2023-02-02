package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemTaskMapper;
import com.maizhiyu.yzt.entity.BuPrescriptionItemTask;
import com.maizhiyu.yzt.service.BuPrescriptionItemTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * (BuPrescriptionItemTask)表服务实现类
 *
 * @author makejava
 * @since 2023-02-01 15:16:12
 */
@Service("buPrescriptionItemTaskService")
@Transactional(rollbackFor = Exception.class)
public class BuPrescriptionItemTaskServiceImpl extends ServiceImpl<BuPrescriptionItemTaskMapper, BuPrescriptionItemTask> implements BuPrescriptionItemTaskService {

}

