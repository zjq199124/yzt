package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuMedicant;

import java.util.List;

public interface IBuMedicantService extends IService<BuMedicant> {

    Integer addMedicant(BuMedicant medicant);

    Integer delMedicant(Long id);

    Integer setMedicant(BuMedicant medicant);

    BuMedicant getMedicant(Long id);

    BuMedicant getMedicantByName(String name);

    List<BuMedicant> getMedicantList(String term);

    List<BuMedicant> getMedicantListByNameList(List<String> namelist);
}
