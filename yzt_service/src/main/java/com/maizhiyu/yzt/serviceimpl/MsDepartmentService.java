package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class MsDepartmentService implements IMsDepartmentService {

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
    public List<Map<String, Object>> getDepartmentList(Integer status, String term) {
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
        List<Map<String, Object>> list = mapper.selectMaps(wrapper);
        return list;
    }
}
