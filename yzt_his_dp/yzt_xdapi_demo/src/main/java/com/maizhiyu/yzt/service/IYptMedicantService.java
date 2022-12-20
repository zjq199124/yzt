package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.YptMedicant;

public interface IYptMedicantService {

    Integer addMedicant(YptMedicant medicant);

    Integer delMedicant(Integer id);

    Integer setMedicant(YptMedicant medicant);

    YptMedicant getMedicant(Integer id);

    YptMedicant getMedicantByCode(String code);

    YptMedicant getMedicantByName(String name);

    IPage<YptMedicant> getMedicantList(Page page, String term);

}
