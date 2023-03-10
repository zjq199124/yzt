package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TreatmentMapping;
import com.maizhiyu.yzt.mapper.TreatmentMappingMapper;
import com.maizhiyu.yzt.service.ITreatmentMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 患者随访表(BuVisit)表服务实现类
 *
 * @author liuyzh
 * @since 2022-12-19 19:00:54
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class TreatmentMappingImpl extends ServiceImpl<TreatmentMappingMapper, TreatmentMapping> implements ITreatmentMappingService {

    @Resource
    private TreatmentMappingMapper treatmentMappingMapper;

    @Override
    public List<TreatmentMapping> selectByCodeList(Long customerId, List<Long> codeList) {
        LambdaQueryWrapper<TreatmentMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TreatmentMapping::getCustomerId, customerId)
                .in(TreatmentMapping::getCode, codeList);

        List<TreatmentMapping> treatmentMappingList = treatmentMappingMapper.selectList(queryWrapper);
        return treatmentMappingList;
    }
}
