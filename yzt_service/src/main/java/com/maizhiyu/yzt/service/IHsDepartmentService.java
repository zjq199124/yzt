package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.HsDepartment;

import java.util.Map;

public interface IHsDepartmentService extends IService<HsDepartment> {

    public Integer addDepartment(HsDepartment department);

    public Integer delDepartment(Long id);

    public Integer setDepartment(HsDepartment department);

    public HsDepartment getDepartment(Long id);

    public IPage<Map<String, Object>> getDepartmentList(Page page, Long customerId, Integer status, String term);
}
