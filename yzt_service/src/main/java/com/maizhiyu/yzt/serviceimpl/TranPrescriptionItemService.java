package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TranPrescriptionItem;
import com.maizhiyu.yzt.mapper.TranPrescriptionItemMapper;
import com.maizhiyu.yzt.service.ITranPrescriptionItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class TranPrescriptionItemService extends ServiceImpl<TranPrescriptionItemMapper, TranPrescriptionItem> implements ITranPrescriptionItemService {

    @Resource
    private TranPrescriptionItemMapper tranPrescriptionItemMapper;

    public void deleteByIdList(List<Long> deleteIdList) {
        LambdaUpdateWrapper<TranPrescriptionItem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(TranPrescriptionItem::getIsDel, 1)
                .in(TranPrescriptionItem::getId, deleteIdList);
        tranPrescriptionItemMapper.update(null,updateWrapper);
    }
}
