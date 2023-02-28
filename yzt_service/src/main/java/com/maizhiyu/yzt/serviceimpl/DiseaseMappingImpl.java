package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.DiseaseMapping;
import com.maizhiyu.yzt.entity.TreatmentMapping;
import com.maizhiyu.yzt.mapper.DiseaseMappingMapper;
import com.maizhiyu.yzt.mapper.TreatmentMappingMapper;
import com.maizhiyu.yzt.service.DiseaseMappingService;
import com.maizhiyu.yzt.service.IDictDiseaseService;
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
public class DiseaseMappingImpl extends ServiceImpl<DiseaseMappingMapper, DiseaseMapping> implements DiseaseMappingService {

}
