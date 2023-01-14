package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.PsUserPatient;

public interface IPsUserPatientService extends IService<PsUserPatient> {
    PsUserPatient selectByUserId(Long psUserId);
}
