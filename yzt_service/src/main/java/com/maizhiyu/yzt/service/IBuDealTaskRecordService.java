package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuDealTaskRecord;

public interface IBuDealTaskRecordService  extends IService<BuDealTaskRecord> {
    BuDealTaskRecord selectLastDealTime();
}
