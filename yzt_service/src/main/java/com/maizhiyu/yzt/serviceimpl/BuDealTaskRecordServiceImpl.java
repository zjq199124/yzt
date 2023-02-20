package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuDealTaskRecord;
import com.maizhiyu.yzt.mapper.BuDealTaskRecordMapper;
import com.maizhiyu.yzt.service.IBuDealTaskRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BuDealTaskRecordServiceImpl extends ServiceImpl<BuDealTaskRecordMapper, BuDealTaskRecord> implements IBuDealTaskRecordService {

    @Resource
    private BuDealTaskRecordMapper buDealTaskRecordMapper;

    @Override
    public BuDealTaskRecord selectLastDealTime() {
        LambdaQueryWrapper<BuDealTaskRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(BuDealTaskRecord::getDealTime)
                .last("limit 1");
        return buDealTaskRecordMapper.selectOne(queryWrapper);
    }
}
