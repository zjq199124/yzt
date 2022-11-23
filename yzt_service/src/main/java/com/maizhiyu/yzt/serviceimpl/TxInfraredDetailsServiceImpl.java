package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TxInfraredDetails;
import com.maizhiyu.yzt.mapper.TxInfraredDetailsMapper;
import com.maizhiyu.yzt.service.TxInfraredDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * (TxInfraredDetails)表服务实现类
 *
 * @author makejava
 * @since 2022-11-21 20:26:25
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class TxInfraredDetailsServiceImpl extends ServiceImpl<TxInfraredDetailsMapper, TxInfraredDetails> implements TxInfraredDetailsService {


}

