package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsAssess;
import com.maizhiyu.yzt.entity.TsProblemRecord;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.TsAssessMapper;
import com.maizhiyu.yzt.mapper.TsProblemRecordMapper;
import com.maizhiyu.yzt.service.ITsAssessService;
import com.maizhiyu.yzt.service.ITsProblemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * className:TsProblemRecordService
 * Package:com.maizhiyu.yzt.serviceimpl
 * Description:
 *
 * @DATE:2021/12/13 4:21 下午
 * @Author:2101825180@qq.com
 */

@Service
public class TsProblemRecordService extends ServiceImpl<TsProblemRecordMapper,TsProblemRecord> implements ITsProblemRecordService {

    @Autowired
    private TsProblemRecordMapper tsProblemRecordMapper;

    @Autowired
    private TsAssessMapper tsAssessMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addTsProblemRecord(TsProblemRecord tsProblemRecord) {
        int i = tsProblemRecordMapper.insert(tsProblemRecord);

        if(i <= 0) {
            throw new BusinessException("失败");
        }

        TsAssess tsAssess = new TsAssess();
        tsAssess.setId(tsProblemRecord.getAssessId());
        tsAssess.setStatus(2);
        tsAssess.setProblemRecordId(tsProblemRecord.getId());
        int i1 = tsAssessMapper.updateById(tsAssess);

        if(i1 <= 0) {
            throw new BusinessException("失败");
        }
    }

    @Override
    public TsProblemRecord getTsProblemRecordById(Long tsProblemRecordId) {
        return tsProblemRecordMapper.selectById(tsProblemRecordId);
    }
}
