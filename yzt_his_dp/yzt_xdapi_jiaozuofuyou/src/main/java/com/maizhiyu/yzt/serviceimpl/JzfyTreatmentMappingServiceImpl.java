package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.JzfyTreatmentMapping;
import com.maizhiyu.yzt.mapperypt.JzfyTreatmentMappingMapper;
import com.maizhiyu.yzt.service.JzfyTreatmentMappingService;
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
public class JzfyTreatmentMappingServiceImpl extends ServiceImpl<JzfyTreatmentMappingMapper, JzfyTreatmentMapping> implements JzfyTreatmentMappingService {

    @Resource
    private JzfyTreatmentMappingMapper jzfyTreatmentMappingMapper;

    @Override
    public JzfyTreatmentMapping getTreatmentByCode(String code) {
        LambdaQueryWrapper<JzfyTreatmentMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JzfyTreatmentMapping::getCode, code)
                .eq(JzfyTreatmentMapping::getIsDel, 0)
                .orderByDesc(JzfyTreatmentMapping::getUpdateTime)
                .last("limit 1");
        JzfyTreatmentMapping jzfyTreatmentMapping = jzfyTreatmentMappingMapper.selectOne(queryWrapper);
        return jzfyTreatmentMapping;
    }

    @Override
    public JzfyTreatmentMapping getTreatmentByName(String name) {
        LambdaQueryWrapper<JzfyTreatmentMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JzfyTreatmentMapping::getName, name)
                .eq(JzfyTreatmentMapping::getIsDel, 0)
                .orderByDesc(JzfyTreatmentMapping::getUpdateTime)
                .last("limit 1");
        JzfyTreatmentMapping jzfyTreatmentMapping = jzfyTreatmentMappingMapper.selectOne(queryWrapper);
        return jzfyTreatmentMapping;
    }

    @Override
    public JzfyTreatmentMapping getTreatmentByHisCode(String hisCode) {
        LambdaQueryWrapper<JzfyTreatmentMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JzfyTreatmentMapping::getHiscode, hisCode)
                .eq(JzfyTreatmentMapping::getIsDel, 0)
                .orderByDesc(JzfyTreatmentMapping::getUpdateTime)
                .last("limit 1");
        JzfyTreatmentMapping jzfyTreatmentMapping = jzfyTreatmentMappingMapper.selectOne(queryWrapper);
        return jzfyTreatmentMapping;
    }

    @Override
    public JzfyTreatmentMapping getTreatmentByHisName(String hisName) {
        LambdaQueryWrapper<JzfyTreatmentMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JzfyTreatmentMapping::getName, hisName)
                .eq(JzfyTreatmentMapping::getIsDel, 0)
                .orderByDesc(JzfyTreatmentMapping::getUpdateTime)
                .last("limit 1");
        JzfyTreatmentMapping jzfyTreatmentMapping = jzfyTreatmentMappingMapper.selectOne(queryWrapper);
        return jzfyTreatmentMapping;
    }
}












