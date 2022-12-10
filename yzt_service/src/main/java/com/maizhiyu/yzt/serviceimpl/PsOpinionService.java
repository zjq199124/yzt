package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.PsOpinion;
import com.maizhiyu.yzt.mapper.PsOpinionMapper;
import com.maizhiyu.yzt.service.IPsOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor=Exception.class)
public class PsOpinionService extends ServiceImpl<PsOpinionMapper,PsOpinion> implements IPsOpinionService {

    @Autowired
    private PsOpinionMapper mapper;

    @Override
    public Integer addOpinion(PsOpinion opinion) {
        return mapper.insert(opinion);
    }

    @Override
    public PsOpinion getOpinion(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<PsOpinion> getOpinionList(Long uid) {
        QueryWrapper<PsOpinion> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        return mapper.selectList(wrapper);
    }

}
