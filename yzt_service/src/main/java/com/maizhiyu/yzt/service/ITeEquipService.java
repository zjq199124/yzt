package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TeEquip;
import com.maizhiyu.yzt.entity.TeMaintain;
import com.maizhiyu.yzt.entity.TxXzcData;
import com.maizhiyu.yzt.entity.TxXzcRun;

import java.util.List;
import java.util.Map;

public interface ITeEquipService extends IService<TeEquip> {

    Integer addEquip(TeEquip equip);

    Integer delEquip(Long id);

    Integer setEquip(TeEquip equip);

    TeEquip getEquip(Long id);

    List<Map<String, Object>> getEquipList(
            Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term);

    List<Map<String, Object>> getEquipListWithRunData(
            Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term);

    List<Map<String, Object>> getEquipListWithMaintain(
            Long agencyId, Long customId, Integer type, Long modelId, Integer status, String term);
}
