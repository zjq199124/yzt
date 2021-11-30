package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.TsStandard;
import com.maizhiyu.yzt.entity.TsSytech;
import com.maizhiyu.yzt.mapper.TsStandardMapper;
import com.maizhiyu.yzt.mapper.TsSytechMapper;
import com.maizhiyu.yzt.service.ITsStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TsStandardService implements ITsStandardService {

    @Autowired
    private TsStandardMapper standardMapper;

    @Autowired
    private TsSytechMapper sytechMapper;


    @Override
    public Integer addStandard(TsStandard standard) {
        return standardMapper.insert(standard);
    }

    @Override
    public Integer delStandard(Long id) {
        return standardMapper.deleteById(id);
    }

    @Override
    public Integer setStandard(TsStandard standard) {
        return standardMapper.updateById(standard);
    }

    @Override
    public TsStandard getStandard(Long id) {
        return standardMapper.selectById(id);
    }

    @Override
    public List<Map<String,Object>> getStandardList() {
        // 查询技术列表
        QueryWrapper<TsSytech> wrapper = new QueryWrapper<>();
        wrapper.eq("display", 1);
        List<Map<String, Object>> sytechList = sytechMapper.selectMaps(wrapper);

        // 查询标准列表
        List<TsStandard> standardList = standardMapper.selectList(null);

        // 整理数据格式
        for (Map<String, Object> sytech : sytechList) {
            sytech.put("children", new ArrayList<TsStandard>());
        }
        for (TsStandard standard : standardList) {
            for (Map<String, Object> sytech : sytechList) {
                if (sytech.get("id") == standard.getTid()) {
                    ((ArrayList<TsStandard>) sytech.get("children")).add(standard);
                }
            }
        }

        return sytechList;
    }
}
