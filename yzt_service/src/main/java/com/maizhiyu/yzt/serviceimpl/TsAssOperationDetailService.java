package com.maizhiyu.yzt.serviceimpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsAssOperationDetail;
import com.maizhiyu.yzt.mapper.TsAssOperationDetailMapper;
import com.maizhiyu.yzt.service.ITsAssOperationDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional(rollbackFor = Exception.class)
public class TsAssOperationDetailService extends ServiceImpl<TsAssOperationDetailMapper, TsAssOperationDetail> implements ITsAssOperationDetailService {

}
