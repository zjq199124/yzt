package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TreatmentMapping;
import com.maizhiyu.yzt.mapperypt.TreatmentMappingMapper;
import com.maizhiyu.yzt.service.TreatmentMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 焦作妇幼his-云平台-治疗(适宜技术)映射表 服务实现类
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TreatmentMappingServiceImpl extends ServiceImpl<TreatmentMappingMapper, TreatmentMapping> implements TreatmentMappingService {

    @Resource
    private TreatmentMappingMapper TreatmentMappingMapper;

    @Override
    public TreatmentMapping getTreatmentByCode(String code) {
        LambdaQueryWrapper<TreatmentMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TreatmentMapping::getCode, code)
                .isNotNull(TreatmentMapping::getHiscode)
                .isNotNull(TreatmentMapping::getHisname)
                .eq(TreatmentMapping::getIsDel, 0)
                .orderByDesc(TreatmentMapping::getUpdateTime)
                .last("limit 1");
        TreatmentMapping TreatmentMapping = TreatmentMappingMapper.selectOne(queryWrapper);
        return TreatmentMapping;
    }

    @Override
    public TreatmentMapping getTreatmentByName(String name) {
        LambdaQueryWrapper<TreatmentMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TreatmentMapping::getName, name)
                .eq(TreatmentMapping::getIsDel, 0)
                .isNotNull(TreatmentMapping::getHiscode)
                .isNotNull(TreatmentMapping::getHisname)
                .orderByDesc(TreatmentMapping::getUpdateTime)
                .last("limit 1");
        TreatmentMapping TreatmentMapping = TreatmentMappingMapper.selectOne(queryWrapper);
        return TreatmentMapping;
    }

    @Override
    public TreatmentMapping getTreatmentByHisCode(String hisCode) {
        LambdaQueryWrapper<TreatmentMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TreatmentMapping::getHiscode, hisCode)
                .eq(TreatmentMapping::getIsDel, 0)
                .orderByDesc(TreatmentMapping::getUpdateTime)
                .last("limit 1");
        TreatmentMapping TreatmentMapping = TreatmentMappingMapper.selectOne(queryWrapper);
        return TreatmentMapping;
    }

    @Override
    public TreatmentMapping getTreatmentByHisName(String hisName) {
        LambdaQueryWrapper<TreatmentMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TreatmentMapping::getName, hisName)
                .eq(TreatmentMapping::getIsDel, 0)
                .orderByDesc(TreatmentMapping::getUpdateTime)
                .last("limit 1");
        TreatmentMapping TreatmentMapping = TreatmentMappingMapper.selectOne(queryWrapper);
        return TreatmentMapping;
    }
}












