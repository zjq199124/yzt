package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maizhiyu.yzt.entity.TeEquip;
import com.maizhiyu.yzt.entity.TxXzcData;
import com.maizhiyu.yzt.entity.TxXzcRun;
import com.maizhiyu.yzt.mapper.TeEquipMapper;
import com.maizhiyu.yzt.service.ITeEquipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TeEquipService implements ITeEquipService {

    @Autowired
    private TeEquipMapper mapper;

    @Override
    public Integer addEquip(TeEquip equip) {
        return mapper.insert(equip);
    }

    @Override
    public Integer delEquip(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setEquip(TeEquip equip) {
        UpdateWrapper<TeEquip> wrapper = new UpdateWrapper<>();
        if (equip.getId() != null) {
            wrapper.eq("id", equip.getId());
        }
        if (equip.getCode() != null) {
            wrapper.eq("code", equip.getCode());
        }
        return mapper.update(equip, wrapper);
    }

    @Override
    public TeEquip getEquip(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getEquipList(
            Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectEquipList(agencyId, customId, type, modelId, status, term);
        return list;
    }

    @Override
    public List<Map<String, Object>> getEquipListWithRunData(
            Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectEquipListWithRunData(agencyId, customId, type, modelId, status, term);
        return list;
    }

    @Override
    public List<Map<String, Object>> getEquipListWithMaintain(
            Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectEquipListWithMaintain(agencyId, customId, type, modelId, status, term);
        return list;
    }
}
