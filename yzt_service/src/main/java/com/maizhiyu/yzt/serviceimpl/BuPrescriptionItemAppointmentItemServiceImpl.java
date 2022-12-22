package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.maizhiyu.yzt.entity.BuOutpatientAppointment;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointment;
import com.maizhiyu.yzt.entity.BuPrescriptionItemAppointmentItem;
import com.maizhiyu.yzt.mapper.BuOutpatientAppointmentMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemAppointmentItemMapper;
import com.maizhiyu.yzt.mapper.BuPrescriptionItemAppointmentMapper;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class BuPrescriptionItemAppointmentItemServiceImpl extends ServiceImpl<BuPrescriptionItemAppointmentItemMapper, BuPrescriptionItemAppointmentItem> implements IBuPrescriptionItemAppointmentItemService {

    @Resource
    private BuPrescriptionItemAppointmentItemMapper buPrescriptionItemAppointmentItemMapper;

    @Resource
    private BuPrescriptionItemAppointmentMapper buPrescriptionItemAppointmentMapper;

    @Resource
    private BuOutpatientAppointmentMapper buOutpatientAppointmentMapper;

    @Override
    public Boolean makeAppointment(BuPrescriptionItemAppointmentItem buPrescriptionItemAppointmentItem) {
        int insert = 0;
        int update = 0;
        int result = 0;
        //先查询是否在该时段已经预约了
        LambdaQueryWrapper<BuPrescriptionItemAppointmentItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BuPrescriptionItemAppointmentItem::getIsDel, 0)
                .eq(BuPrescriptionItemAppointmentItem::getAppointmentDate, buPrescriptionItemAppointmentItem.getAppointmentDate())
                .eq(BuPrescriptionItemAppointmentItem::getTimeSlot, buPrescriptionItemAppointmentItem.getTimeSlot())
                .eq(BuPrescriptionItemAppointmentItem::getCustomerId, buPrescriptionItemAppointmentItem.getCustomerId())
                .eq(BuPrescriptionItemAppointmentItem::getPatientId, buPrescriptionItemAppointmentItem.getPatientId())
                .eq(BuPrescriptionItemAppointmentItem::getOutpatientId, buPrescriptionItemAppointmentItem.getOutpatientId())
                .last("limit 1");
        BuPrescriptionItemAppointmentItem select = buPrescriptionItemAppointmentItemMapper.selectOne(queryWrapper);

        Preconditions.checkArgument(Objects.isNull(select), "该患者在该时段已经有预约不能重复预约!");

        buPrescriptionItemAppointmentItem.setState(1);
        buPrescriptionItemAppointmentItem.setCreateTime(new Date());
        buPrescriptionItemAppointmentItem.setUpdateTime(buPrescriptionItemAppointmentItem.getCreateTime());
        insert = buPrescriptionItemAppointmentItemMapper.insert(buPrescriptionItemAppointmentItem);

        if (insert > 0) {
            //预约成功，要增加该适宜技术已预约的次数，扣减剩余预约的次数
            LambdaQueryWrapper<BuPrescriptionItemAppointment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(BuPrescriptionItemAppointment::getIsDel, 0)
                    .eq(BuPrescriptionItemAppointment::getPrescriptionItemId, buPrescriptionItemAppointmentItem.getPrescriptionItemAppointmentId())
                    .orderByDesc(BuPrescriptionItemAppointment::getCreateTime)
                    .last("limit 1");
            BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectOne(wrapper);
            Preconditions.checkArgument(Objects.nonNull(buPrescriptionItemAppointment), "当前所开适宜技术的预约信息错误!");

            buPrescriptionItemAppointment.setSurplusQuantity(buPrescriptionItemAppointment.getSurplusQuantity() - 1);
            buPrescriptionItemAppointment.setAppointmentQuantity(buPrescriptionItemAppointment.getAppointmentQuantity() + 1);

            if (buPrescriptionItemAppointment.getSurplusQuantity() == 0) {
                buPrescriptionItemAppointment.setState(3);
            } else {
                buPrescriptionItemAppointment.setState(2);
            }
            buPrescriptionItemAppointment.setUpdateTime(new Date());
            update = buPrescriptionItemAppointmentMapper.updateById(buPrescriptionItemAppointment);
            if (update > 0) {
                //更新整个诊断下的预约的状态
                LambdaQueryWrapper<BuOutpatientAppointment> query = new LambdaQueryWrapper<>();
                query.eq(BuOutpatientAppointment::getIsDel, 0)
                        .eq(BuOutpatientAppointment::getCustomerId, buPrescriptionItemAppointment.getCustomerId())
                        .eq(BuOutpatientAppointment::getPatientId, buPrescriptionItemAppointment.getPatientId())
                        .eq(BuOutpatientAppointment::getOutpatientId, buPrescriptionItemAppointment.getOutpatientAppointmentId())
                        .orderByDesc(BuOutpatientAppointment::getCreateTime)
                        .last("limit 1");
                BuOutpatientAppointment buOutpatientAppointment = buOutpatientAppointmentMapper.selectOne(query);
                Preconditions.checkArgument(Objects.nonNull(buOutpatientAppointment), "当前门诊下的预约数据错误!");

                if (buOutpatientAppointment.getState() == 1) {//未预约
                    //预约中
                    buOutpatientAppointment.setState(2);
                } else if (buOutpatientAppointment.getState() == 2) {//预约中
                    //查询该诊断下的适宜技术的预约数据是否还有不是预约完成状态的
                    LambdaQueryWrapper<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentQueryWrapper = new LambdaQueryWrapper<>();
                    buPrescriptionItemAppointmentQueryWrapper.eq(BuPrescriptionItemAppointment::getIsDel, 0)
                            .eq(BuPrescriptionItemAppointment::getOutpatientAppointmentId, buOutpatientAppointment.getId())
                            .eq(BuPrescriptionItemAppointment::getCustomerId, buPrescriptionItemAppointment.getCustomerId())
                            .eq(BuPrescriptionItemAppointment::getPatientId, buPrescriptionItemAppointment.getPatientId())
                            .eq(BuPrescriptionItemAppointment::getOutpatientId, buPrescriptionItemAppointment.getOutpatientId())
                            .ne(BuPrescriptionItemAppointment::getState, 3);

                    List<BuPrescriptionItemAppointment> list = buPrescriptionItemAppointmentMapper.selectList(buPrescriptionItemAppointmentQueryWrapper);
                    if (CollectionUtils.isEmpty(list)) {
                        //没有查到不是预约完成状态的子项
                        buOutpatientAppointment.setState(3);
                    } else {
                        buOutpatientAppointment.setState(2);
                    }
                    result = buOutpatientAppointmentMapper.updateById(buOutpatientAppointment);
                }
            }
        }
        return insert > 0 && update > 0 && result > 0;
    }

    @Override
    public Boolean deleteAppointment(Long buPrescriptionItemAppointmentItemId) {
        LambdaUpdateWrapper<BuPrescriptionItemAppointmentItem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BuPrescriptionItemAppointmentItem::getIsDel, 1)
                .eq(BuPrescriptionItemAppointmentItem::getId, buPrescriptionItemAppointmentItemId);
        int update = buPrescriptionItemAppointmentItemMapper.update(null, updateWrapper);
        return update > 0;
    }
}
