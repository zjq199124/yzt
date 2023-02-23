package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.mapper.BuOutpatientMapper;
import com.maizhiyu.yzt.service.IBuOutpatientService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Service
@Transactional(rollbackFor = Exception.class)
public class BuOutpatientService extends ServiceImpl<BuOutpatientMapper, BuOutpatient> implements IBuOutpatientService {

    @Autowired
    private BuOutpatientMapper mapper;


    @Override
    @ExistCheck(clazz = BuOutpatient.class, fname = "time|customer_id|patient_id", message = "挂号已存在")
    public Integer addOutpatient(BuOutpatient outpatient) {
        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        outpatient.setCode(code);
        return mapper.insert(outpatient);
    }

    @Override
    public Integer setOutpatient(BuOutpatient outpatient) {
        return mapper.updateById(outpatient);
    }

    @Override
    public BuOutpatient getOutpatient(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public BuOutpatient getOutpatientByHisId(Long customerId, Long hisId) {
        QueryWrapper<BuOutpatient> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(customerId)) {
            wrapper.eq("customer_id", customerId);
        }

        wrapper.eq("his_id", hisId)
                .orderByDesc("update_time")
                .last("limit 1");
        return mapper.selectOne(wrapper);
    }

    @Override
    public IPage<Map<String, Object>> getOutpatientList(Page<Map<String,Object>> page, String createStartDate, String createEndDate, String startDate, String endDate, Long customerId, Long departmentId, Long doctorId, Long patientId, Integer type, Integer status, String term) {
        return mapper.selectOutpatientList(page,createStartDate, createEndDate, startDate, endDate, customerId, departmentId, doctorId, patientId, type, status, term);
    }

    @Override
    public IPage<Map<String, Object>> getPsUserOutpatientList(Page page,Long userId, Long patientId, Integer type, Integer status) {
        return mapper.selectPsUserOutpatientList(page,userId, patientId, type, status);
    }

    @Override
    public List<BuOutpatient> selectByHisIdListAndCustomerId(Long customerId, List<Long> hisIdList) {
        LambdaQueryWrapper<BuOutpatient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuOutpatient::getCustomerId, customerId)
                .in(BuOutpatient::getHisId, hisIdList);

        return mapper.selectList(queryWrapper);
    }
}
