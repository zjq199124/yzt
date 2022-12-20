package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuVisit;
import com.maizhiyu.yzt.mapper.BuVisitMapper;
import com.maizhiyu.yzt.ro.VisitRO;
import com.maizhiyu.yzt.service.BuVisitService;
import com.maizhiyu.yzt.vo.VisitVO;
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
public class BuVisitServiceImpl extends ServiceImpl<BuVisitMapper, BuVisit> implements BuVisitService {


    @Override
    public IPage<VisitVO> getPage(Page page, VisitRO visitRO) {
        return baseMapper.getPage(page,visitRO);
    }
}
