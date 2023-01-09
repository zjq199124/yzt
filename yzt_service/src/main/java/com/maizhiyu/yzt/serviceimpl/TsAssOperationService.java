package com.maizhiyu.yzt.serviceimpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsAssOperation;
import com.maizhiyu.yzt.entity.TsAssOperationDetail;
import com.maizhiyu.yzt.mapper.TsAssMapper;
import com.maizhiyu.yzt.mapper.TsAssOperationMapper;
import com.maizhiyu.yzt.service.ITsAssOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(rollbackFor=Exception.class)
public class TsAssOperationService extends ServiceImpl<TsAssOperationMapper , TsAssOperation> implements ITsAssOperationService {

    @Resource
    private TsAssOperationMapper tsAssOperationMapper;

    @Override
    public List<TsAssOperation> getAssDetail(Long sytechId) {
        List<TsAssOperation> list = tsAssOperationMapper.selectAssDetail(sytechId);
      return list;

    }
}
