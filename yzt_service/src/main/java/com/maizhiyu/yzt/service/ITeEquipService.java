package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TeEquip;

import java.util.List;
import java.util.Map;

public interface ITeEquipService extends IService<TeEquip> {

    Integer addEquip(TeEquip equip);

    Integer delEquip(Long id);

    Integer setEquip(TeEquip equip);

    TeEquip getEquip(Long id);

    IPage<Map<String, Object>> getEquipList(Page page,
                                            Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term);

    List<Map<String, Object>> getEquipListWithRunData(
            Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term);

    IPage<Map<String, Object>> getEquipListWithMaintain(Page page,
            Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term);
}
