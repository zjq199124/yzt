package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.PsUserPatient;

import java.util.List;

public interface IPsUserPatientService extends IService<PsUserPatient> {
    List<PsUserPatient> selectByUserId(Long psUserId);
}
