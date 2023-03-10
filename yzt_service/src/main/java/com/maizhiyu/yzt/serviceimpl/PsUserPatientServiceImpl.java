package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.PsUserPatient;
import com.maizhiyu.yzt.mapper.PsUserPatientMapper;
import com.maizhiyu.yzt.service.IPsUserPatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PsUserPatientServiceImpl extends ServiceImpl<PsUserPatientMapper, PsUserPatient> implements IPsUserPatientService {

    @Resource
    private PsUserPatientMapper psUserPatientMapper;

    @Override
    public List<PsUserPatient> selectByUserId(Long psUserId) {
        LambdaQueryWrapper<PsUserPatient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PsUserPatient::getUserId, psUserId);
        List<PsUserPatient> psUserPatientList = psUserPatientMapper.selectList(queryWrapper);
        return psUserPatientList;
    }
}
