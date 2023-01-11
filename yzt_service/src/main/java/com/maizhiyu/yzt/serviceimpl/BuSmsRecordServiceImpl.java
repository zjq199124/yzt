package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuSmsRecord;
import com.maizhiyu.yzt.mapper.BuSmsRecordMapper;
import com.maizhiyu.yzt.service.IBuSmsRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BuSmsRecordServiceImpl extends ServiceImpl<BuSmsRecordMapper, BuSmsRecord>implements IBuSmsRecordService {
}
