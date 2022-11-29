package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maizhiyu.yzt.entity.JzfyDiseaseMapping;
import com.maizhiyu.yzt.mapperypt.JzfyDiseaseMappingMapper;
import com.maizhiyu.yzt.service.JzfyDiseaseMappingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 焦作妇幼his-云平台-疾病映射表 服务实现类
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-14
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class JzfyDiseaseMappingServiceImpl extends ServiceImpl<JzfyDiseaseMappingMapper, JzfyDiseaseMapping> implements JzfyDiseaseMappingService {

    @Resource
    private JzfyDiseaseMappingMapper jzfyDiseaseMappingMapper;

    @Override
    public JzfyDiseaseMapping selectByHisName(String hisDiseaseName) {
        LambdaQueryWrapper<JzfyDiseaseMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JzfyDiseaseMapping::getHisName, hisDiseaseName)
                .eq(JzfyDiseaseMapping::getIsDel, 0)
                .orderByDesc(JzfyDiseaseMapping::getUpdateTime)
                .last("limit 1");
        JzfyDiseaseMapping jzfyDiseaseMapping = jzfyDiseaseMappingMapper.selectOne(queryWrapper);
        return jzfyDiseaseMapping;
    }

    @Override
    public List<JzfyDiseaseMapping> diseaseList(String search) {
        LambdaQueryWrapper<JzfyDiseaseMapping> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(search)) {
            queryWrapper.like(JzfyDiseaseMapping::getHisName, search);
        }
        queryWrapper.eq(JzfyDiseaseMapping::getIsDel, 0);
        List<JzfyDiseaseMapping> jzfyDiseaseMappingList = jzfyDiseaseMappingMapper.selectList(queryWrapper);
        return jzfyDiseaseMappingList;
    }
}
