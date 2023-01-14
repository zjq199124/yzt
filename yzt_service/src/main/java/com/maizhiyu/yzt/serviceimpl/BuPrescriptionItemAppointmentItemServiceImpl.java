package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
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
import com.maizhiyu.yzt.ro.AppointmentRo;
import com.maizhiyu.yzt.ro.BuPrescriptionItemAppointmentItemRo;
import com.maizhiyu.yzt.service.IBuPrescriptionItemAppointmentItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        int del = 0;
        int update = 0;
        int result = 0;
        BuPrescriptionItemAppointmentItem buPrescriptionItemAppointmentItem = buPrescriptionItemAppointmentItemMapper.selectById(buPrescriptionItemAppointmentItemId);
        Preconditions.checkArgument(Objects.nonNull(buPrescriptionItemAppointmentItem), "处方明细适宜技术具体预约数据id错误!");

        if (buPrescriptionItemAppointmentItem.getIsDel() == 1) {
            //该预约数据已经删除成功了
            return true;
        }
        buPrescriptionItemAppointmentItem.setIsDel(1);
        buPrescriptionItemAppointmentItem.setUpdateTime(new Date());
        del = buPrescriptionItemAppointmentItemMapper.updateById(buPrescriptionItemAppointmentItem);
        if (del > 0) {
            //对应的适宜技术小项目中的预约数据要-1，剩余预约次数要+1
            BuPrescriptionItemAppointment buPrescriptionItemAppointment = buPrescriptionItemAppointmentMapper.selectById(buPrescriptionItemAppointmentItem.getPrescriptionItemAppointmentId());
            Preconditions.checkArgument(Objects.nonNull(buPrescriptionItemAppointmentItem), "处方明细适宜技术预约汇总数据错误!");

            buPrescriptionItemAppointment.setAppointmentQuantity(buPrescriptionItemAppointment.getAppointmentQuantity() - 1);
            buPrescriptionItemAppointment.setSurplusQuantity(buPrescriptionItemAppointment.getSurplusQuantity() + 1);
            if (buPrescriptionItemAppointment.getSurplusQuantity() == buPrescriptionItemAppointment.getQuantity()) {
                //剩余预约次数和开单次数相等，改为未预约
                buPrescriptionItemAppointment.setState(1);
            } else {
                //预约状态改成预约中
                buPrescriptionItemAppointment.setState(2);
            }
            buPrescriptionItemAppointment.setUpdateTime(new Date());
            update = buPrescriptionItemAppointmentMapper.updateById(buPrescriptionItemAppointment);
            if (update > 0) {
                //更改所有的门诊下的预约数据的预约状态
                //查询该诊断下的适宜技术的预约数据是否还有不是未预约的数据
                LambdaQueryWrapper<BuPrescriptionItemAppointment> buPrescriptionItemAppointmentQueryWrapper = new LambdaQueryWrapper<>();
                buPrescriptionItemAppointmentQueryWrapper.eq(BuPrescriptionItemAppointment::getIsDel, 0)
                        .eq(BuPrescriptionItemAppointment::getOutpatientAppointmentId, buPrescriptionItemAppointment.getOutpatientAppointmentId())
                        .eq(BuPrescriptionItemAppointment::getCustomerId, buPrescriptionItemAppointment.getCustomerId())
                        .eq(BuPrescriptionItemAppointment::getPatientId, buPrescriptionItemAppointment.getPatientId())
                        .eq(BuPrescriptionItemAppointment::getOutpatientId, buPrescriptionItemAppointment.getOutpatientId())
                        .ne(BuPrescriptionItemAppointment::getState, 1);//不是未预约的数据，就是处于预约中和预约已完成的数据

                List<BuPrescriptionItemAppointment> list = buPrescriptionItemAppointmentMapper.selectList(buPrescriptionItemAppointmentQueryWrapper);

                LambdaUpdateWrapper<BuOutpatientAppointment> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(BuOutpatientAppointment::getIsDel, 0)
                        .eq(BuOutpatientAppointment::getId, buPrescriptionItemAppointment.getOutpatientAppointmentId());
                if (CollectionUtils.isEmpty(list)) {
                    //没有查到不是未预约状态的子项(所以该门诊下的所有技术项目都未预约)
                    updateWrapper.set(BuOutpatientAppointment::getState,1);
                } else {
                    //删除了一个预约数据，所以总的门诊下的预约数据一定有未完成的，所以总的不可能是预约完成，现在只能是预约中了
                    updateWrapper.set(BuOutpatientAppointment::getState,2);
                }
                result = buOutpatientAppointmentMapper.update(null,updateWrapper);
            }
        }
        return del > 0 && update > 0 && result > 0;
    }

    @Override
    public Boolean appointment(AppointmentRo appointmentRo) {
        //取出现有要保存或编辑的预约数据的id
        List<Long> idList = appointmentRo.getBuPrescriptionItemAppointmentItemRoList().stream().filter(item -> Objects.nonNull(item.getId())).map(BuPrescriptionItemAppointmentItemRo::getId).collect(Collectors.toList());

        //查询出之前在，这次没有的id，表明这些预约数据要删除
        List<Long> deleteIdList = appointmentRo.getPreItemIdList().stream().filter(item -> !idList.contains(item)).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(deleteIdList)) {
            deleteIdList.forEach(item -> {
                deleteAppointment(item);
            });
        }
        //查询出id为null的数据出来这表示是新加上的数据
        List<BuPrescriptionItemAppointmentItem> insertList = appointmentRo.getBuPrescriptionItemAppointmentItemRoList().stream().filter(item -> Objects.isNull(item.getId())).map(obj -> {
            BuPrescriptionItemAppointmentItem buPrescriptionItemAppointmentItem = new BuPrescriptionItemAppointmentItem();
            BeanUtil.copyProperties(obj, buPrescriptionItemAppointmentItem);
            buPrescriptionItemAppointmentItem.setCustomerId(appointmentRo.getCustomerId());
            return buPrescriptionItemAppointmentItem;
        }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(insertList)) {
            insertList.forEach(item -> {
                makeAppointment(item);
            });
        }
        return true;
    }

    @Override
    public List<BuPrescriptionItemAppointmentItem> selectRemindLatestAppointmentList(Date startDate, Date endDate) {
        List<BuPrescriptionItemAppointmentItem> list = buPrescriptionItemAppointmentItemMapper.selectRemindLatestAppointmentList(startDate, endDate);
        return list;
    }

    @Override
    public List<BuPrescriptionItemAppointmentItem> selectTreatmentRemindList(Date startDate, Date endDate) {
        List<BuPrescriptionItemAppointmentItem> list = buPrescriptionItemAppointmentItemMapper.selectTreatmentRemindList(startDate, endDate);
        return list;
    }
}
