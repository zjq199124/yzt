package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.MsDepartment;

import java.util.List;
import java.util.Map;

public interface IMsDepartmentService {

    public Integer addDepartment(MsDepartment department);

    public Integer delDepartment(Long id);

    public Integer setDepartment(MsDepartment department);

    public MsDepartment getDepartment(Long id);

    public List<Map<String,Object>> getDepartmentList(Integer status, String term);
}
