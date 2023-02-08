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
import com.maizhiyu.yzt.mapper.BuPrescriptionItemMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionMapper;
import com.maizhiyu.yzt.ro.WaitSignatureRo;
import com.maizhiyu.yzt.service.IBuPrescriptionItemService;
import com.maizhiyu.yzt.vo.WaitSignatureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuPrescriptionItemService extends ServiceImpl<BuPrescriptionItemMapper,BuPrescriptionItem> implements IBuPrescriptionItemService {

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

        //补全诊断，开方次数，已治疗次数,适宜技术名称等信息
        List<Long> outpatientIdList = pageResult.getRecords().stream().map(WaitSignatureVo::getOutpatientId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        if(CollectionUtils.isEmpty(outpatientIdList))
            return pageResult;
        List<WaitSignatureVo> waitSignatureRoList = mapper.selectWaitSignatureInfo(outpatientIdList);
        if(CollectionUtils.isEmpty(waitSignatureRoList))
            return pageResult;

        Map<Long, WaitSignatureVo> prescriptionItemIdMap = waitSignatureRoList.stream().collect(Collectors.toMap(WaitSignatureVo::getPrescriptionItemId, Function.identity(), (k1, k2) -> k1));
        if(MapUtil.isEmpty(prescriptionItemIdMap))
            return pageResult;

        //将剩余的数据遍历填充
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
    public List<BuPrescriptionItem> getPrescriptionItemListByDiagnoseId(Long diagnoseId) {
        LambdaQueryWrapper<BuPrescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuPrescription::getIsDel, 0)
                .eq(BuPrescription::getDiagnoseId, diagnoseId)
                .orderByDesc(BuPrescription::getId)
                .last(" limit 1");

        BuPrescription buPrescription = buPrescriptionMapper.selectOne(queryWrapper);
        Preconditions.checkArgument(Objects.nonNull(buPrescription), "该诊断下尚未开具适宜技术!");

        LambdaQueryWrapper<BuPrescriptionItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BuPrescriptionItem::getIsDel, 0)
                .eq(BuPrescriptionItem::getPrescriptionId, buPrescription.getId());

        List<BuPrescriptionItem> buPrescriptionItems = mapper.selectList(wrapper);
        return buPrescriptionItems;
    }
}
