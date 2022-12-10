package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.HsDepartment;

import java.util.List;
import java.util.Map;

public interface IHsDepartmentService extends IService<HsDepartment> {

    public Integer addDepartment(HsDepartment department);

    public Integer delDepartment(Long id);

    public Integer setDepartment(HsDepartment department);

    public HsDepartment getDepartment(Long id);

    public List<Map<String,Object>> getDepartmentList(Long customerId, Integer status, String term);
}
