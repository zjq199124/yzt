package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.MsDepartment;

import java.util.Map;

public interface IMsDepartmentService extends IService<MsDepartment> {

    public Integer addDepartment(MsDepartment department);

    public Integer delDepartment(Long id);

    public Integer setDepartment(MsDepartment department);

    public MsDepartment getDepartment(Long id);

    IPage<Map<String, Object>> getDepartmentList(Page page, Integer status, String term);
}
