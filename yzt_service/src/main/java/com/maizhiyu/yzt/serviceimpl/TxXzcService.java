package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.TxXzcCmd;
import com.maizhiyu.yzt.entity.TxXzcData;
import com.maizhiyu.yzt.entity.TxXzcRun;
import com.maizhiyu.yzt.mapper.TxXzcCmdMapper;
import com.maizhiyu.yzt.mapper.TxXzcDataMapper;
import com.maizhiyu.yzt.mapper.TxXzcRunMapper;
import com.maizhiyu.yzt.ro.TxXzcCmdRo;
import com.maizhiyu.yzt.service.ITxXzcService;
import com.maizhiyu.yzt.utils.ExistCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class TxXzcService implements ITxXzcService {

    @Autowired
    private TxXzcCmdMapper cmdMapper;

    @Autowired
    private TxXzcRunMapper runMapper;

    @Autowired
    private TxXzcDataMapper dataMapper;

    @Override
    public Integer addCmd(TxXzcCmd cmd) {
        return cmdMapper.insert(cmd);
    }

    @Override
    public Integer setCmd(TxXzcCmd cmd) {
        return cmdMapper.updateById(cmd);
    }

    @Override
    public TxXzcCmd getCmd(Long id, String code, String runid, Integer status) {
        QueryWrapper<TxXzcCmd> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (code != null) {
            wrapper.eq("code", code);
        }
        if (runid != null) {
            wrapper.eq("runid", runid);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time").last("limit 1");
        return cmdMapper.selectOne(wrapper);
    }

    @Override
    @ExistCheck(clazz = TxXzcRun.class, fname = "code|runid", message = "runid?????????")
    public Integer addRun(TxXzcRun run) {
        return runMapper.insert(run);
    }

    @Override
    public Integer setRun(TxXzcRun run) {
        Integer res;
        // ??????????????????
        res = runMapper.updateById(run);
        // ??????????????????(?????????????????????runid????????????????????????)
        TxXzcRun run2 = runMapper.selectById(run.getId());
        // ??????????????????
        TxXzcCmd cmd = new TxXzcCmd();
        cmd.setCmd(6);
        cmd.setStatus(1);
        cmd.setCode(run2.getCode());
        cmd.setRunid(run2.getRunid());
        cmd.setSysState(run2.getStatus());
        cmd.setNeckTemp(run2.getNeckTemp());
        cmd.setWaistTemp(run2.getWaistTemp());
        cmd.setCreateTime(new Date());
        res = cmdMapper.insert(cmd);
        // ??????????????????
        return res;
    }

    @Override
    public Integer setRunOnly(TxXzcRun run) {
        UpdateWrapper<TxXzcRun> wrapper = new UpdateWrapper<>();
        wrapper.eq("code", run.getCode())
                .eq("runid", run.getRunid());
        return runMapper.update(run, wrapper);
    }

    @Override
    public Integer setRunWarn(TxXzcRun run) {
        UpdateWrapper<TxXzcRun> wrapper = new UpdateWrapper<>();
        wrapper.eq("code", run.getCode())
                .eq("runid", run.getRunid());
        wrapper.set("warn", run.getWarn());
        return runMapper.update(run, wrapper);
    }

    @Override
    public Integer addData(TxXzcData data) {
        return dataMapper.insert(data);
    }

    @Override
    public IPage<TxXzcRun> getRunList(Page page, String code, String startDate, String endDate) {
        QueryWrapper<TxXzcRun> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        wrapper.orderByDesc("start_time");
        if (startDate != null && startDate.trim().length() > 0) {
            wrapper.ge("start_time", startDate);
        }
        if (endDate != null && endDate.trim().length() > 0) {
            // ?????????????????????start_time < endDate??????????????? end_time < endDate
            // startDate/endDate????????????????????????????????????????????????startDate???endDate??????
            wrapper.le("start_time", endDate);
        }
        IPage<TxXzcRun> list = runMapper.selectPage(page,wrapper);
        return list;
    }

    @Override
    public List<TxXzcData> getRunData(String code, String runId) {
        QueryWrapper<TxXzcData> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        wrapper.eq("runid", runId);
        List<TxXzcData> list = dataMapper.selectList(wrapper);
        for (TxXzcData data : list) {
            if (data.getNeckLiquidTemp() > 100) {
                data.setNeckLiquidTemp(data.getNeckLiquidTemp() / 10);
            }
            if (data.getNeckSkinTemp() > 100) {
                data.setNeckSkinTemp(data.getNeckSkinTemp() / 10);
            }
            if (data.getWaistLiquidTemp() > 100) {
                data.setWaistLiquidTemp(data.getWaistLiquidTemp() / 10);
            }
            if (data.getWaistSkinTemp() > 100) {
                data.setWaistSkinTemp(data.getWaistSkinTemp() / 10);
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getRunWarnList(String code, String runid) {
        List<Map<String, Object>> list = new ArrayList<>();
        // ??????????????????
        QueryWrapper<TxXzcRun> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code)
                .eq("runid", runid);
        TxXzcRun run = runMapper.selectOne(wrapper);
        // ?????????????????????b6:??????????????? b5:?????????????????? b4:?????????????????? b2:??????????????? b1:?????????????????? b0:?????????????????????
        if (run != null) {
            Integer warnInt = run.getWarn();
            if (warnInt != null) {
                if ((warnInt & 0b01000000) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "???????????????");
                    list.add(map);
                }
                if ((warnInt & 0b00100000) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "??????????????????");
                    list.add(map);
                }
                if ((warnInt & 0b00010000) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "??????????????????");
                    list.add(map);
                }
                if ((warnInt & 0b00000100) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "???????????????");
                    list.add(map);
                }
                if ((warnInt & 0b00000010) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "??????????????????");
                    list.add(map);
                }
                if ((warnInt & 0b00000001) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "??????????????????");
                    list.add(map);
                }
            }
        }
        return list;
    }

    @Override
    public TxXzcRun getRunOne(TxXzcCmdRo ro) {
        return runMapper.selectOne(new QueryWrapper<TxXzcRun>()
                .eq("code",ro.getCode())
                .eq("status",1)
                .orderByDesc("start_time")
                .last("limit 1")
        );
    }
}
