package com.maizhiyu.yzt.serviceimpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsAssOperation;
import com.maizhiyu.yzt.mapper.TsAssMapper;
import com.maizhiyu.yzt.mapper.TsAssOperationMapper;
import com.maizhiyu.yzt.service.ITsAssOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor=Exception.class)
public class TsAssOperationService extends ServiceImpl<TsAssOperationMapper , TsAssOperation> implements ITsAssOperationService {

    @Resource
    private TsAssOperationMapper tsAssOperationMapper;

    @Override
    public List<Map<String, Object>> getAssDetail(Long sytechId) {
        List<Map<String, Object>> list = tsAssOperationMapper.selectAssDetail(sytechId);
        return list;

    }
}
