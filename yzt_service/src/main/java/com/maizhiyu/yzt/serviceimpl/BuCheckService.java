package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.BuCheckMapper;
import com.maizhiyu.yzt.mapper.BuOutpatientMapper;
import com.maizhiyu.yzt.service.IBuCheckService;
import com.maizhiyu.yzt.utils.MyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuCheckService implements IBuCheckService {

    @Autowired
    private BuCheckMapper mapper;

    @Autowired
    private BuOutpatientMapper outpatientMapper;

    @Override
    public Integer addCheck(BuCheck check) {
        // 获取诊断
        String name = check.getPatientName();
        String phone = check.getPatientPhone();
        String timeStart = MyDate.getTodayStr();
        String timeEnd = MyDate.getTomorrowStr();
        List<Map<String, Object>> list = outpatientMapper.selectOutpatientByPatientInfo(name, phone, timeStart, timeEnd);
        if (list.size() != 1) {
            throw new BusinessException("门诊信息不存在");
        }
        // 补充门诊ID
        Long outpatientId = (Long) list.get(0).get("id");
        check.setOutpatientId(outpatientId);
        return mapper.insert(check);
    }

    @Override
    public Integer setCheck(BuCheck check) {
        return mapper.updateById(check);
    }

    @Override
    public BuCheck getCheck(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<BuCheck> getCheckListOfOutpatient(Long outpatientId) {
        QueryWrapper<BuCheck> wrapper = new QueryWrapper<>();
        wrapper.eq("outpatient_id", outpatientId);
        return mapper.selectList(wrapper);
    }

}
