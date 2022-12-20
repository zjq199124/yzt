package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.MsDepartment;
import com.maizhiyu.yzt.mapper.MsDepartmentMapper;
import com.maizhiyu.yzt.service.IMsDepartmentService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsDepartmentService extends ServiceImpl<MsDepartmentMapper,MsDepartment> implements IMsDepartmentService {

    @Autowired
    private MsDepartmentMapper mapper;

    @Override
    @ExistCheck(clazz = MsDepartment.class, fname = "dname", message = "部门已存在")
    public Integer addDepartment(MsDepartment department) {
        return mapper.insert(department);
    }

    @Override
    public Integer delDepartment(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setDepartment(MsDepartment department) {
        return mapper.updateById(department);
    }

    @Override
    public MsDepartment getDepartment(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public IPage<Map<String, Object>> getDepartmentList(Page page, Integer status, String term) {
        QueryWrapper<MsDepartment> wrapper = new QueryWrapper<>();
        wrapper.select("id", "status", "dname", "descrip",
                "DATE_FORMAT(update_time, '%Y-%m-%d %T') update_time",
                "DATE_FORMAT(create_time, '%Y-%m-%d %T') create_time");
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (term != null) {
            wrapper.like("dname", term);
        }
        IPage<Map<String, Object>> list = mapper.selectMapsPage(page,wrapper);
        return list;
    }
}
