package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.MsDepartment;
import com.maizhiyu.yzt.entity.MsUser;

import java.util.List;
import java.util.Map;

public interface IMsUserService {

    Integer addUser(MsUser user);

    Integer delUser(Long id);

    Integer setUser(MsUser user);

    Integer setUserBasic(MsUser user);

    MsUser getUser(Long id);

    List<Map<String, Object>> getUserList(Long departmentId, Long roleId, Integer status, String term);
}
