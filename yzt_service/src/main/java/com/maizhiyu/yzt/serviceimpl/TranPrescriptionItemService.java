package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TranPrescriptionItem;
import com.maizhiyu.yzt.mapper.TranPrescriptionItemMapper;
import com.maizhiyu.yzt.service.ITranPrescriptionItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class TranPrescriptionItemService extends ServiceImpl<TranPrescriptionItemMapper, TranPrescriptionItem> implements ITranPrescriptionItemService {



}
