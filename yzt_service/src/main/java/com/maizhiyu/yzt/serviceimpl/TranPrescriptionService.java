package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TranPrescription;
import com.maizhiyu.yzt.mapper.TranPrescriptionMapper;
import com.maizhiyu.yzt.service.ITranPrescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class TranPrescriptionService extends ServiceImpl<TranPrescriptionMapper, TranPrescription> implements ITranPrescriptionService {


}
