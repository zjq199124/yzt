package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.MedicantMapping;
import com.maizhiyu.yzt.mapperypt.MedicantMappingMapper;
import com.maizhiyu.yzt.service.MedicantMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 焦作妇幼his-云平台-药材映射表 服务实现类
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-18
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class MedicantMappingServiceImpl extends ServiceImpl<MedicantMappingMapper, MedicantMapping> implements MedicantMappingService {

    @Resource
    private MedicantMappingMapper MedicantMappingMapper;

    @Override
    public MedicantMapping getMedicantByCode(String code) {
        LambdaQueryWrapper<MedicantMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicantMapping::getCode, code)
                .eq(MedicantMapping::getIsDel, 0)
                .orderByDesc(MedicantMapping::getUpdateTime)
                .last("limit 1");
        MedicantMapping MedicantMapping = MedicantMappingMapper.selectOne(queryWrapper);
        return MedicantMapping;
    }

    @Override
    public MedicantMapping getMedicantByName(String name) {
        LambdaQueryWrapper<MedicantMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicantMapping::getName, name)
                .eq(MedicantMapping::getIsDel, 0)
                .orderByDesc(MedicantMapping::getUpdateTime)
                .last("limit 1");
        MedicantMapping MedicantMapping = MedicantMappingMapper.selectOne(queryWrapper);
        return MedicantMapping;
    }

    @Override
    public List<MedicantMapping> getMedicantByCodeList(List<String> zhongyaoCodeList) {
        LambdaQueryWrapper<MedicantMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MedicantMapping::getCode, zhongyaoCodeList)
                .eq(MedicantMapping::getIsDel, 0);
        List<MedicantMapping> MedicantMappings = MedicantMappingMapper.selectList(queryWrapper);
        return MedicantMappings;
    }

    @Override
    public List<MedicantMapping> getMedicantByNameList(List<String> zhongyaoNameList) {
        LambdaQueryWrapper<MedicantMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MedicantMapping::getName, zhongyaoNameList)
                .eq(MedicantMapping::getIsDel, 0);
        List<MedicantMapping> MedicantMappings = MedicantMappingMapper.selectList(queryWrapper);
        return MedicantMappings;
    }
}











