package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TreatmentMapping;
import com.maizhiyu.yzt.mapper.TreatmentMappingMapper;
import com.maizhiyu.yzt.service.ITreatmentMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
