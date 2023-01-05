package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuSignature;
import com.maizhiyu.yzt.mapper.BuSignatureMapper;
import com.maizhiyu.yzt.ro.WaitTreatmentRo;
import com.maizhiyu.yzt.service.BuSignatureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 治疗签到表(BuSignature)表服务实现类
 *
 * @author liuyzh
 * @since 2022-12-19 19:05:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BuSignatureServiceImpl extends ServiceImpl<BuSignatureMapper, BuSignature> implements BuSignatureService {

    @Resource
    private BuSignatureMapper buSignatureMapper;

    @Override
    public Boolean addSignature(BuSignature buSignature) {
        buSignature.setTreatmentStatus(0);
        buSignature.setCreateTime(new Date());
        buSignature.setUpdateTime(buSignature.getCreateTime());
        int insert = buSignatureMapper.insert(buSignature);
        return insert > 0;
    }

    @Override
    public Page<BuSignature> waitTreatmentList(WaitTreatmentRo waitTreatmentRo) {
        Page<BuSignature> page = new Page<>(waitTreatmentRo.getCurrentPage(), waitTreatmentRo.getPageSize());
        Page<BuSignature> resultPage = buSignatureMapper.selectWaitTreatmentList(page, waitTreatmentRo);
        return resultPage;
    }
}
