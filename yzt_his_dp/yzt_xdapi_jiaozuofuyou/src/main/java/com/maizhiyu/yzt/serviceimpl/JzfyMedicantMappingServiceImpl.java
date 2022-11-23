package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.JzfyMedicantMapping;
import com.maizhiyu.yzt.mapperypt.JzfyMedicantMappingMapper;
import com.maizhiyu.yzt.service.JzfyMedicantMappingService;
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
public class JzfyMedicantMappingServiceImpl extends ServiceImpl<JzfyMedicantMappingMapper, JzfyMedicantMapping> implements JzfyMedicantMappingService {

    @Resource
    private JzfyMedicantMappingMapper jzfyMedicantMappingMapper;

    @Override
    public JzfyMedicantMapping getMedicantByCode(String code) {
        LambdaQueryWrapper<JzfyMedicantMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JzfyMedicantMapping::getCode, code)
                .eq(JzfyMedicantMapping::getIsDel, 0)
                .orderByDesc(JzfyMedicantMapping::getUpdateTime)
                .last("limit 1");
        JzfyMedicantMapping jzfyMedicantMapping = jzfyMedicantMappingMapper.selectOne(queryWrapper);
        return jzfyMedicantMapping;
    }

    @Override
    public JzfyMedicantMapping getMedicantByName(String name) {
        LambdaQueryWrapper<JzfyMedicantMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JzfyMedicantMapping::getName, name)
                .eq(JzfyMedicantMapping::getIsDel, 0)
                .orderByDesc(JzfyMedicantMapping::getUpdateTime)
                .last("limit 1");
        JzfyMedicantMapping jzfyMedicantMapping = jzfyMedicantMappingMapper.selectOne(queryWrapper);
        return jzfyMedicantMapping;
    }

    @Override
    public List<JzfyMedicantMapping> getMedicantByCodeList(List<String> zhongyaoCodeList) {
        LambdaQueryWrapper<JzfyMedicantMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(JzfyMedicantMapping::getCode, zhongyaoCodeList)
                .eq(JzfyMedicantMapping::getIsDel, 0);
        List<JzfyMedicantMapping> jzfyMedicantMappings = jzfyMedicantMappingMapper.selectList(queryWrapper);
        return jzfyMedicantMappings;
    }

    @Override
    public List<JzfyMedicantMapping> getMedicantByNameList(List<String> zhongyaoNameList) {
        LambdaQueryWrapper<JzfyMedicantMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(JzfyMedicantMapping::getName, zhongyaoNameList)
                .eq(JzfyMedicantMapping::getIsDel, 0);
        List<JzfyMedicantMapping> jzfyMedicantMappings = jzfyMedicantMappingMapper.selectList(queryWrapper);
        return jzfyMedicantMappings;
    }
}











