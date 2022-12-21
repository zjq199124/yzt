package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.YptTreatment;
import com.maizhiyu.yzt.mapperypt.YptTreatmentMapper;
import com.maizhiyu.yzt.service.IYptTreatmentService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor=Exception.class)
public class YptTreatmentService implements IYptTreatmentService {

    @Autowired
    private YptTreatmentMapper mapper;

    @Override
    @ExistCheck(clazz = YptTreatment.class, fname = "code", message = "治疗项目已存在")
    @ExistCheck(clazz = YptTreatment.class, fname = "name", message = "治疗项目已存在")
    public Integer addTreatment(YptTreatment medicant) {
        return mapper.insert(medicant);
    }

    @Override
    public Integer delTreatment(Integer id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setTreatment(YptTreatment medicant) {
        return mapper.updateById(medicant);
    }

    @Override
    public YptTreatment getTreatment(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public YptTreatment getTreatmentByCode(String code) {
        QueryWrapper<YptTreatment> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        return mapper.selectOne(wrapper);
    }

    @Override
    public YptTreatment getTreatmentByName(String name) {
        QueryWrapper<YptTreatment> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return mapper.selectOne(wrapper);
    }

    @Override
    public YptTreatment getTreatmentByHisCode(String code) {
        QueryWrapper<YptTreatment> wrapper = new QueryWrapper<>();
        wrapper.eq("hiscode", code);
        return mapper.selectOne(wrapper);
    }

    @Override
    public YptTreatment getTreatmentByHisName(String name) {
        QueryWrapper<YptTreatment> wrapper = new QueryWrapper<>();
        wrapper.eq("hisname", name);
        return mapper.selectOne(wrapper);
    }

    @Override
    public Page<YptTreatment> getTreatmentList(Page page,String term) {
        QueryWrapper<YptTreatment> wrapper = new QueryWrapper<>();
        wrapper.like("name", term)
                .or().like("code", term)
                .or().like("pinyin", term)
                .or().like("abbr", term)
                .or().like("hisname", term)
                .or().like("hiscode", term);
        return mapper.selectPage(page,wrapper);
    }
}
