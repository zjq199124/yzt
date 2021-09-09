package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemMapper;
import com.maizhiyu.yzt.service.IBuPrescriptionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuPrescriptionItemService implements IBuPrescriptionItemService {

    @Autowired
    private BuPrescriptionItemMapper mapper;


    @Override
    public Integer addPrescriptionItem(BuPrescriptionItem item) {
        return mapper.insert(item);
    }

    @Override
    public Integer delPrescriptionItem(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setPrescriptionItem(BuPrescriptionItem item) {
        return mapper.updateById(item);
    }

    @Override
    public List<BuPrescriptionItem> getPrescriptionItemList(Long prescriptionId) {
        QueryWrapper<BuPrescriptionItem> wrapper = new QueryWrapper<>();
        wrapper.eq("prescription_id", prescriptionId);
        return mapper.selectList(wrapper);
    }
}
