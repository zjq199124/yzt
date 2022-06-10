package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.BuMedicant;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.BuMedicantMapper;
import com.maizhiyu.yzt.mapper.BuOutpatientMapper;
import com.maizhiyu.yzt.service.IBuMedicantService;
import com.maizhiyu.yzt.utils.ExistCheck;
import com.maizhiyu.yzt.utils.ExistChecks;
import com.maizhiyu.yzt.utils.MyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class BuMedicantService implements IBuMedicantService {

    @Autowired
    private BuMedicantMapper mapper;

    @Override
    @ExistCheck(clazz = BuMedicant.class, fname = "name", message = "药材已存在")
    public Integer addMedicant(BuMedicant medicant) {
        return mapper.insert(medicant);
    }

    @Override
    public Integer delMedicant(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setMedicant(BuMedicant medicant) {
        return mapper.updateById(medicant);
    }

    @Override
    public BuMedicant getMedicant(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public BuMedicant getMedicantByName(String name) {
        QueryWrapper<BuMedicant> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return mapper.selectOne(wrapper);
    }

    @Override
    public List<BuMedicant> getMedicantList(String term) {
        QueryWrapper<BuMedicant> wrapper = new QueryWrapper<>();
        wrapper.like("name", term)
                .or().like("pinyin", term)
                .or().like("abbr", term);
        return mapper.selectList(wrapper);
    }

    @Override
    public List<BuMedicant> getMedicantListByNameList(List<String> namelist) {
        QueryWrapper<BuMedicant> wrapper = new QueryWrapper<>();
        wrapper.in("name", namelist);
        return mapper.selectList(wrapper);
    }

}
