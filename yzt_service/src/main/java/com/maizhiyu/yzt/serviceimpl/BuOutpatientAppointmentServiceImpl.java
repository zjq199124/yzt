package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.mapper.BuOutpatientAppointmentMapper;
import com.maizhiyu.yzt.ro.OutpatientAppointmentRo;
import com.maizhiyu.yzt.service.IBuOutpatientAppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BuOutpatientAppointmentServiceImpl extends ServiceImpl<BuOutpatientAppointmentMapper,BuOutpatientAppointment> implements IBuOutpatientAppointmentService {

    @Resource
    private BuOutpatientAppointmentMapper buOutpatientAppointmentMapper;

    @Override
    public List<BuOutpatientAppointment> list(OutpatientAppointmentRo outpatientAppointmentRo) {
        return buOutpatientAppointmentMapper.list(outpatientAppointmentRo);
    }
}
