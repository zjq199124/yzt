package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.MsUser;

import java.util.Map;

public interface IMsUserService extends IService<MsUser> {

    Integer addUser(MsUser user);

    Integer delUser(Long id);

    Integer setUser(MsUser user);

    Integer setUserBasic(MsUser user);

    MsUser getUser(Long id);

    IPage<Map<String, Object>> getUserList(Page page, Long departmentId, Long roleId, Integer status, String term);
}
