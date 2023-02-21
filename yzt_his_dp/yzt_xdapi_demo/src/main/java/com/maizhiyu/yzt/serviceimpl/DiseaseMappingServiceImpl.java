package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.DiseaseMapping;
import com.maizhiyu.yzt.mapperypt.DiseaseMappingMapper;
import com.maizhiyu.yzt.service.DiseaseMappingService;
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
public class DiseaseMappingServiceImpl extends ServiceImpl<DiseaseMappingMapper, DiseaseMapping> implements DiseaseMappingService {

    @Resource
    private DiseaseMappingMapper DiseaseMappingMapper;

    @Override
    public DiseaseMapping selectByCustomerIdAndHisName(Long customerId,String hisDiseaseName) {
        LambdaQueryWrapper<DiseaseMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DiseaseMapping::getHisName, hisDiseaseName)
                .eq(DiseaseMapping::getCustomerId, customerId)
                .eq(DiseaseMapping::getIsDel, 0)
                .isNotNull(DiseaseMapping::getDiseaseId)
                .orderByDesc(DiseaseMapping::getUpdateTime)
                .last("limit 1");
        DiseaseMapping DiseaseMapping = DiseaseMappingMapper.selectOne(queryWrapper);
        return DiseaseMapping;
    }

    @Override
    public List<DiseaseMapping> diseaseList(Long customerId,String search) {
        LambdaQueryWrapper<DiseaseMapping> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(search)) {
            queryWrapper.like(DiseaseMapping::getHisName, search);
        }
        queryWrapper.eq(DiseaseMapping::getIsDel, 0);
        queryWrapper.eq(DiseaseMapping::getCustomerId, customerId);
        List<DiseaseMapping> DiseaseMappingList = DiseaseMappingMapper.selectList(queryWrapper);
        return DiseaseMappingList;
    }
}
