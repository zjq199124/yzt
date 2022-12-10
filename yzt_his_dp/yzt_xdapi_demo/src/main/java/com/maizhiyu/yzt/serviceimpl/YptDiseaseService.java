package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.YptDisease;
import com.maizhiyu.yzt.mapperypt.YptDiseaseMapper;
import com.maizhiyu.yzt.service.IYptDiseaseService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor=Exception.class)
public class YptDiseaseService implements IYptDiseaseService {

    @Autowired
    private YptDiseaseMapper mapper;

    @Override
    @ExistCheck(clazz = YptDisease.class, fname = "hiscode", message = "疾病已存在")
    @ExistCheck(clazz = YptDisease.class, fname = "hisname", message = "疾病已存在")
    public Integer addDisease(YptDisease medicant) {
        return mapper.insert(medicant);
    }

    @Override
    public Integer delDisease(Integer id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setDisease(YptDisease medicant) {
        return mapper.updateById(medicant);
    }

    @Override
    public YptDisease getDisease(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public YptDisease getDiseaseByHiscode(String code) {
        QueryWrapper<YptDisease> wrapper = new QueryWrapper<>();
        wrapper.eq("hiscode", code);
        return mapper.selectOne(wrapper);
    }

    @Override
    public YptDisease getDiseaseByHisname(String name) {
        QueryWrapper<YptDisease> wrapper = new QueryWrapper<>();
        wrapper.eq("hisname", name);
        return mapper.selectOne(wrapper);
    }

    @Override
    public List<YptDisease> getDiseaseList(String term) {
        QueryWrapper<YptDisease> wrapper = new QueryWrapper<>();
        wrapper.like("name", term)
                .or().like("code", term)
                .or().like("pinyin", term)
                .or().like("abbr", term)
                .or().like("hisname", term)
                .or().like("hiscode", term);
        return mapper.selectList(wrapper);
    }
}
