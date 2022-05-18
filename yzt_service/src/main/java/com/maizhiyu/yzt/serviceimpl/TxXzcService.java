package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maizhiyu.yzt.entity.*;
import com.maizhiyu.yzt.mapper.TxXzcCmdMapper;
import com.maizhiyu.yzt.mapper.TxXzcDataMapper;
import com.maizhiyu.yzt.mapper.TxXzcRunMapper;
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
    @ExistCheck(clazz = TxXzcRun.class, fname = "code|runid", message = "runid已存在")
    public Integer addRun(TxXzcRun run) {
        return runMapper.insert(run);
    }

    @Override
    public Integer setRun(TxXzcRun run) {
        Integer res;
        // 更新运行数据
        res = runMapper.updateById(run);
        // 查询运行数据(主要是为了获取runid，前端没有带过来)
        TxXzcRun run2 = runMapper.selectById(run.getId());
        // 新增命令数据
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
        // 返回执行结果
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
    public List<TxXzcRun> getRunList(String code, String startDate, String endDate) {
        QueryWrapper<TxXzcRun> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        wrapper.orderByDesc("start_time");
        if (startDate != null && startDate.trim().length() > 0) {
            wrapper.ge("start_time", startDate);
        }
        if (endDate != null && endDate.trim().length() > 0) {
            // 注意：这里就是start_time < endDate，不要改为 end_time < endDate
            // startDate/endDate表示的是，设备开始运行的时间，在startDate和endDate之间
            wrapper.le("start_time", endDate);
        }
        List<TxXzcRun> list = runMapper.selectList(wrapper);
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
        // 查询预警信息
        QueryWrapper<TxXzcRun> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code)
                .eq("runid", runid);
        TxXzcRun run = runMapper.selectOne(wrapper);
        // 整理预警信息（b6:腰部液位低 b5:腰部药液超温 b4:腰部体感超温 b2:颈部液位低 b1:颈部药液超温 b0:颈部体感超温）
        if (run != null) {
            Integer warnInt = run.getWarn();
            if (warnInt != null) {
                if ((warnInt & 0b01000000) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "腰部液位低");
                    list.add(map);
                }
                if ((warnInt & 0b00100000) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "腰部药液超温");
                    list.add(map);
                }
                if ((warnInt & 0b00010000) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "腰部体感超温");
                    list.add(map);
                }
                if ((warnInt & 0b00000100) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "颈部液位低");
                    list.add(map);
                }
                if ((warnInt & 0b00000010) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "颈部药液超温");
                    list.add(map);
                }
                if ((warnInt & 0b00000001) > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("typeName", "颈部体感超温");
                    list.add(map);
                }
            }
        }
        return list;
    }
}
