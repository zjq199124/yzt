package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.entity.BuPrescription;
import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointment;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionMapper;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.service.IBuPrescriptionItemService;
import com.maizhiyu.yzt.vo.WaitSignatureVo;
import com.sun.org.apache.xpath.internal.operations.Lt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuPrescriptionItemServiceImpl extends ServiceImpl<BuPrescriptionItemMapper,BuPrescriptionItem> implements IBuPrescriptionItemService {

    @Autowired
    private BuPrescriptionItemMapper mapper;

    @Resource
    private BuPrescriptionMapper buPrescriptionMapper;

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

    @Override
    public void deleteByIdList(List<Long> deleteIdList) {
        LambdaUpdateWrapper<BuPrescriptionItem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BuPrescriptionItem::getIsDel, 1)
                .in(BuPrescriptionItem::getId, deleteIdList);
        mapper.update(null,updateWrapper);
    }

    @Override
    public Page<WaitSignatureVo> selectWaitSignatureList(WaitSignatureRo waitSignatureRo) {
        Page<WaitSignatureVo> page = new Page<>(waitSignatureRo.getCurrentPage(), waitSignatureRo.getPageSize());
        Page<WaitSignatureVo> pageResult = mapper.selectWaitSignatureList(page,waitSignatureRo);

        if (CollectionUtils.isEmpty(pageResult.getRecords()))
            return pageResult;

        //?????????????????????????????????????????????,???????????????????????????
        List<Long> outpatientIdList = pageResult.getRecords().stream().map(WaitSignatureVo::getOutpatientId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        if(CollectionUtils.isEmpty(outpatientIdList))
            return pageResult;
        List<WaitSignatureVo> waitSignatureRoList = mapper.selectWaitSignatureInfo(outpatientIdList);
        if(CollectionUtils.isEmpty(waitSignatureRoList))
            return pageResult;

        Map<Long, WaitSignatureVo> prescriptionItemIdMap = waitSignatureRoList.stream().collect(Collectors.toMap(WaitSignatureVo::getPrescriptionItemId, Function.identity(), (k1, k2) -> k1));
        if(MapUtil.isEmpty(prescriptionItemIdMap))
            return pageResult;

        //??????????????????????????????
        pageResult.getRecords().forEach(item -> {
            WaitSignatureVo waitSignatureVo = prescriptionItemIdMap.get(item.getPrescriptionItemId());
            if(Objects.isNull(waitSignatureVo))
                return;

            item.setDisease(waitSignatureVo.getDisease());
            item.setTsName(waitSignatureVo.getTsName());
            item.setOutpatientTime(waitSignatureVo.getOutpatientTime());
        });

        return pageResult;
    }

    @Override
    public List<BuPrescriptionItem> selectHasUpdateData(Date startDate, Date endDate) {
        LambdaQueryWrapper<BuPrescriptionItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuPrescriptionItem::getIsDel, 0);
        if (Objects.isNull(startDate)) {
            queryWrapper.lt(BuPrescriptionItem::getUpdateTime, endDate);
        } else {
            queryWrapper.between(BuPrescriptionItem::getUpdateTime, startDate, endDate);
        }
        List<BuPrescriptionItem> buPrescriptionItemList = mapper.selectList(queryWrapper);
        return buPrescriptionItemList;
    }
}
