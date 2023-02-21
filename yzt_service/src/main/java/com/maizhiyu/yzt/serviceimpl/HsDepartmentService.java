package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.HsDepartment;
import com.maizhiyu.yzt.mapper.HsDepartmentMapper;
import com.maizhiyu.yzt.service.IHsDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class)
public class HsDepartmentService extends ServiceImpl<HsDepartmentMapper,HsDepartment> implements IHsDepartmentService {

    @Autowired
    private HsDepartmentMapper mapper;

    @Override
    public Integer addDepartment(HsDepartment department) {
        return mapper.insert(department);
    }

    @Override
    public Integer delDepartment(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setDepartment(HsDepartment department) {
        return mapper.updateById(department);
    }

    @Override
    public HsDepartment getDepartment(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public IPage<Map<String, Object>> getDepartmentList(Page page, Long customerId, Integer status, String term) {
        QueryWrapper<HsDepartment> wrapper = new QueryWrapper<>();
        wrapper.select("id", "status", "dname","descrip",
                "DATE_FORMAT(update_time, '%Y-%m-%d %T') updateTime",
                "DATE_FORMAT(create_time, '%Y-%m-%d %T') createTime");
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
