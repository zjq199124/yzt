package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maizhiyu.yzt.entity.TeWarn;
import com.maizhiyu.yzt.mapper.TeWarnMapper;
import com.maizhiyu.yzt.service.ITeWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TeWarnService implements ITeWarnService {

    @Autowired
    private TeWarnMapper mapper;

    @Override
    public Integer addWarn(TeWarn warn) {
        Integer res = 0;
        QueryWrapper<TeWarn> wrapper = new QueryWrapper<>();
        wrapper.eq("code", warn.getCode())
                .eq("runid", warn.getRunid());
        List<TeWarn> list = mapper.selectList(wrapper);
        if (list == null || list.size() == 0) {
            res = mapper.insert(warn);
        } else {
            res = mapper.update(warn, wrapper);
        }
        return res;
    }

    @Override
    public List<Map<String, Object>> getWarnList(String date, Long agencyId, Long customId, Integer type, Long modelId) {
        List<Map<String, Object>> list = mapper.selectWarnList(date, agencyId, customId, type, modelId);
        return list;
    }

    @Override
    public List<Map<String, Object>> getWarnListOfRun(String code, String runid) {
        List<Map<String, Object>> list = mapper.selectWarnListOfRun(code, runid);
        return list;
    }
}
