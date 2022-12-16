package com.maizhiyu.yzt.serviceimpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsAss;
import com.maizhiyu.yzt.entity.TsAssess;
import com.maizhiyu.yzt.mapper.TsAssMapper;
import com.maizhiyu.yzt.service.ITsAssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor=Exception.class)
public class TsAssService extends ServiceImpl<TsAssMapper, TsAss> implements ITsAssService {

    @Resource
    private TsAssMapper tsAssMapper;

    @Override
    public List<Map<String,Object>> getAsslist(Long therapistId, Long sytechId, String createTime, String endTime,
                                               String term){
        List<Map<String,Object>>  list = tsAssMapper.selectAsslist(therapistId,sytechId,createTime,endTime,term);
        return list;
    }



}
