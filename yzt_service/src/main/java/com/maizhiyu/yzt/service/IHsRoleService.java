package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.HsRole;

import java.util.List;
import java.util.Map;

public interface IHsRoleService extends IService<HsRole> {

    Integer addRole(HsRole role);

    Integer delRole(Long id);

    Integer setRole(HsRole role);

    Integer setRoleStatus(HsRole role);

    HsRole getRole(Long id);

    List<Map<String, Object>> getRoleList(Long customerId, Integer status, String term);
}
