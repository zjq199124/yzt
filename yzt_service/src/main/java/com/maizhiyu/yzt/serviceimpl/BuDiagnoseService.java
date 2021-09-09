package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.BuDiagnose;
import com.maizhiyu.yzt.mapper.BuDiagnoseMapper;
import com.maizhiyu.yzt.service.IBuDiagnoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuDiagnoseService implements IBuDiagnoseService {

    @Autowired
    private BuDiagnoseMapper mapper;

    @Override
    public Integer addDiagnose(BuDiagnose diagnose) {
        return mapper.insert(diagnose);
    }

    @Override
    public Integer setDiagnose(BuDiagnose diagnose) {
        return mapper.updateById(diagnose);
    }

    @Override
    public BuDiagnose getDiagnose(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public BuDiagnose getDiagnoseOfOutpatient(Long outpatientId) {
        QueryWrapper<BuDiagnose> wrapper = new QueryWrapper<>();
        wrapper.eq("outpatient_id", outpatientId);
        return mapper.selectOne(wrapper);
    }

}
