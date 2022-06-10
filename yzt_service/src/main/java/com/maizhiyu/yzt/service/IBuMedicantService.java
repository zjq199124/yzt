package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.BuMedicant;

import java.util.List;

public interface IBuMedicantService {

    Integer addMedicant(BuMedicant medicant);

    Integer delMedicant(Long id);

    Integer setMedicant(BuMedicant medicant);

    BuMedicant getMedicant(Long id);

    BuMedicant getMedicantByName(String name);

    List<BuMedicant> getMedicantList(String term);

    List<BuMedicant> getMedicantListByNameList(List<String> namelist);
}
