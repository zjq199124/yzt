package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.MsRole;

import java.util.List;
import java.util.Map;

public interface IMsRoleService extends IService<MsRole> {

    Integer addRole(MsRole role);

    Integer delRole(Long id);

    Integer setRole(MsRole role);

    Integer setRoleStatus(MsRole role);

    MsRole getRole(Long id);

    List<Map<String, Object>> getRoleList(Integer status, String term);
}
