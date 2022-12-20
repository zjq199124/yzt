package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.MsRole;

import java.util.Map;

public interface IMsRoleService extends IService<MsRole> {

    Integer addRole(MsRole role);

    Integer delRole(Long id);

    Integer setRole(MsRole role);

    Integer setRoleStatus(MsRole role);

    MsRole getRole(Long id);

    IPage<Map<String, Object>> getRoleList(Page page, Integer status, String term);
}
