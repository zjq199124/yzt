package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.YptMedicant;
import com.maizhiyu.yzt.mapperypt.YptMedicantMapper;
import com.maizhiyu.yzt.service.IYptMedicantService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor=Exception.class)
public class YptMedicantService implements IYptMedicantService {

    @Autowired
    private YptMedicantMapper mapper;

    @Override
    @ExistCheck(clazz = YptMedicant.class, fname = "code", message = "药材已存在")
    @ExistCheck(clazz = YptMedicant.class, fname = "name", message = "药材已存在")
    public Integer addMedicant(YptMedicant medicant) {
        return mapper.insert(medicant);
    }

    @Override
    public Integer delMedicant(Integer id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setMedicant(YptMedicant medicant) {
        return mapper.updateById(medicant);
    }

    @Override
    public YptMedicant getMedicant(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public YptMedicant getMedicantByCode(String code) {
        QueryWrapper<YptMedicant> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        return mapper.selectOne(wrapper);
    }

    @Override
    public YptMedicant getMedicantByName(String name) {
        QueryWrapper<YptMedicant> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return mapper.selectOne(wrapper);
    }

    @Override
    public IPage<YptMedicant> getMedicantList(Page page, String term) {
        QueryWrapper<YptMedicant> wrapper = new QueryWrapper<>();
        wrapper.like("name", term)
                .or().like("code", term)
                .or().like("pinyin", term)
                .or().like("abbr", term)
                .or().like("hisname", term)
                .or().like("hiscode", term);
        return mapper.selectPage(page,wrapper);
    }
}
