package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.entity.BuCure;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointment;
import com.maizhiyu.yzt.mapper.BuCureMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemAppointmentMapper;
import com.maizhiyu.yzt.ro.BuCureSearchRO;
import com.maizhiyu.yzt.service.BuCureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Resource
    private BuCureMapper buCureMapper;

    @Resource
    private BuPrescriptionItemAppointmentMapper buPrescriptionItemAppointmentMapper;

    @Override
    public boolean saveOrUpdateBuCure(BuCure buCure) {
        if (Objects.isNull(buCure.getId())) {
            //先判断同一个签到的数据是否已经生成了治疗记录
            LambdaQueryWrapper<BuCure> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BuCure::getSignatureId, buCure.getSignatureId())
                    .eq(BuCure::getCustomerId, buCure.getCustomerId())
                    .eq(BuCure::getOutpatientId, buCure.getOutpatientId())
                    .eq(BuCure::getIsDel, 0)
                    .last("limit 1");
            BuCure select = buCureMapper.selectOne(queryWrapper);
            Preconditions.checkArgument(Objects.isNull(select), "本次签到已经存在治疗数据!");

            buCure.setIsDel(0);
            buCure.setCreateTime(new Date());
            buCure.setUpdateTime(buCure.getCreateTime());
            int insert = buCureMapper.insert(buCure);
            return insert > 0;
        } else {
            buCure.setUpdateTime(new Date());
            int update = buCureMapper.updateById(buCure);
            return update > 0;
        }
    }

    @Override
    public boolean endTreatment(Long id) {
        BuCure buCure = buCureMapper.selectById(id);
        Preconditions.checkArgument(Objects.nonNull(buCure), "治疗id错误!");

        buCure.setStatus(2);
        buCure.setUpdateTime(new Date());

        int update = buCureMapper.updateById(buCure);
        if (update > 0) {
            //增加已治疗次数
            LambdaUpdateWrapper<BuPrescriptionItemAppointment> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(BuPrescriptionItemAppointment::getPrescriptionItemId, buCure.getPrescriptionItemId())
                    .eq(BuPrescriptionItemAppointment::getIsDel, 0)
                    .setSql("treatment_quantity = treatment_quantity + 1");

            update = buPrescriptionItemAppointmentMapper.update(null, updateWrapper);
        }
        return update > 0;
    }

    @Override
    public Page<BuCure> treatmentList(BuCureSearchRO buCureSearchRO) {
        Page<BuCure> page = new Page<>(buCureSearchRO.getCurrentPage(), buCureSearchRO.getPageSize());
        Page<BuCure> resultPage = buCureMapper.selectTreatmentList(page, buCureSearchRO);
        return resultPage;
    }
}
