package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TxBgqRun;

import java.util.List;

public interface ITxBgqRunService {

    Integer addBgqRun(TxBgqRun run);

    Integer setBgqRun(TxBgqRun run);

    List<TxBgqRun> getBgqRunList(String code, String startDate, String endDate);

}
