package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TeEquip;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.TeEquipMapper;
import com.maizhiyu.yzt.service.ITeEquipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class TeEquipService extends ServiceImpl<TeEquipMapper,TeEquip> implements ITeEquipService {

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
        // id不空按id查询
        if (equip.getId() != null) {
            wrapper.eq("id", equip.getId());
        }
        // id为空按code查询
        else if (equip.getCode() != null) {
            wrapper.eq("code", equip.getCode());
        }
        // id和code都空则错误
        else {
            throw new BusinessException("id和code不能都为空");
        }
        return mapper.update(equip, wrapper);
    }

    @Override
    public TeEquip getEquip(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public IPage<Map<String, Object>> getEquipList(Page page,
                                                  Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term) {
        IPage<Map<String, Object>> list = mapper.selectEquipList(page,agencyId, customId, type, modelId, status, term);
        return list;
    }

    @Override
    public List<Map<String, Object>> getEquipListWithRunData(Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectEquipListWithRunData(agencyId, customId, type, modelId, status, term);
        return list;
    }



    @Override
    public IPage<Map<String, Object>> getEquipListWithMaintain(Page page,Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term) {
        IPage<Map<String, Object>> list = mapper.selectEquipListWithMaintain(page,agencyId, customId, type, modelId, status, term);
        return list;
    }
}
