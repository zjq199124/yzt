package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.HsRole;
import com.maizhiyu.yzt.result.Result;

import java.util.Map;

public interface IHsRoleService extends IService<HsRole> {

    Integer addRole(HsRole role);

    Result delRole(Long id);

    Boolean setRole(HsRole role);

    Integer setRoleStatus(HsRole role);

    HsRole getRole(Long id);

    IPage<Map<String, Object>> getRoleList(Page page, Long customerId, Integer status, String term);
}
