package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.HisDoctor;
import com.maizhiyu.yzt.mapperhis.HisDoctorMapper;
import com.maizhiyu.yzt.service.IHisDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor=Exception.class)
public class HisDoctorService implements IHisDoctorService {

    @Autowired
    private HisDoctorMapper mapper;

    @Override
    public List<HisDoctor> getDoctorList(Date startTime, Date endTime) {
        QueryWrapper<HisDoctor> wrapper = new QueryWrapper<>();
        wrapper.ge("update_time", startTime)
                .lt("update_time", endTime);
        return mapper.selectList(wrapper);
    }

}
