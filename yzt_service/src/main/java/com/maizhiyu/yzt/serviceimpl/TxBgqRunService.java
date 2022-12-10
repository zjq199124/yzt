package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TxBgqRun;
import com.maizhiyu.yzt.mapper.TxBgqRunMapper;
import com.maizhiyu.yzt.service.ITxBgqRunService;
import com.maizhiyu.yzt.utils.ExistCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class TxBgqRunService extends ServiceImpl<TxBgqRunMapper,TxBgqRun> implements ITxBgqRunService {

    @Autowired
    private TxBgqRunMapper runMapper;

    @Override
    @ExistCheck(clazz = TxBgqRun.class, fname = "code|runid", message = "runid已存在")
    public Integer addBgqRun(TxBgqRun run) {
        return runMapper.insert(run);
    }

    @Override
    public Integer setBgqRun(TxBgqRun run) {
        QueryWrapper<TxBgqRun> wrapper = new QueryWrapper<>();
        wrapper.eq("code", run.getCode())
                .eq("runid", run.getRunid());
        return runMapper.update(run, wrapper);
    }

    @Override
    public List<TxBgqRun> getBgqRunList(String code, String startDate, String endDate) {
        QueryWrapper<TxBgqRun> wrapper = new QueryWrapper<>();
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
        List<TxBgqRun> list = runMapper.selectList(wrapper);
        return list;
    }

}
