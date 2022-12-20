package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuCure;
import com.maizhiyu.yzt.mapper.BuCureMapper;
import com.maizhiyu.yzt.service.BuCureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 治疗表(BuCure)表服务实现类
 *
 * @author liuyzh
 * @since 2022-12-19 18:52:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BuCureServiceImpl extends ServiceImpl<BuCureMapper, BuCure> implements BuCureService {
 
}
