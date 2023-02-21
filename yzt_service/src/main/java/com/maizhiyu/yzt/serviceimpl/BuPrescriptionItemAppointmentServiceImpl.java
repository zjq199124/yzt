package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointment;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemAppointmentMapper;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BuPrescriptionItemAppointmentServiceImpl extends ServiceImpl<BuPrescriptionItemAppointmentMapper, BuPrescriptionItemAppointment> implements IBuPrescriptionItemAppointmentService {

    @Resource
    private BuPrescriptionItemAppointmentMapper buPrescriptionItemAppointmentMapper;

    @Override
    public BuPrescriptionItemAppointment selectByAppointmentIdAndItemId(Long outpatientAppointmentId, Long prescriptionItemId) {
        LambdaQueryWrapper<BuPrescriptionItemAppointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuPrescriptionItemAppointment::getOutpatientAppointmentId, outpatientAppointmentId)
                .eq(BuPrescriptionItemAppointment::getPrescriptionItemId, prescriptionItemId)
                .eq(BuPrescriptionItemAppointment::getIsDel, 0)
                .last("limit 1");
        return buPrescriptionItemAppointmentMapper.selectOne(queryWrapper);
    }

    @Override
    public List<BuPrescriptionItemAppointment> selectByItemHisIdList(List<Long> prescriptionItemHisIdList) {
        LambdaQueryWrapper<BuPrescriptionItemAppointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BuPrescriptionItemAppointment::getPrescriptionItemId, prescriptionItemHisIdList)
                .eq(BuPrescriptionItemAppointment::getIsDel, 0);

        return buPrescriptionItemAppointmentMapper.selectList(queryWrapper);
    }
}
