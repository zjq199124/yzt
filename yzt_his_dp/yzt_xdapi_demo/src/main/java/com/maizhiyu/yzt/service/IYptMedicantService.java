package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.YptMedicant;

import java.util.List;

public interface IYptMedicantService {

    Integer addMedicant(YptMedicant medicant);

    Integer delMedicant(Integer id);

    Integer setMedicant(YptMedicant medicant);

    YptMedicant getMedicant(Integer id);

    YptMedicant getMedicantByCode(String code);

    YptMedicant getMedicantByName(String name);

    List<YptMedicant> getMedicantList(String term);

}
