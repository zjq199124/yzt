package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.BuMedicant;
import com.maizhiyu.yzt.mapper.BuMedicantMapper;
import com.maizhiyu.yzt.service.IBuMedicantService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class BuMedicantService extends ServiceImpl<BuMedicantMapper,BuMedicant> implements IBuMedicantService {

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
    public IPage<BuMedicant> getMedicantList(Page page, String term) {
        QueryWrapper<BuMedicant> wrapper = new QueryWrapper<>();
        wrapper.like("name", term)
                .or().like("pinyin", term)
                .or().like("abbr", term);
        return mapper.selectPage(page,wrapper);
    }

    @Override
    public List<BuMedicant> getMedicantListByNameList(List<String> namelist) {
        QueryWrapper<BuMedicant> wrapper = new QueryWrapper<>();
        wrapper.in("name", namelist);
        return mapper.selectList(wrapper);
    }

}
