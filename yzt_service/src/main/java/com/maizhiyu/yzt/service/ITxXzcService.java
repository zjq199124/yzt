package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TxXzcCmd;
import com.maizhiyu.yzt.entity.TxXzcData;
import com.maizhiyu.yzt.entity.TxXzcRun;

import java.util.List;
import java.util.Map;

public interface ITxXzcService {

    Integer addCmd(TxXzcCmd cmd);

    Integer setCmd(TxXzcCmd cmd);

    TxXzcCmd getCmd(Long id, String code, String runid, Integer status);

    Integer addRun(TxXzcRun run);

    Integer setRun(TxXzcRun run);

    Integer setRunWarn(TxXzcRun run);

    Integer addData(TxXzcData data);

    List<TxXzcRun> getRunList(String code, String startDate, String endDate);

    List<TxXzcData> getRunData(String code, String runId);

    List<Map<String, Object>> getRunWarnList(String code, String runId);
}
