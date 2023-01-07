package com.maizhiyu.yzt.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.entity.TsAss;
import com.maizhiyu.yzt.entity.TsSytech;
import com.maizhiyu.yzt.entity.UserAss;
import com.maizhiyu.yzt.mapper.HsUserMapper;
import com.maizhiyu.yzt.mapper.TsAssMapper;
import com.maizhiyu.yzt.mapper.TsSytechMapper;
import com.maizhiyu.yzt.ro.BatchAddUserRO;
import com.maizhiyu.yzt.service.ITsAssService;
import javafx.scene.effect.SepiaTone;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(rollbackFor = Exception.class)
public class TsAssService extends ServiceImpl<TsAssMapper, TsAss> implements ITsAssService {

    @Resource
    private TsAssMapper tsAssMapper;

    @Resource
    private HsUserMapper hsUserMapper;

    @Resource
    private TsSytechMapper tsSytechMapper;

    @Override
    public List<Map<String, Object>> getAssItem(Long id) {
        List<Map<String, Object>> list = tsAssMapper.selectAssItem(id);
        return list;
    }


    @Override
    public IPage<Map<String, Object>> getAsslist(Page page,String phoneOrtherapistName,Long examinerId,
                                   String department,Integer status) {
        IPage<Map<String, Object>> list = tsAssMapper.selectAsslist(page,phoneOrtherapistName,examinerId
        ,department,status);
        return list;

    }

    public List<TsAss> addBatch(BatchAddUserRO batchAddUserRO) {
        List<TsAss> list = new ArrayList<TsAss>();
        String[] therapistIds = batchAddUserRO.therapistIdS.split(",");
        for(String therapistId : therapistIds) {
            TsAss tsAss = new TsAss();
            tsAss.setStatus(0);
            tsAss.setExaminerId(batchAddUserRO.examinerId);
            tsAss.setTherapistId(Long.valueOf(therapistId));
            tsAss.setSytechId(batchAddUserRO.sytechId);
            list.add(tsAss);
            tsAssMapper.insert(tsAss);
        }
        return list;

    }

//        if (!CollectionUtils.isEmpty(resultPage.getRecords())) {
//            Set<Long> set = resultPage.getRecords().stream().map(TsAss::getSytechId).collect(Collectors.toSet());
//            Set<Long> set1 = resultPage.getRecords().stream().map(TsAss::getExaminerId).collect(Collectors.toSet());
//            Set<Long> set2 = resultPage.getRecords().stream().map(TsAss::getTherapistId).collect(Collectors.toSet());
//            Boolean res = set1.addAll(set2);
//            Map<Long, String> map = tsSytechMapper.selectBatchIds(set).stream().collect(Collectors.toMap(TsSytech::getId, TsSytech::getName));
//            Map<Long, String> map1 = hsUserMapper.selectBatchIds(set1).stream().collect(Collectors.toMap(HsUser::getId, HsUser::getRealName));
//            resultPage.getRecords().forEach(item -> {
//                String sytechName = map.get(item.getSytechId());
//                String examinerName = map1.get(item.getExaminerId());
//                String therapistName = map1.get(item.getTherapistId());
//                item.setSytechName(sytechName);
//                item.setExaminerName(examinerName);
//                item.setTherapistName(therapistName);
//            });
//        }
}
