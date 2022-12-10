package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuCheck;

import java.util.List;

public interface IBuCheckService extends IService<BuCheck> {

    Integer addCheck(BuCheck check);

    Integer setCheck(BuCheck check);

    BuCheck getCheck(Long id);

    List<BuCheck> getCheckListOfOutpatient(Long outpatientId);

}
