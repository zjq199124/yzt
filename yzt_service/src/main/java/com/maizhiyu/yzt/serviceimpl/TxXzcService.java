package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maizhiyu.yzt.entity.TxXzcCmd;
import com.maizhiyu.yzt.entity.TxXzcData;
import com.maizhiyu.yzt.entity.TxXzcRun;
import com.maizhiyu.yzt.mapper.TxXzcCmdMapper;
import com.maizhiyu.yzt.mapper.TxXzcDataMapper;
import com.maizhiyu.yzt.mapper.TxXzcRunMapper;
import com.maizhiyu.yzt.service.ITxXzcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


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
    public Integer addRun(TxXzcRun run) {
        return runMapper.insert(run);
    }

    @Override
    public Integer setRun(TxXzcRun run) {
        Integer res;
        // 更新运行数据
        res = runMapper.updateById(run);
        // 新增命令数据
        TxXzcCmd cmd = new TxXzcCmd();
        cmd.setCmd(6);
        cmd.setStatus(1);
        cmd.setCode(run.getCode());
        cmd.setRunid(run.getRunid());
        cmd.setSysStatus(run.getStatus());
        cmd.setNeckTemp(run.getNeckTemp());
        cmd.setWaistTemp(run.getWaistTemp());
        cmd.setCreateTime(new Date());
        res = cmdMapper.insert(cmd);
        // 返回执行结果
        return res;
    }

    @Override
    public Integer addData(TxXzcData data) {
        return dataMapper.insert(data);
    }

    @Override
    public List<TxXzcRun> getRunList(String code, String startDate, String endDate) {
        QueryWrapper<TxXzcRun> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        if (startDate != null) {
            wrapper.ge("start_time", startDate);
        }
        if (endDate != null) {
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
        return list;
    }
}
