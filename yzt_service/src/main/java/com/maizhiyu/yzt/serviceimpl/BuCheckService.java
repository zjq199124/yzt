package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.enums.CheckTypeEnum;
import com.maizhiyu.yzt.mapper.BuCheckMapper;
import com.maizhiyu.yzt.mapper.BuOutpatientMapper;
import com.maizhiyu.yzt.service.IBuCheckService;
import com.maizhiyu.yzt.service.SysMultimediaService;
import com.maizhiyu.yzt.utils.MyDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BuCheckService extends ServiceImpl<BuCheckMapper,BuCheck> implements IBuCheckService {

    @Autowired
    private BuCheckMapper mapper;

    @Autowired
    private BuOutpatientMapper outpatientMapper;


    @Override
    public Integer addCheck(BuCheck check) {
        // 获取今天的门诊信息
        String timeStart = MyDate.getTodayStr();
        String timeEnd = MyDate.getTomorrowStr();
        Assert.isFalse(check.getIdCard() == null && check.getMobile() == null, "检查信息中必须有身份证号或手机号....");
        List<Map<String, Object>> list = outpatientMapper.selectOutpatientByPatientInfo(check.getIdCard(), check.getMobile(), timeStart, timeEnd);
        //门诊信息不存在直接报错
        if (list.size() != 1) {
//            throw new BusinessException("门诊信息不存在");
        } else {
            // 补充门诊ID
            Long outpatientId = (Long) list.get(0).get("id");
            check.setOutpatientId(outpatientId);
        }
        return mapper.insert(check);
    }

    @Override
    public Integer setCheck(BuCheck check) {
        return mapper.updateById(check);
    }

    @Override
    public BuCheck getCheck(Long id) {
        BuCheck buCheck = mapper.selectById(id);
        buCheck.setName(CheckTypeEnum.getNameByCode(buCheck.getType()));
        return buCheck;
    }

    @Override
    public List<BuCheck> getCheckListOfOutpatient(Long outpatientId) {
        QueryWrapper<BuCheck> wrapper = new QueryWrapper<>();
        wrapper.eq("outpatient_id", outpatientId);
        List<BuCheck> buChecks = mapper.selectList(wrapper);
        buChecks.stream().forEach(item->{
            item.setName(CheckTypeEnum.getNameByCode(item.getType()));
        });
        return buChecks;
    }

}
