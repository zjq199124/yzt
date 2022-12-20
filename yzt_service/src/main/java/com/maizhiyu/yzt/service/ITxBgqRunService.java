package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.TxBgqRun;

public interface ITxBgqRunService extends IService<TxBgqRun> {

    Integer addBgqRun(TxBgqRun run);

    Integer setBgqRun(TxBgqRun run);

    IPage<TxBgqRun> getBgqRunList(Page page, String code, String startDate, String endDate);

}
